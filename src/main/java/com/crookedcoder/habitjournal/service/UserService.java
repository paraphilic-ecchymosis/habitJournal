package com.crookedcoder.habitjournal.service;

import java.util.List;
import java.util.Optional;

import com.crookedcoder.habitjournal.users.User;
import com.crookedcoder.habitjournal.users.UserDto;

public interface UserService {

    /**
     * Register a new user with encrypted password
     */
    User registerUser(UserDto userDto);

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by ID
     */
    User findById(String id);

    /**
     * Get all users (admin only)
     */
    List<User> getAllUsers();

    /**
     * Update user profile
     */
    User updateUser(String id, User user);

    /**
     * Delete user
     */
    void deleteUser(String id);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Update last login timestamp
     */
    void updateLastLogin(String username);
}
