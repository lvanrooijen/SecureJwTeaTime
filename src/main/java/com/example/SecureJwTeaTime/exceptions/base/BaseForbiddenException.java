package com.example.SecureJwTeaTime.exceptions.base;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * Base class for exceptions related to entities that need to return a forbidden status code.
 *
 * <p>Specific exceptions that need to be caught and return a status code forbidden can extend this
 * class, exceptions extending this class will automatically get caught and return a problem detail
 * with http status of forbidden request, the provided exception message will be logged but not
 * included in the http response
 *
 * <p>Exceptions will be caught and handled by the following method {@link
 * GlobalExceptionHandler#handleForbiddenExceptions(Exception e)}
 */
public abstract class BaseForbiddenException extends RuntimeException {
  public BaseForbiddenException(String message) {
    super(message);
  }
}
