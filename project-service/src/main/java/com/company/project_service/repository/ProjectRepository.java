/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectRepository.java
 * Purpose: Repository layer: provides database access through Spring Data JPA.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.repository;

import com.company.project_service.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

// This declaration defines the main type represented by this source file.
public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    boolean existsByCode(String code);
}
