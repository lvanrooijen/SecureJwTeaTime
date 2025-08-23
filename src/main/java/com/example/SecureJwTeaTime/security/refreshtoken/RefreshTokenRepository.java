package com.example.SecureJwTeaTime.security.refreshtoken;

import com.example.SecureJwTeaTime.domain.user.base.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
  Optional<RefreshToken> findByUser(User user);

  Optional<RefreshToken> findByToken(String refreshToken);
}
