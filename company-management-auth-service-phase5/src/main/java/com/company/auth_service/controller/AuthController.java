/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: AuthController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.controller;

import com.company.auth_service.dto.*;
import com.company.auth_service.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// Defines the common/base URL path handled by this controller.
@RequestMapping("/api/auth")
// This declaration defines the main type represented by this source file.
public class AuthController {
    // Dependency/state used by this class. Constructor injection supplies `authenticationService` when the class is created.
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/ping")
    public ResponseEntity<String> ping() { return ResponseEntity.ok("AUTH SERVICE WORKING"); }

    // Maps an HTTP POST request to the method below.
    @PostMapping("/register")
    /**
     * Creates new data after applying the required validation/business rules.
     */
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
    }

    // Maps an HTTP POST request to the method below.
    @PostMapping("/login")
    /**
     * Authenticates the supplied credentials and returns authentication information when they are valid.
     */
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/me")
    /**
     * Handles the `me` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public ResponseEntity<Map<String, Object>> me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "userId", jwt.getClaim("userId"),
                "name", jwt.getClaimAsString("name"),
                "email", jwt.getSubject(),
                "role", jwt.getClaimAsString("role")
        ));
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/admin/test")
    public ResponseEntity<String> adminTest() { return ResponseEntity.ok("Welcome ADMIN"); }
}
