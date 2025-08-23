package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * Should be thrown when a user with an invalid role is registered. extends {@link
 * BaseBadRequestException}
 *
 * <p>handled by {@link GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 */
public class InvalidUserRoleException extends BaseBadRequestException {
  public InvalidUserRoleException(String message) {
    super(message);
  }
}
