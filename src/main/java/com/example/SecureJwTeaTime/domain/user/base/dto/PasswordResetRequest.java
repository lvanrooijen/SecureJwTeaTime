package com.example.SecureJwTeaTime.domain.user.base.dto;

import static com.example.SecureJwTeaTime.domain.user.base.dto.UserConstraints.INVALID_EMAIL_MSG;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(@NotBlank @Email(message = INVALID_EMAIL_MSG) String email) {}
