package com.crookedcoder.habitjournal.auth;

/**
 * Registration response DTO using Java 21 record
 */
public record RegisterResponse(
    String message,
    String username,
    String email
) {}
