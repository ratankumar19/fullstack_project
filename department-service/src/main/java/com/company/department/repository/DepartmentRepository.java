/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentRepository.java
 * Purpose: Repository layer: provides database access through Spring Data JPA.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.repository;

import com.company.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This declaration defines the main type represented by this source file.
public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    boolean existsByCode(String code);
    boolean existsByCodeIgnoreCase(String code);

    List<Department> findByNameContainingIgnoreCase(
            String name
    );
}
