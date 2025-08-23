package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * Should be thrown when a user being registered already exists, extends {@link
 * BaseBadRequestException}
 *
 * <p>handled by {@link GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 */
public class UserAlreadyRegisteredException extends BaseBadRequestException {
  public UserAlreadyRegisteredException(String message) {
    super(message);
  }
}
