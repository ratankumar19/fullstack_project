/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: JwtConfig.java
 * Purpose: Configuration layer: defines framework/security beans and application infrastructure behavior.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.jwt.*;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

// Marks this class as a source of Spring bean definitions/configuration.
@Configuration
// This declaration defines the main type represented by this source file.
public class JwtConfig {
    // Injects a configuration/property value into the field or parameter below.
    @Value("${jwt.private-key}") private Resource privateKeyResource;
    // Injects a configuration/property value into the field or parameter below.
    @Value("${jwt.public-key}") private Resource publicKeyResource;

    // Registers the object returned by the method below in the Spring application context.
    @Bean
    RSAPrivateKey rsaPrivateKey() throws Exception {
        String pem = privateKeyResource.getContentAsString(StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pem)));
    }

    // Registers the object returned by the method below in the Spring application context.
    @Bean
    RSAPublicKey rsaPublicKey() throws Exception {
        String pem = publicKeyResource.getContentAsString(StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(pem)));
    }

    // Registers the object returned by the method below in the Spring application context.
    @Bean
    JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID("company-management-key").build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
    }

    // Registers the object returned by the method below in the Spring application context.
    @Bean
    JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}
