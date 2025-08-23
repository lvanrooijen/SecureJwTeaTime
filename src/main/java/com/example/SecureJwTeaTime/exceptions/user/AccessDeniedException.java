package com.example.SecureJwTeaTime.exceptions.user;

import com.example.SecureJwTeaTime.exceptions.GlobalExceptionHandler;
import com.example.SecureJwTeaTime.exceptions.base.BaseForbiddenException;

/**
 * This exception should be thrown when a user without the proper permissions tries to access a
 * secured endpoint or resource.
 *
 * <p>extends {@link BaseForbiddenException} and is handled by{@link
 * GlobalExceptionHandler#handleAccessDeniedException(Exception)}
 */
public class AccessDeniedException extends BaseForbiddenException {
  public AccessDeniedException(String message) {
    super(message);
  }
}
