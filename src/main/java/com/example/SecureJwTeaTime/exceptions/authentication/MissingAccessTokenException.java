package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseForbiddenException;

/**
 * This exception should be thrown when an access token is not present while trying to extract it
 * from the HTTP request header
 *
 * <p>It extends {@link BaseForbiddenException} and is handled by {@link
 * GlobalExceptionHandler#handleForbiddenExceptions(Exception)}
 *
 * <p>returns HTTP status FORBIDDEN 403
 */
public class MissingAccessTokenException extends BaseForbiddenException {
  public MissingAccessTokenException(String message) {
    super(message);
  }
}
