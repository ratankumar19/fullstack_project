/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectAssignmentRepository.java
 * Purpose: Repository layer: provides database access through Spring Data JPA.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.repository;

import com.company.project_service.entity.ProjectAssignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// This declaration defines the main type represented by this source file.
public interface ProjectAssignmentRepository
        extends JpaRepository<ProjectAssignment, Long> {

    boolean existsByEmployeeIdAndProject_Id(
            Long employeeId,
            Long projectId
    );

    List<ProjectAssignment> findByProject_Id(Long projectId);

    List<ProjectAssignment> findByEmployeeId(Long employeeId);

    Optional<ProjectAssignment> findByEmployeeIdAndProject_Id(
            Long employeeId,
            Long projectId
    );
}
