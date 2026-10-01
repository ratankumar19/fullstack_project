/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: JwkSetController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// This declaration defines the main type represented by this source file.
public class JwkSetController {
    // Dependency/state used by this class. Constructor injection supplies `publicKey` when the class is created.
    private final RSAPublicKey publicKey;
    public JwkSetController(RSAPublicKey publicKey) { this.publicKey = publicKey; }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/.well-known/jwks.json")
    /**
     * Handles the `jwks` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public ResponseEntity<Map<String, Object>> jwks() {
        RSAKey key = new RSAKey.Builder(publicKey).keyID("company-management-key").build();
        return ResponseEntity.ok(new JWKSet(key).toJSONObject());
    }
}
