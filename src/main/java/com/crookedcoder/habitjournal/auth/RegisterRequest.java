package com.crookedcoder.habitjournal.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * User registration request DTO using Java 21 record
 */
public record RegisterRequest(
    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,

    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,

    @NotEmpty(message = "First name is required")
    String firstName,

    @NotEmpty(message = "Last name is required")
    String lastName,

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password,

    @NotEmpty(message = "Password confirmation is required")
    String confirmPassword
) {}
