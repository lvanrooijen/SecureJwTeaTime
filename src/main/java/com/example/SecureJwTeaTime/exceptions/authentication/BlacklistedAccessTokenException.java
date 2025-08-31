package com.example.SecureJwTeaTime.exceptions.authentication;

import org.springframework.security.authentication.InsufficientAuthenticationException;

/**
 * This exception should be thrown when an access token is on the black list, it extends 
 */
public class BlacklistedAccessTokenException extends InsufficientAuthenticationException {
  public BlacklistedAccessTokenException(String message) {
    super(message);
  }
}
