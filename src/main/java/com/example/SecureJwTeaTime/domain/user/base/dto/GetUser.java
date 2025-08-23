package com.example.SecureJwTeaTime.domain.user.base.dto;

import java.util.UUID;

/**
 * DTO representing how the User is returned to the client
 *
 * <p>email address is used as the username
 *
 * @param id
 * @param username
 */
public record GetUser(UUID id, String username) {}
