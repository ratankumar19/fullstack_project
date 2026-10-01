/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: AuthenticationService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.service;

import com.company.auth_service.dto.LoginRequest;
import com.company.auth_service.dto.LoginResponse;
import com.company.auth_service.dto.RegisterRequest;
import com.company.auth_service.dto.RegisterResponse;

import com.company.auth_service.entity.Role;
import com.company.auth_service.entity.User;

import com.company.auth_service.exception.DuplicateEmailException;
import com.company.auth_service.exception.InvalidCredentialsException;

import com.company.auth_service.repository.UserRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class AuthenticationService {

    // Dependency/state used by this class. Constructor injection supplies `userRepository` when the class is created.
    private final UserRepository userRepository;

    // Dependency/state used by this class. Constructor injection supplies `passwordEncoder` when the class is created.
    private final PasswordEncoder passwordEncoder;

    // Dependency/state used by this class. Constructor injection supplies `jwtService` when the class is created.
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // ======================================
    // REGISTER
    // ======================================

    /**
     * Creates new data after applying the required validation/business rules.
     */
    public RegisterResponse register(RegisterRequest request) {

        if (request == null) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Request cannot be null");
        }

        String name = request.name() == null ? "" : request.name().trim();
        String email = request.email() == null ? "" : request.email().trim().toLowerCase(Locale.ROOT);
        String password = request.password();

        if (name.isBlank()) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Name is required");
        }

        if (email.isBlank()) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Email is required");
        }

        if (password == null || password.isBlank()) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Password is required");
        }

        if (password.getBytes(StandardCharsets.UTF_8).length > 72) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Password must not exceed 72 UTF-8 bytes");
        }

        if (userRepository.existsByEmail(email)) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new DuplicateEmailException("Email is already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.EMPLOYEE);

        User savedUser;

        try {
            savedUser = userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new DuplicateEmailException("Email is already registered");
        }

        // Return the completed result to the caller of this service method.
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "User registered successfully"
        );
    }

    // ======================================
    // LOGIN
    // ======================================

    /**
     * Authenticates the supplied credentials and returns authentication information when they are valid.
     */
    public LoginResponse login(LoginRequest request) {

        if (request == null) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new IllegalArgumentException("Request cannot be null");
        }

        String email = request.email() == null ? "" : request.email().trim().toLowerCase(Locale.ROOT);
        String password = request.password();

        if (email.isBlank() || password == null || password.isBlank()) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(email)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        boolean passwordMatches =
                passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatches) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user);

        // Return the completed result to the caller of this service method.
        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                accessToken,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}
