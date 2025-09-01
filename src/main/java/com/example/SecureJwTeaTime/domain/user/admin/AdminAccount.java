package com.example.SecureJwTeaTime.domain.user.admin;

import com.example.SecureJwTeaTime.domain.user.base.Role;
import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin_accounts")
@NoArgsConstructor
@Getter
@Setter
public class AdminAccount extends User {
  @Builder
  public AdminAccount(
      String email,
      String password,
      Role role,
      LocalDate createdOn,
      boolean isActivated,
      RefreshToken refreshToken) {
    super(email, password, role, createdOn, isActivated, refreshToken);
  }

  @Override
  public Boolean isAdmin() {
    return true;
  }
}
