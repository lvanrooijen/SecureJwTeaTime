package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseForbiddenException;

/**
 * This exception should be thrown when an invalid password is provided
 *
 * <p>It extends {@link BaseForbiddenException} and is handled by {@link
 * GlobalExceptionHandler#handleForbiddenExceptions(Exception)}
 *
 * <p>It returns HTTP status FORBIDDEN 403, exception message is not included in the client response
 */
public class InvalidPasswordException extends BaseForbiddenException {
  public InvalidPasswordException(String message) {
    super(message);
  }
}
