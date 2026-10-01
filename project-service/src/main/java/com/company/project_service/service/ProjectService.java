/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.service;

import com.company.project_service.dto.ProjectRequest;
import com.company.project_service.dto.ProjectResponse;
import com.company.project_service.entity.Project;
import com.company.project_service.exception.DepartmentNotFoundException;
import com.company.project_service.exception.DuplicateResourceException;
import com.company.project_service.exception.ResourceNotFoundException;
// Use the mapper so API DTOs stay separate from JPA database entities.
import com.company.project_service.mapper.ProjectMapper;
import com.company.project_service.repository.ProjectRepository;

import feign.FeignException;

import org.springframework.stereotype.Service;

import com.company.project_service.client.DepartmentClient;
//import com.company.project_service.client.DepartmentClient;
import com.company.project_service.dto.DepartmentResponse;

import java.util.List;
//import feign.FeignException;

import com.company.project_service.gateway.DepartmentCircuitBreakerGateway;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class ProjectService {

    // Dependency/state used by this class. Constructor injection supplies `projectRepository` when the class is created.
    private final ProjectRepository projectRepository;
    // Dependency/state used by this class. Constructor injection supplies `projectMapper` when the class is created.
    private final ProjectMapper projectMapper;
    // Dependency/state used by this class. Constructor injection supplies `departmentGateway` when the class is created.
    private final DepartmentCircuitBreakerGateway departmentGateway;

    public ProjectService(
            ProjectRepository projectRepository,
            ProjectMapper projectMapper,
            DepartmentCircuitBreakerGateway departmentGateway) {

    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.departmentGateway = departmentGateway;
}

        /**
         * Validates the supplied data/business condition and throws a meaningful exception when invalid.
         */
        private void validateDepartment(Long departmentId) {
        departmentGateway.getDepartment(departmentId);
        }

    /**
     * Validates the supplied data/business condition and throws a meaningful exception when invalid.
     */
    private void validateProjectDates(ProjectRequest request) {

    if (request.getEndDate() != null
            && request.getEndDate()
                    .isBefore(request.getStartDate())) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new IllegalArgumentException(
                "Project end date cannot be before start date"
        );
    }
}

    // ==========================
    // CREATE PROJECT
    // ==========================
//     public ProjectResponse createProject(ProjectRequest request) {

//         Project project =
//                 projectMapper.mapToEntity(request);

//         Project savedProject =
//                 projectRepository.save(project);

//         return projectMapper.mapToResponse(savedProject);
//     }
    //updated createProject method to check for duplicate project code
    /**
     * Creates new data after applying the required validation/business rules.
     */
    public ProjectResponse createProject(ProjectRequest request) {
        validateProjectDates(request);
        validateDepartment(request.getDepartmentId());

    String normalizedCode =
            request.getCode()
                    .trim()
                    .toUpperCase();

    if (projectRepository.existsByCode(normalizedCode)) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DuplicateResourceException(
                "Project already exists with code: " + normalizedCode
        );
    }

    Project project =
            // Use the mapper so API DTOs stay separate from JPA database entities.
            projectMapper.mapToEntity(request);

    project.setCode(normalizedCode);

    Project savedProject =
            // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
            projectRepository.save(project);

    // Use the mapper so API DTOs stay separate from JPA database entities.
    return projectMapper.mapToResponse(savedProject);
}

    // ==========================
    // GET ALL PROJECTS
    // ==========================
    /**
     * Reads and returns all available records.
     */
    public List<ProjectResponse> getAllProjects() {

        // Return the completed result to the caller of this service method.
        return projectRepository
                // Ask the repository for all rows of this entity from the database.
                .findAll()
                .stream()
                .map(projectMapper::mapToResponse)
                .toList();
    }


    // ==========================
    // GET PROJECT BY ID
    // ==========================
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository
                // Ask the repository to look up one database record by its primary-key ID.
                .findById(id)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + id
                        )
                );

        // Use the mapper so API DTOs stay separate from JPA database entities.
        return projectMapper.mapToResponse(project);
    }


    // ==========================
    // UPDATE PROJECT
    // ==========================
    public ProjectResponse updateProject(
            Long id,
            ProjectRequest request) {

                validateProjectDates(request);
        validateDepartment(request.getDepartmentId());
        Project project = projectRepository
                // Ask the repository to look up one database record by its primary-key ID.
                .findById(id)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + id
                        )
                );

        project.setName(request.getName());
        project.setCode(request.getCode());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setDepartmentId(request.getDepartmentId());

        Project updatedProject =
                // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
                projectRepository.save(project);

        // Use the mapper so API DTOs stay separate from JPA database entities.
        return projectMapper.mapToResponse(updatedProject);
    }


    // ==========================
    // DELETE PROJECT
    // ==========================
    /**
     * Deletes/removes the requested data after checking that it exists.
     */
    public void deleteProject(Long id) {

        Project project = projectRepository
                // Ask the repository to look up one database record by its primary-key ID.
                .findById(id)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + id
                        )
                );

        // Remove the selected record from the database.
        projectRepository.delete(project);
    }
}
