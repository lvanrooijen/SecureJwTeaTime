package com.example.SecureJwTeaTime.domain.user.customer.dto;

import static com.example.SecureJwTeaTime.domain.user.base.dto.UserConstraints.*;

import com.example.SecureJwTeaTime.domain.user.base.Role;
import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import com.example.SecureJwTeaTime.util.validators.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

/**
 * Represents the request body for POST request creating a new {@link CustomerAccount}
 *
 * @param email
 * @param password
 * @param firstName
 * @param lastName
 * @param birthdate
 */
public record PostCustomer(
    @NotBlank @Email(message = INVALID_EMAIL_MSG) String email,
    @Password String password,
    @NotBlank @Length(min = NAME_MIN, max = NAME_MAX, message = FIRST_NAME_INVALID_LENGTH_MSG)
        String firstName,
    @NotBlank @Length(min = NAME_MIN, max = NAME_MAX, message = LAST_NAME_INVALID_LENGTH_MSG)
        String lastName,
    LocalDate birthdate) {
  /**
   * Method that returns a {@link CustomerAccount} from {@link PostCustomer}
   *
   * <p>Does not set a password! Spring security requires Password Encoder
   *
   * @param dto {@link PostCustomer}
   * @return {@link CustomerAccount}
   */
  public static CustomerAccount from(PostCustomer dto) {
    return CustomerAccount.builder()
        .email(dto.email)
        .firstName(dto.firstName)
        .lastName(dto.lastName)
        .birthDate(dto.birthdate)
        .role(Role.CUSTOMER)
        .createdOn(LocalDate.now())
        .isActivated(false)
        .build();
  }
}
