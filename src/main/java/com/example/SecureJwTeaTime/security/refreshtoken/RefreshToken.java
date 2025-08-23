package com.example.SecureJwTeaTime.security.refreshtoken;

import com.example.SecureJwTeaTime.domain.user.base.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {
  @Builder
  public RefreshToken(String token, User user, boolean isRevoked) {
    this.token = token;
    this.user = user;
    this.isRevoked = isRevoked;
    this.expiresAt = LocalDate.now().plusDays(7);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "token", nullable = false, length = 512)
  private String token;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "revoked", nullable = false)
  private boolean isRevoked;

  @Column(name = "expires_at", nullable = false)
  private LocalDate expiresAt;
}
