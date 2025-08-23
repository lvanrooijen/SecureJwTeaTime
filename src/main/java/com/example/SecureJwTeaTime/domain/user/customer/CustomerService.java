package com.example.SecureJwTeaTime.domain.user.customer;

import com.example.SecureJwTeaTime.domain.accountactivation.AccountActivation;
import com.example.SecureJwTeaTime.domain.accountactivation.AccountActivationRepository;
import com.example.SecureJwTeaTime.domain.user.base.UserRepository;
import com.example.SecureJwTeaTime.domain.user.customer.dto.GetCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PostCustomer;
import com.example.SecureJwTeaTime.events.userregistration.UserRegistrationPublisher;
import com.example.SecureJwTeaTime.exceptions.user.UserAlreadyRegisteredException;
import com.example.SecureJwTeaTime.security.jwt.JwtService;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
  private final UserRepository userRepository;
  private final CustomerAccountRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRegistrationPublisher registrationPublisher;
  private final AccountActivationRepository activationRepository;

  public GetCustomer register(PostCustomer dto, HttpServletResponse response) {
    userRepository
        .findByEmailIgnoreCase(dto.email())
        .ifPresent(
            user -> {
              throw new UserAlreadyRegisteredException("Email already in use");
            });

    CustomerAccount customer = PostCustomer.from(dto);
    customer.setPassword(passwordEncoder.encode(dto.password()));
    userRepository.save(customer);

    RefreshToken refreshToken = createAndSaveRefreshTokenForCustomer(customer);
    storeRefreshTokenInHttpServletResponse(response, refreshToken.getToken());

    String activationCode =
        createAndSaveActivationCodeForUser(customer).getActivationCode().toString();

    registrationPublisher.publishCustomerRegistrationEvent(customer, activationCode);

    String token = jwtService.generateAccessToken(customer);
    return GetCustomer.to(customer, token);
  }

  @Transactional
  private AccountActivation createAndSaveActivationCodeForUser(CustomerAccount customer) {
    AccountActivation activation =
        AccountActivation.builder()
            .createdOn(LocalDateTime.now())
            .activationCode(UUID.randomUUID())
            .user(customer)
            .build();

    return activationRepository.save(activation);
  }

  @Transactional
  private RefreshToken createAndSaveRefreshTokenForCustomer(CustomerAccount customer) {
    RefreshToken refreshToken =
        RefreshToken.builder()
            .user(customer)
            .token(jwtService.generateRefreshToken(customer))
            .isRevoked(false)
            .build();

    return refreshTokenRepository.save(refreshToken);
  }

  private void storeRefreshTokenInHttpServletResponse(
      HttpServletResponse response, String refreshToken) {
    ResponseCookie cookie =
        ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/api/v1/auth")
            .maxAge(Duration.ofDays(7))
            .sameSite("Strict")
            .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }
}
