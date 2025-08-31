package com.example.SecureJwTeaTime.domain.user.base.dto;

import static com.example.SecureJwTeaTime.domain.user.base.dto.UserConstraints.INVALID_EMAIL_MSG;

import com.example.SecureJwTeaTime.util.validators.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewPasswordRequest(
    @NotBlank @Email(message = INVALID_EMAIL_MSG) String email,
    @NotBlank @Password String password,
    @NotBlank @Password String confirmPassword) {}
