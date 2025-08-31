package com.example.SecureJwTeaTime.domain.blacklist.accesstoken;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedAccessTokenRepository
    extends JpaRepository<BlacklistedAccessToken, Long> {
  Optional<BlacklistedAccessToken> findByAccessToken(String accessToken);
}
