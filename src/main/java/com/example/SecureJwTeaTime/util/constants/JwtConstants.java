package com.example.SecureJwTeaTime.util.constants;

public class JwtConstants {
  public static final String ROLES_CLAIM_NAME = "roles";
  public static final int JWT_EXPIRATION_MS = 15 * 60 * 1000;
  public static final long REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000;
  public static final int REFRESH_TOKEN_EXPIRATION_DAYS = 7;

  public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
  public static final String AUTHORIZATION_HEADER_JWT_PREFIX = "Bearer ";
}
