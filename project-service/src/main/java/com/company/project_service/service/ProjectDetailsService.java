/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectDetailsService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.service;

import com.company.project_service.dto.EmployeeResponse;
import com.company.project_service.dto.ProjectDetailsResponse;

import com.company.project_service.entity.Project;
import com.company.project_service.entity.ProjectAssignment;

import com.company.project_service.exception.ResourceNotFoundException;

import com.company.project_service.gateway.EmployeeGateway;

import com.company.project_service.repository.ProjectAssignmentRepository;
import com.company.project_service.repository.ProjectRepository;

import org.springframework.stereotype.Service;

import java.util.List;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class ProjectDetailsService {

    // Dependency/state used by this class. Constructor injection supplies `projectRepository` when the class is created.
    private final ProjectRepository projectRepository;

    // Dependency/state used by this class. Constructor injection supplies `assignmentRepository` when the class is created.
    private final ProjectAssignmentRepository assignmentRepository;

    // Dependency/state used by this class. Constructor injection supplies `employeeGateway` when the class is created.
    private final EmployeeGateway employeeGateway;

    public ProjectDetailsService(
            ProjectRepository projectRepository,
            ProjectAssignmentRepository assignmentRepository,
            EmployeeGateway employeeGateway) {

        this.projectRepository = projectRepository;
        this.assignmentRepository = assignmentRepository;
        this.employeeGateway = employeeGateway;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public ProjectDetailsResponse getProjectDetails(Long projectId) {

        // 1. Fetch project from local database
        // Ask the repository to look up one database record by its primary-key ID.
        Project project = projectRepository.findById(projectId)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId
                        )
                );

        // 2. Fetch local assignment records
        List<ProjectAssignment> assignments =
                assignmentRepository.findByProject_Id(projectId);

        // 3. Fetch employee details from Employee Service
        List<EmployeeResponse> employees = assignments.stream()
                .map(assignment ->
                        employeeGateway.getEmployee(
                                assignment.getEmployeeId()
                        )
                )
                .toList();

        // 4. Build enriched response
        // Return the completed result to the caller of this service method.
        return ProjectDetailsResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .departmentId(project.getDepartmentId())
                .employees(employees)
                .build();
    }
}
