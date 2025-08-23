package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * This exception should be thrown when a user login is failed.
 *
 * <p>handled by {@link GlobalExceptionHandler#handleFailedLoginAttemptException(Exception)}
 */
public class FailedLoginAttemptException extends RuntimeException {
  public FailedLoginAttemptException(String message) {
    super(message);
  }
}
