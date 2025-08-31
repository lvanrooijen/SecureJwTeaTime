package com.example.SecureJwTeaTime.domain.user.passwordreset;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByUserEmailIgnoreCase(String email);
    Optional<PasswordReset> findByResetCode(UUID resetCode);
}
