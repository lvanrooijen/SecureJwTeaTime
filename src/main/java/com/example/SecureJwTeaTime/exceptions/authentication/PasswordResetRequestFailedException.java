package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * This exception should be thrown if something goes wrong in the password reset request
 *
 * <p>This exception is handled by {@link
 * GlobalExceptionHandler#handlePasswordResetRequestFailedException(Exception)}
 */
public class PasswordResetRequestFailedException extends RuntimeException {
  public PasswordResetRequestFailedException(String message) {
    super(message);
  }
}
