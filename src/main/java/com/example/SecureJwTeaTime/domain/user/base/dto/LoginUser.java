package com.example.SecureJwTeaTime.domain.user.base.dto;

import static com.example.SecureJwTeaTime.domain.user.base.dto.UserConstraints.INVALID_EMAIL_MSG;

import com.example.SecureJwTeaTime.util.validators.password.Password;
import jakarta.validation.constraints.Email;

/**
 * DTO representing the request body of a User POST (login user) request
 *
 * @param email
 * @param password
 */
public record LoginUser(
    @Email(message = INVALID_EMAIL_MSG) String email,
    @Password String password) {}
