package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseBadRequestException;

/**
 * This exception should be thrown when an action is attempted on an incompatible user Account Type
 *
 * <p>This exception extends {@link BaseBadRequestException} and is handled by {@link
 * GlobalExceptionHandler#handleBadRequestExceptions(Exception)}
 */
public class UserAccountMisMatchException extends BaseBadRequestException {
  public UserAccountMisMatchException(String message) {
    super(message);
  }
}
