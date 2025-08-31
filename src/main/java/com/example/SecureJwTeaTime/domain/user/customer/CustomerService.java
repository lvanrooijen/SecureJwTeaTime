package com.example.SecureJwTeaTime.domain.user.customer;

import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.domain.user.base.UserRepository;
import com.example.SecureJwTeaTime.domain.user.base.dto.GetUserWithJwtToken;
import com.example.SecureJwTeaTime.domain.user.customer.dto.GetCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PatchCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PostCustomer;
import com.example.SecureJwTeaTime.events.userregistration.UserRegistrationPublisher;
import com.example.SecureJwTeaTime.exceptions.authentication.UserRequirementsNotMetException;
import com.example.SecureJwTeaTime.exceptions.user.UserAccountMisMatchException;
import com.example.SecureJwTeaTime.exceptions.user.UserAlreadyRegisteredException;
import com.example.SecureJwTeaTime.exceptions.user.UserNotFoundException;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivation;
import com.example.SecureJwTeaTime.security.accountactivation.AccountActivationRepository;
import com.example.SecureJwTeaTime.security.jwt.JwtService;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserRegistrationPublisher registrationPublisher;
  private final AccountActivationRepository activationRepository;
  private final RefreshTokenService refreshTokenService;

  public GetUserWithJwtToken register(PostCustomer dto, HttpServletResponse response) {
    if (!dto.password().equals(dto.confirmPassword())) {
      throw new UserRequirementsNotMetException(
          "Password is not the same as confirmation password");
    }
    userRepository
        .findByEmailIgnoreCase(dto.email())
        .ifPresent(
            user -> {
              throw new UserAlreadyRegisteredException("Email already in use");
            });

    CustomerAccount customer = PostCustomer.from(dto);
    customer.setPassword(passwordEncoder.encode(dto.password()));
    userRepository.save(customer);

    refreshTokenService.processRefreshToken(response, customer);

    String activationCode =
        createAndSaveActivationCodeForUser(customer).getActivationCode().toString();

    registrationPublisher.publishCustomerRegistrationEvent(customer, activationCode);

    String token = jwtService.generateAccessToken(customer);
    return GetUserWithJwtToken.to(customer, token);
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

  public GetCustomer update(UUID id, PatchCustomer patch) {
    User user =
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

    if (!isCustomer(user)) {
      throw new UserAccountMisMatchException(
          "Can not type update, provided user account is not a customer account");
    }
    CustomerAccount customer = (CustomerAccount) user;

    PatchCustomer.updateFields(customer, patch);
    userRepository.save(customer);

    // Todo send email that account is updated
    return GetCustomer.to(customer);
  }

  private User getAuthenticatedUser() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  private boolean isCustomer(User user) {
    return user instanceof CustomerAccount;
  }
}
