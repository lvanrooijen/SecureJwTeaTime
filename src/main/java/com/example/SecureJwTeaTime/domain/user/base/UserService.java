package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.domain.user.base.dto.GetUserWithJwtToken;
import com.example.SecureJwTeaTime.domain.user.base.dto.LoginUser;
import com.example.SecureJwTeaTime.domain.user.base.dto.NewPasswordRequest;
import com.example.SecureJwTeaTime.domain.user.base.dto.PasswordResetRequest;
import com.example.SecureJwTeaTime.domain.user.passwordreset.PasswordReset;
import com.example.SecureJwTeaTime.domain.user.passwordreset.PasswordResetRepository;
import com.example.SecureJwTeaTime.events.passwordreset.PasswordResetPublisher;
import com.example.SecureJwTeaTime.exceptions.authentication.*;
import com.example.SecureJwTeaTime.exceptions.user.UserNotFoundException;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivation;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivationRepository;
import com.example.SecureJwTeaTime.security.jwt.JwtService;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final AccountActivationRepository activationRepository;
  private final PasswordResetRepository passwordResetRepository;
  private final PasswordResetPublisher passwordResetPublisher;
  private final String resetURL = "https://SecureJwTeaTime/auth/reset-password";

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmailIgnoreCase(username)
        .orElseThrow(
            () -> new UserNotFoundException(String.format("Username:%s not found", username)));
  }

  public GetUserWithJwtToken login(HttpServletResponse response, LoginUser requestBody) {
    User user =
        userRepository
            .findByEmailIgnoreCase(requestBody.email())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    passwordEncoder.matches(requestBody.password(), user.getPassword());

    String accessToken = jwtService.generateAccessToken(user);

    refreshTokenService.processRefreshToken(response, user);

    return GetUserWithJwtToken.to(user, accessToken);
  }

  public void activateUser(UUID code) {
    User user = getAuthenticatedUser();

    AccountActivation accountActivation =
        activationRepository
            .findByActivationCode(code)
            .orElseThrow(
                () -> new InvalidAccountActivationCodeException("Activation code does not exist"));

    if (!user.isSameUSer(accountActivation.getUser())) {
      throw new InvalidAccountActivationCodeException(
          "Activation code does not belong to this user");
    }

    user.setActivated(true);
    userRepository.save(user);
  }

  private User getAuthenticatedUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public GetUserWithJwtToken refreshToken(
      HttpServletRequest request, HttpServletResponse response) {
    User user = getAuthenticatedUser();
    String providedToken = refreshTokenService.extractTokenFromCookie(request);

    RefreshToken refreshToken =
        refreshTokenService
            .getRefreshToken(providedToken)
            .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

    if (!refreshToken.getUser().isSameUSer(user)) {
      throw new InvalidRefreshTokenException("Refresh token does not match the user");
    }

    refreshTokenService.updateRefreshToken(response, refreshToken);
    String accessToken = jwtService.generateAccessToken(user);

    return GetUserWithJwtToken.to(user, accessToken);
  }

  public void logout(HttpServletRequest request) {
    User user = getAuthenticatedUser();
    String refreshTokenValue = refreshTokenService.extractTokenFromCookie(request);

    RefreshToken refreshToken =
        refreshTokenService
            .getRefreshToken(refreshTokenValue)
            .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token does not exist"));

    if (!refreshToken.getUser().isSameUSer(user)) {
      throw new InvalidRefreshTokenException("User and Refresh token don't match");
    }

    refreshTokenService.revoke(refreshToken);

    // TODO add access token to blacklist
  }

  /**
   * Creates a new Password Reset Request for the user.
   *
   * <p>Limited to 3 requests per hour.
   *
   * @param body
   */
  public void requestPasswordReset(PasswordResetRequest body) {
    User user =
        userRepository
            .findByEmailIgnoreCase(body.email())
            .orElseThrow(
                () ->
                    new PasswordResetRequestFailedException(
                        "Can not request password reset, email is not registered"));

    PasswordReset resetRequest =
        passwordResetRepository
            .findByUserEmailIgnoreCase(body.email())
            .orElse(createNewResetRequest(user));

    if (resetRequest.getRequestCount() >= 3) {
      throw new PasswordResetRequestLimitExceededException(
          "Too many password reset requests made in the last hour");
    }

    updateResetRequest(resetRequest);

    String activationLink = String.format("%s/%s", resetURL, resetRequest.getResetCode());
    passwordResetPublisher.publishPasswordResetEvent(user, activationLink);

    passwordResetRepository.save(resetRequest);
  }

  /**
   * Calculates the minutes passed since the last Reset Request was made
   *
   * @param resetRequest {@link PasswordReset}
   * @return Long amount of minutes passed since last request was made
   */
  public Long getMinutesPassed(PasswordReset resetRequest) {
    Duration timePassedSinceLastRequest =
        Duration.between(resetRequest.getLastRequestedAt(), LocalDateTime.now());
    return timePassedSinceLastRequest.toMinutes();
  }

  /**
   * Updates PasswordReset
   *
   * <p>If a password reset request was made longer then an hour ago, the request counter is reset.
   *
   * <p>If a password reset request was made less then an hour ago, the request counter gets
   * incremented
   *
   * @param resetRequest {@link PasswordReset}
   * @return updated {@link PasswordReset} request
   */
  public PasswordReset updateResetRequest(PasswordReset resetRequest) {
    resetRequest.setResetCode(UUID.randomUUID());
    resetRequest.setExpiresAt(LocalDateTime.now());
    if (getMinutesPassed(resetRequest) >= 60) {
      resetRequest.resetCounter();
    } else {
      resetRequest.incrementCounter();
    }
    resetRequest.setLastRequestedAt(LocalDateTime.now());
    return resetRequest;
  }

  /**
   * Creates a new PasswordReset request
   *
   * @param user whom it is generated for
   * @return created {@link PasswordReset}
   */
  public PasswordReset createNewResetRequest(User user) {
    return PasswordReset.builder()
        .user(user)
        .resetCode(UUID.randomUUID())
        .expiresAt(LocalDateTime.now().plusMinutes(15))
        .lastRequestedAt(LocalDateTime.now())
        .requestCount(0)
        .build();
  }

  public GetUserWithJwtToken resetPassword(UUID resetCode, NewPasswordRequest body) {
    if (!body.password().equals(body.confirmPassword())) {
      throw new UserRequirementsNotMetException(
          "Password is not the same as confirmation password");
    }

    PasswordReset reset =
        passwordResetRepository
            .findByResetCode(resetCode)
            .orElseThrow(() -> new InvalidPasswordResetCodeException("reset code does not exist"));

    User user =
        userRepository
            .findByEmailIgnoreCase(body.email())
            .orElseThrow(() -> new UserNotFoundException("Email not registered"));

    if (!reset.getUser().isSameUSer(user)) {
      throw new InvalidPasswordResetCodeException("The reset code is not connected to the user");
    }

    user.setPassword(passwordEncoder.encode(body.password()));
    userRepository.save(user);

    String token = jwtService.generateAccessToken(user);
    return GetUserWithJwtToken.to(user, token);
  }
}
