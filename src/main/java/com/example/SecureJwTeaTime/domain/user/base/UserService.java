package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.domain.user.base.dto.GetUserWithJwtToken;
import com.example.SecureJwTeaTime.domain.user.base.dto.LoginUser;
import com.example.SecureJwTeaTime.exceptions.authentication.InvalidAccountActivationCodeException;
import com.example.SecureJwTeaTime.exceptions.authentication.InvalidRefreshTokenException;
import com.example.SecureJwTeaTime.exceptions.user.UserNotFoundException;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivation;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivationRepository;
import com.example.SecureJwTeaTime.security.jwt.JwtService;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
}
