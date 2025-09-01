package com.example.SecureJwTeaTime.security.accountactivation;

import com.example.SecureJwTeaTime.domain.user.base.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "account_activation")
@Getter
@NoArgsConstructor
public class AccountActivation {
  @Builder
  public AccountActivation(UUID activationCode, LocalDateTime createdOn, User user) {
    this.activationCode = activationCode;
    this.createdOn = createdOn;
    this.user = user;
  }

  @Id @GeneratedValue private Long id;

  @Column(name = "activation_code", nullable = false)
  private UUID activationCode;

  @Column(name = "created_on", nullable = false)
  private LocalDateTime createdOn;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;
}
