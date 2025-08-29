package com.example.SecureJwTeaTime.domain.accountactivation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountActivationRepository extends JpaRepository<AccountActivation, Long> {
  Optional<AccountActivation> findByActivationCode(UUID code);
}
