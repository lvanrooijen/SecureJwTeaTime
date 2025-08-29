package com.example.SecureJwTeaTime.domain.user.customer.dto;

import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import java.time.LocalDate;
import java.util.UUID;

public record GetCustomer(UUID id, String firstName, String lastName, LocalDate birthDate) {
  public static GetCustomer to(CustomerAccount customer) {
    return new GetCustomer(
        customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getBirthDate());
  }
}
