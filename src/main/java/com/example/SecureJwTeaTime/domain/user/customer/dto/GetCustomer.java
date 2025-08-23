package com.example.SecureJwTeaTime.domain.user.customer.dto;

import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import java.util.UUID;

public record GetCustomer(UUID id, String email, String accessToken) {
  public static GetCustomer to(CustomerAccount customer, String token) {
    return new GetCustomer(customer.getId(), customer.getEmail(), token);
  }
}
