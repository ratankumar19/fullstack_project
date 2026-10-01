/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.service;

import com.company.department.dto.DepartmentRequest;
import com.company.department.dto.DepartmentResponse;
import com.company.department.entity.Department;
import com.company.department.exception.DuplicateResourceException;
import com.company.department.exception.ResourceNotFoundException;
// Use the mapper so API DTOs stay separate from JPA database entities.
import com.company.department.mapper.DepartmentMapper;
import com.company.department.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class DepartmentService {

    // Dependency/state used by this class. Constructor injection supplies `departmentRepository` when the class is created.
    private final DepartmentRepository departmentRepository;
    // Dependency/state used by this class. Constructor injection supplies `departmentMapper` when the class is created.
    private final DepartmentMapper departmentMapper;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper
    ) {
        this.departmentRepository =
                departmentRepository;

        this.departmentMapper =
                departmentMapper;
    }

// Runs the method inside a database transaction so related changes succeed or roll back together.
@Transactional
public DepartmentResponse createDepartment(
        DepartmentRequest request
) {

    // Step 1: Normalize department code
    String normalizedCode =
            request.getCode()
                    .trim()
                    .toUpperCase();

    // Step 2: Check duplicate using normalized code
    if (departmentRepository
            .existsByCodeIgnoreCase(normalizedCode)) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DuplicateResourceException(
                "Department already exists with code: "
                        + normalizedCode
        );
    }

    // Step 3: Set normalized code back into request
    request.setCode(normalizedCode);

    // Step 4: Convert DTO -> Entity
    Department department =
            // Use the mapper so API DTOs stay separate from JPA database entities.
            departmentMapper.toEntity(request);

    // Step 5: Save
    Department savedDepartment =
            // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
            departmentRepository.save(department);

    // Step 6: Entity -> Response DTO
    // Use the mapper so API DTOs stay separate from JPA database entities.
    return departmentMapper.toResponse(
            savedDepartment
    );
}
    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    public List<DepartmentResponse>
    getAllDepartments() {

        // Return the completed result to the caller of this service method.
        return departmentRepository
                // Ask the repository for all rows of this entity from the database.
                .findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(
            Long id
    ) {

        Department department =
                findDepartmentById(id);

        // Use the mapper so API DTOs stay separate from JPA database entities.
        return departmentMapper.toResponse(
                department
        );
    }

  // Runs the method inside a database transaction so related changes succeed or roll back together.
  @Transactional
public DepartmentResponse updateDepartment(
        Long id,
        DepartmentRequest request
) {

    Department department =
            findDepartmentById(id);

    // Normalize incoming department code
    String normalizedCode =
            request.getCode()
                    .trim()
                    .toUpperCase();

    // Check duplicate only when changing code
    if (!department.getCode()
            .equalsIgnoreCase(normalizedCode)
            &&
            departmentRepository
                    .existsByCodeIgnoreCase(
                            normalizedCode
                    )) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DuplicateResourceException(
                "Department already exists with code: "
                        + normalizedCode
        );
    }

    // Store normalized value
    request.setCode(normalizedCode);

    // Use the mapper so API DTOs stay separate from JPA database entities.
    departmentMapper.updateEntity(
            department,
            request
    );

    Department savedDepartment =
            // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
            departmentRepository.save(
                    department
            );

    // Use the mapper so API DTOs stay separate from JPA database entities.
    return departmentMapper.toResponse(
            savedDepartment
    );
}
    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional
    /**
     * Deletes/removes the requested data after checking that it exists.
     */
    public void deleteDepartment(Long id) {

        Department department =
                findDepartmentById(id);

        // Remove the selected record from the database.
        departmentRepository.delete(department);
    }

    private Department findDepartmentById(
            Long id
    ) {

        // Return the completed result to the caller of this service method.
        return departmentRepository
                // Ask the repository to look up one database record by its primary-key ID.
                .findById(id)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Department not found with id: "
                                                + id
                                )
                );
    }
}
