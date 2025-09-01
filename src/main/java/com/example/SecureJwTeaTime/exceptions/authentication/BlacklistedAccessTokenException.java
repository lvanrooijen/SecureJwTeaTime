package com.example.SecureJwTeaTime.exceptions.authentication;

import com.example.SecureJwTeaTime.security.jwt.UnAuthorizedEntryPoint;
import org.springframework.security.authentication.InsufficientAuthenticationException;

/**
 * This exception should be thrown when an access token is on the black list, it extends {@link
 * InsufficientAuthenticationException} and is handled by spring security through: {@link
 * UnAuthorizedEntryPoint}
 *
 * <p>It returns HTTP status UNAUTHORIZED 401
 */
public class BlacklistedAccessTokenException extends InsufficientAuthenticationException {
  public BlacklistedAccessTokenException(String message) {
    super(message);
  }
}
