/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: RegisterResponse.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.dto;

import com.company.auth_service.entity.Role;

public record RegisterResponse(

        Long userId,
        String name,
        String email,
        Role role,
        String message

) {
}
