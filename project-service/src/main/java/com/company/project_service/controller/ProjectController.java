/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.controller;

import com.company.project_service.dto.ProjectRequest;
import com.company.project_service.dto.ProjectResponse;
import com.company.project_service.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//new api end point 
import com.company.project_service.dto.ProjectDetailsResponse;
import com.company.project_service.service.ProjectDetailsService;


import java.util.List;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// Defines the common/base URL path handled by this controller.
@RequestMapping("/api/projects")
// This declaration defines the main type represented by this source file.
public class ProjectController {

    // Dependency/state used by this class. Constructor injection supplies `projectService` when the class is created.
    private final ProjectService projectService;
   // Dependency/state used by this class. Constructor injection supplies `projectDetailsService` when the class is created.
   private final ProjectDetailsService projectDetailsService;

public ProjectController(
        ProjectService projectService,
        ProjectDetailsService projectDetailsService) {

    this.projectService = projectService;
    this.projectDetailsService = projectDetailsService;
}



    // Maps an HTTP POST request to the method below.
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            // Triggers Bean Validation for the object supplied to the method.
            @Valid @RequestBody ProjectRequest request) {

        ProjectResponse response =
                projectService.createProject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // Maps an HTTP GET request to the method below.
    @GetMapping
    /**
     * Reads and returns all available records.
     */
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(
                projectService.getAllProjects()
        );
    }


    // Maps an HTTP GET request to the method below.
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                projectService.getProjectById(id)
        );
    }


    // Maps an HTTP PUT request to the method below.
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            // Triggers Bean Validation for the object supplied to the method.
            @Valid @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(
                projectService.updateProject(id, request)
        );
    }


    // Maps an HTTP DELETE request to the method below.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.noContent().build();
    }


    // Maps an HTTP GET request to the method below.
    @GetMapping("/{id}/details")
    public ResponseEntity<ProjectDetailsResponse> getProjectDetails(
        @PathVariable Long id) {
            //System.out.println("Fetching project details for project ID: " + id);

    return ResponseEntity.ok(
            projectDetailsService.getProjectDetails(id)
    );
}

}
