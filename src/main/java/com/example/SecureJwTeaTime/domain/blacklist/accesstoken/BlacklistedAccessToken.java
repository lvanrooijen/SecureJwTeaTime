package com.example.SecureJwTeaTime.domain.blacklist.accesstoken;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blacklisted_access_tokens")
@NoArgsConstructor
@Getter
@Setter
public class BlacklistedAccessToken {
  public BlacklistedAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Id @GeneratedValue private Long id;

  @Column(name = "access_token", nullable = false)
  private String accessToken;
}
