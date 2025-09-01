package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * This exception should be thrown when an account activation code is invalid.
 *
 * <p>It extends {@link BaseBadRequestException} and is handled by {@link
 * GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 *
 * <p>return HTTP status BAD_REQUEST 400, exception message is included in the response
 */
public class InvalidAccountActivationCodeException extends BaseBadRequestException {
  public InvalidAccountActivationCodeException(String message) {
    super(message);
  }
}
