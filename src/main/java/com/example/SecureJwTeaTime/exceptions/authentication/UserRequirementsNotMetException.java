package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * This exception should be thrown when creating a user is not possible because it fails to meet the
 * requirements. It extends {@link BaseBadRequestException} and is handled by {@link
 * GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 *
 * <p>return HTTP status BAD_REQUEST 400, exception message is included in the response
 */
public class UserRequirementsNotMetException extends BaseBadRequestException {
  public UserRequirementsNotMetException(String message) {
    super(message);
  }
}
