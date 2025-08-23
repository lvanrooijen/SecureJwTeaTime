package com.example.SecureJwTeaTime.exceptions.base;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * Base class for exceptions related to entities that need to return a bad request status code.
 *
 * <p>Specific exceptions that need to be caught and return a status code bad request can extend
 * this class, exceptions extending this class will automatically get caught and return a problem
 * detail with http status of bad request, and the provided exception message as detail.
 *
 * <p>Exceptions will be caught and handled by the following method {@link
 * GlobalExceptionHandler#handleBadRequestExceptions(Exception e)}
 */
public abstract class BaseBadRequestException extends RuntimeException {
  public BaseBadRequestException(String message) {
    super(message);
  }
}
