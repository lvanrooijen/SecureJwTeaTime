package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseForbiddenException;

/**
 * This exception should be thrown when a client is attempting to make an action they don't have
 * permission for.
 *
 * <p>It extends {@link BaseForbiddenException} and is handled by {@link
 * GlobalExceptionHandler#handleForbiddenExceptions(Exception)}
 *
 * <p>It returns HTTP status FORBIDDEN 403, exception message is not included in the client response
 */
public class IllegalActionException extends BaseForbiddenException {
  public IllegalActionException(String message) {
    super(message);
  }
}
