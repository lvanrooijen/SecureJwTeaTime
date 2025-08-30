package com.example.SecureJwTeaTime.domain.user.passwordreset;

import com.example.SecureJwTeaTime.domain.user.base.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_resets")
@Getter
@NoArgsConstructor
public class PasswordReset {
  @Builder
  public PasswordReset(
      User user,
      UUID resetCode,
      LocalDateTime expiresAt,
      int requestCount,
      LocalDateTime lastRequestedAt) {
    this.user = user;
    this.resetCode = resetCode;
    this.expiresAt = expiresAt;
    this.requestCount = requestCount;
    this.lastRequestedAt = lastRequestedAt;
  }

  @Id @GeneratedValue private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Setter
  @Column(name = "reset_code", nullable = false)
  private UUID resetCode;

  @Setter
  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;

  @Setter
  @Column(name = "request_count")
  private int requestCount;

  @Setter
  @Column(name = "last_requested_at")
  private LocalDateTime lastRequestedAt;

  public boolean isExpired() {
    return expiresAt.isBefore(LocalDateTime.now());
  }

  public void resetCounter() {
    requestCount = 0;
  }

  public void incrementCounter() {
    requestCount++;
  }
}
