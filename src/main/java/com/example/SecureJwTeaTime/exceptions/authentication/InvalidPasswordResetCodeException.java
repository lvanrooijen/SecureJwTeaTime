package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseForbiddenException;

/**
 * This exception should be thrown when the client attempts to change a password with an invalid
 * code
 *
 * <p>it extends {@link BaseForbiddenException} and is handled by {@link
 * GlobalExceptionHandler#handleForbiddenExceptions(Exception)}
 *
 * <p>Return HTTP status code FORBIDDEN 403
 */
public class InvalidPasswordResetCodeException extends BaseForbiddenException {
  public InvalidPasswordResetCodeException(String message) {
    super(message);
  }
}
