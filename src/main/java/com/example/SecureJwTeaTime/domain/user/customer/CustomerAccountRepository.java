package com.example.SecureJwTeaTime.domain.user.customer;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, UUID> {
  Optional<CustomerAccount> findByBirthDate(LocalDate birthdate);
}
