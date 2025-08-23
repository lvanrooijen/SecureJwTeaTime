package com.example.SecureJwTeaTime.domain.user.base;

/**
 * Represent User Roles
 *
 * <p>toString() returns roles with a "ROLE_ prefix"
 */
public enum Role {
  CUSTOMER("ROLE_CUSTOMER"),
  COMPANY("ROLE_COMPANY"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  /**
   * Returns a String representation of this role
   *
   * <p>{@link Role} is prefixed with "ROLE_"
   *
   * @return {@link Role}
   */
  @Override
  public String toString() {
    return role;
  }
}
