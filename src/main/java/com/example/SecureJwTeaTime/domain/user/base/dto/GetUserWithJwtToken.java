package com.example.SecureJwTeaTime.domain.user.base.dto;

import java.util.UUID;

/**
 * DTO representing how the User is returned to the client, contains an access token and a refresh
 * token
 *
 * <p>email address is used as the username
 *
 * @param id user ID
 * @param email email address of user
 * @param accessToken jwt access-token
 */
public record GetUserWithJwtToken(
    UUID id, String email, String username, String role, String accessToken) {}
