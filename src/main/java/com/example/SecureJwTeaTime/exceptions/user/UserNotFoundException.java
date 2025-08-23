package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseNotFoundException;

/**
 * Should be thrown when a user is not found, extends {@link BaseNotFoundException}
 *
 * <p>Handled by: {@link GlobalExceptionHandler#handleEntitiesNotFoundExceptions(Exception)}
 */
public class UserNotFoundException extends BaseNotFoundException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
