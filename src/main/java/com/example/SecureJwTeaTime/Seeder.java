package com.example.SecureJwTeaTime;

import com.example.SecureJwTeaTime.domain.user.admin.AdminAccount;
import com.example.SecureJwTeaTime.domain.user.base.Role;
import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.domain.user.base.UserRepository;
import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccountRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final CustomerAccountRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    seedAdmin();
    seedCustomer();
  }

  private void seedCustomer() {
    CustomerAccount customer =
        CustomerAccount.builder()
            .email("customer@email.com")
            .birthDate(LocalDate.of(2000, 2, 2))
            .createdOn(LocalDate.now())
            .firstName("cus")
            .lastName("tomer")
            .password(passwordEncoder.encode("SecurePassword123!"))
            .isActivated(true)
            .role(Role.CUSTOMER)
            .build();
    customerRepository.save(customer);
  }

  private void seedAdmin() {
    User admin =
        AdminAccount.builder()
            .email("admin@email.com")
            .isActivated(true)
            .password(passwordEncoder.encode("Admin123!"))
            .createdOn(LocalDate.now())
            .role(Role.ADMIN)
            .build();
    userRepository.save(admin);
  }
}
