package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;

/**
 * This exception should be thrown when a http request does not contain a (valid) refresh-token and
 * returns http status code 401 UNAUTHORIZED
 *
 * <p>This exception is handled by {@link
 * GlobalExceptionHandler#handleInvalidRefreshTokenException(Exception)} The exception message is
 * not returned but will be logged.
 */
public class InvalidRefreshTokenException extends RuntimeException {
  public InvalidRefreshTokenException(String message) {
    super(message);
  }
}
