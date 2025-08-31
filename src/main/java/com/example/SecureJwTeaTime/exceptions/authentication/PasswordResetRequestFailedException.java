package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * This exception should be thrown if a password reset request can not be completed
 *
 * <p>This exception is handled by {@link
 * GlobalExceptionHandler#handlePasswordResetRequestFailedException(Exception)}
 *
 * <p>Returns HTTP status 200 OK
 */
public class PasswordResetRequestFailedException extends RuntimeException {
  public PasswordResetRequestFailedException(String message) {
    super(message);
  }
}
