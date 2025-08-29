package com.example.SecureJwTeaTime.domain.user.customer.dto;

import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import java.time.LocalDate;

public record PatchCustomer(String firstName, String lastName, LocalDate birthDate) {
  public static CustomerAccount updateFields(CustomerAccount customer, PatchCustomer patch) {
    if (patch.firstName != null) {
      customer.setFirstName(patch.firstName);
    }
    if (patch.lastName != null) {
      customer.setLastName(patch.lastName);
    }
    if (patch.birthDate != null) {
      customer.setBirthDate(patch.birthDate);
    }
    return customer;
  }
}
