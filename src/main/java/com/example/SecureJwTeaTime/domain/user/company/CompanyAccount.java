package com.example.SecureJwTeaTime.domain.user.company;

import com.example.SecureJwTeaTime.domain.user.base.Role;
import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "company_accounts")
@NoArgsConstructor
public class CompanyAccount extends User {
  @Builder
  public CompanyAccount(
      UUID id,
      String email,
      String password,
      Role role,
      LocalDate createdOn,
      boolean isActivated,
      RefreshToken refreshToken,
      String companyName,
      String kvkNumber) {
    super(id, email, password, role, createdOn, isActivated, refreshToken);
    this.companyName = companyName;
    this.kvkNumber = kvkNumber;
  }

  public CompanyAccount(
      String email,
      String password,
      Role role,
      LocalDate createdOn,
      boolean isActivated,
      String companyName,
      String kvkNumber) {
    super(email, password, role, createdOn, isActivated);
    this.companyName = companyName;
    this.kvkNumber = kvkNumber;
  }

  @Column(name = "company_name", nullable = false)
  private String companyName;

  @Column(name = "kvk_number", nullable = false)
  private String kvkNumber;
}
