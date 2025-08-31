package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * This exception should be thrown when creating a user is not possible because it fails to meet the
 * requirements. It extends {@link BaseBadRequestException} and is handled by {@link
 * GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 *
 * <p>The included exception message will be returned to the client.
 */
public class UserRequirementsNotMetException extends BaseBadRequestException {
  public UserRequirementsNotMetException(String message) {
    super(message);
  }
}
