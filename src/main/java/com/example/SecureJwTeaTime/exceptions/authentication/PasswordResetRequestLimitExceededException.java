package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * This exception should be thrown when too many password resets are requested
 *
 * <p>This Exception is handled by {@link
 * GlobalExceptionHandler#handlePasswordResetRequestLimitExceededException(Exception)}
 */
public class PasswordResetRequestLimitExceededException extends RuntimeException {
  public PasswordResetRequestLimitExceededException(String message) {
    super(message);
  }
}
