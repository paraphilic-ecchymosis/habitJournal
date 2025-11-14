package com.crookedcoder.habitjournal.auth;

import jakarta.validation.constraints.NotEmpty;

/**
 * Login request DTO using Java 21 record
 */
public record LoginRequest(
    @NotEmpty(message = "Username is required")
    String username,

    @NotEmpty(message = "Password is required")
    String password
) {}
