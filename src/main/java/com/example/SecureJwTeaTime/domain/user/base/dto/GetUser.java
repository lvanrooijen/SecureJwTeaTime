package com.example.SecureJwTeaTime.domain.user.base.dto;

import com.example.SecureJwTeaTime.domain.user.base.User;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO representing how the User is returned to the client
 *
 * <p>email address is used as the username
 *
 * @param id
 * @param email
 * @param name
 */
public record GetUser(
    UUID id, String email, String name, Boolean isActivated, String role, LocalDate createdOn) {
  public static GetUser to(User user) {
    return new GetUser(
        user.getId(),
        user.getEmail(),
        user.getAccountName(),
        user.isActivated(),
        user.getRole(),
        user.getCreatedOn());
  }
}
