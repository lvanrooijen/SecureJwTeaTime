package com.example.SecureJwTeaTime.domain.user.customer;

import com.example.SecureJwTeaTime.domain.user.base.Role;
import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.security.refreshtoken.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer_accounts")
@NoArgsConstructor
public class CustomerAccount extends User {
  @Builder
  public CustomerAccount(
      String email,
      String password,
      Role role,
      LocalDate createdOn,
      boolean isActivated,
      RefreshToken refreshToken,
      String firstName,
      String lastName,
      LocalDate birthDate) {
    super(email, password, role, createdOn, isActivated, refreshToken);
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "birth_date", nullable = true)
  private LocalDate birthDate;

  @Override
  public String getAccountName() {
    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
    lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
    return firstName + " " + lastName;
  }
}
