/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: UserRepository.java
 * Purpose: Repository layer: provides database access through Spring Data JPA.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.auth_service.repository;

import com.company.auth_service.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This declaration defines the main type represented by this source file.
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
