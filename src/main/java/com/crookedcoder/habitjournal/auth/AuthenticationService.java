package com.crookedcoder.habitjournal.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crookedcoder.habitjournal.exception.BadRequestException;
import com.crookedcoder.habitjournal.security.CustomUserDetailsService;
import com.crookedcoder.habitjournal.security.JwtTokenService;
import com.crookedcoder.habitjournal.service.JournalService;
import com.crookedcoder.habitjournal.service.UserService;
import com.crookedcoder.habitjournal.users.User;
import com.crookedcoder.habitjournal.users.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationService {

    private final UserService userService;
    private final JournalService journalService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Register a new user and create their journal
     */
    public RegisterResponse register(RegisterRequest request) {
        log.info("Attempting to register user: {}", request.getUsername());

        // Validate password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // Create UserDto from RegisterRequest
        UserDto userDto = new UserDto();
        userDto.setUsername(request.getUsername());
        userDto.setEmail(request.getEmail());
        userDto.setFirstname(request.getFirstName());
        userDto.setLastname(request.getLastName());
        userDto.setPassword(request.getPassword());
        userDto.setConfirmPassword(request.getConfirmPassword());

        // Register user (service will hash password and check for duplicates)
        User user = userService.registerUser(userDto);

        // Create journal for the new user
        try {
            journalService.createJournal(user.getUsername());
            log.info("Journal created for user: {}", user.getUsername());
        } catch (Exception e) {
            log.error("Failed to create journal for user: {}", user.getUsername(), e);
            // Continue anyway - journal can be created later
        }

        return new RegisterResponse(
                "User registered successfully",
                user.getUsername(),
                user.getEmail()
        );
    }

    /**
     * Authenticate user and generate JWT token
     */
    public LoginResponse login(LoginRequest request) {
        log.info("Attempting to authenticate user: {}", request.getUsername());

        // Authenticate using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT token
        String token = jwtTokenService.generateToken(userDetails);

        // Update last login timestamp
        userService.updateLastLogin(request.getUsername());

        // Get user info
        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        log.info("User authenticated successfully: {}", request.getUsername());

        return new LoginResponse(token, user.getUsername(), user.getEmail());
    }
}
