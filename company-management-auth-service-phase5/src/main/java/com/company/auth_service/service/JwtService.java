/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: JwtService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.service;

import com.company.auth_service.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class JwtService {
    // Dependency/state used by this class. Constructor injection supplies `encoder` when the class is created.
    private final JwtEncoder encoder;
    // Dependency/state used by this class. Constructor injection supplies `expirationSeconds` when the class is created.
    private final long expirationSeconds;
    // Dependency/state used by this class. Constructor injection supplies `issuer` when the class is created.
    private final String issuer;

    public JwtService(JwtEncoder encoder,
                      // Injects a configuration/property value into the field or parameter below.
                      @Value("${jwt.access-token-expiration}") long expirationSeconds,
                      // Injects a configuration/property value into the field or parameter below.
                      @Value("${jwt.issuer}") String issuer) {
        this.encoder = encoder;
        this.expirationSeconds = expirationSeconds;
        this.issuer = issuer;
    }

    /**
     * Performs JWT/token-related work used by the authentication flow.
     */
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole().name())
                .build();
        JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256)
                .keyId("company-management-key").build();
        // Return the completed result to the caller of this service method.
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    // Return the completed result to the caller of this service method.
    public long getExpirationSeconds() { return expirationSeconds; }
}
