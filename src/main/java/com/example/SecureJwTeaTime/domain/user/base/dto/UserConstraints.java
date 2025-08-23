package com.example.SecureJwTeaTime.domain.user.base.dto;

import com.example.SecureJwTeaTime.util.constants.GlobalValidationConstraints;

/**
 * Contains constants for validation constraints related to the User DTO's.
 *
 * <p>Extends the {@link GlobalValidationConstraints}
 */
public class UserConstraints extends GlobalValidationConstraints {
  public static final int NAME_MIN = 3;
  public static final int NAME_MAX = 50;

  public static final String FIRST_NAME_INVALID_LENGTH_MSG =
      "First name must be between " + NAME_MIN + " and " + NAME_MAX + " characters";

  public static final String LAST_NAME_INVALID_LENGTH_MSG =
      "Last name must be between " + NAME_MIN + " and " + NAME_MAX + " characters";

  public static final String INVALID_EMAIL_MSG = "Invalid email address";
}
