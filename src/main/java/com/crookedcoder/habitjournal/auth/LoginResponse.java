package com.crookedcoder.habitjournal.auth;

/**
 * Login response DTO using Java 21 record
 */
public record LoginResponse(
    String token,
    String type,
    String username,
    String email
) {
    /**
     * Compact constructor with default type
     */
    public LoginResponse(String token, String username, String email) {
        this(token, "Bearer", username, email);
    }
}
