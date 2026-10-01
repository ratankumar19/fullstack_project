/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectAssignmentService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.service;

import com.company.project_service.dto.ProjectAssignmentRequest;
import com.company.project_service.dto.ProjectAssignmentResponse;

import com.company.project_service.entity.Project;
import com.company.project_service.entity.ProjectAssignment;

import com.company.project_service.exception.DuplicateAssignmentException;
import com.company.project_service.exception.ResourceNotFoundException;

import com.company.project_service.gateway.EmployeeGateway;

// Use the mapper so API DTOs stay separate from JPA database entities.
import com.company.project_service.mapper.ProjectAssignmentMapper;

import com.company.project_service.repository.ProjectAssignmentRepository;
import com.company.project_service.repository.ProjectRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class ProjectAssignmentService {

    // Dependency/state used by this class. Constructor injection supplies `assignmentRepository` when the class is created.
    private final ProjectAssignmentRepository assignmentRepository;
    // Dependency/state used by this class. Constructor injection supplies `projectRepository` when the class is created.
    private final ProjectRepository projectRepository;
    // Dependency/state used by this class. Constructor injection supplies `assignmentMapper` when the class is created.
    private final ProjectAssignmentMapper assignmentMapper;
    // Dependency/state used by this class. Constructor injection supplies `employeeGateway` when the class is created.
    private final EmployeeGateway employeeGateway;

    public ProjectAssignmentService(
            ProjectAssignmentRepository assignmentRepository,
            ProjectRepository projectRepository,
            ProjectAssignmentMapper assignmentMapper,
            EmployeeGateway employeeGateway) {

        this.assignmentRepository = assignmentRepository;
        this.projectRepository = projectRepository;
        this.assignmentMapper = assignmentMapper;
        this.employeeGateway = employeeGateway;
    }

    // ASSIGN EMPLOYEE
    public ProjectAssignmentResponse assignEmployee(
            ProjectAssignmentRequest request) {

        Long employeeId = request.getEmployeeId();
        Long projectId = request.getProjectId();

        // 1. Validate local project
        // Ask the repository to look up one database record by its primary-key ID.
        Project project = projectRepository.findById(projectId)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId
                        )
                );

        // 2. Check duplicate assignment
        if (assignmentRepository.existsByEmployeeIdAndProject_Id(
                employeeId, projectId)) {

            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new DuplicateAssignmentException(
                    "Employee is already assigned to this project"
            );
        }

        // 3. Validate remote employee
        employeeGateway.getEmployee(employeeId);

        // 4. Create assignment
        ProjectAssignment assignment =
                ProjectAssignment.builder()
                        .employeeId(employeeId)
                        .project(project)
                        .build();

        try {

            ProjectAssignment saved =
                    assignmentRepository.saveAndFlush(assignment);

            // Use the mapper so API DTOs stay separate from JPA database entities.
            return assignmentMapper.mapToResponse(saved);

        } catch (DataIntegrityViolationException ex) {

            // Database unique constraint protects against
            // concurrent duplicate assignment requests.
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new DuplicateAssignmentException(
                    "Employee is already assigned to this project"
            );
        }
    }

    // GET ASSIGNMENTS BY PROJECT
    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public List<ProjectAssignmentResponse> getByProject(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new ResourceNotFoundException(
                    "Project not found with id: " + projectId
            );
        }

        // Return the completed result to the caller of this service method.
        return assignmentRepository.findByProject_Id(projectId)
                .stream()
                .map(assignmentMapper::mapToResponse)
                .toList();
    }

    // GET ASSIGNMENTS BY EMPLOYEE
    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public List<ProjectAssignmentResponse> getByEmployee(Long employeeId) {

        employeeGateway.getEmployee(employeeId);

        // Return the completed result to the caller of this service method.
        return assignmentRepository.findByEmployeeId(employeeId)
                .stream()
                .map(assignmentMapper::mapToResponse)
                .toList();
    }

    // REMOVE ASSIGNMENT
    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional
    /**
     * Deletes/removes the requested data after checking that it exists.
     */
    public void removeAssignment(Long employeeId, Long projectId) {

        ProjectAssignment assignment =
                assignmentRepository.findByEmployeeIdAndProject_Id(
                        employeeId, projectId
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found"
                        )
                );

        // Remove the selected record from the database.
        assignmentRepository.delete(assignment);
    }
}
