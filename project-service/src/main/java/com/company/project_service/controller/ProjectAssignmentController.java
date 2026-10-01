/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectAssignmentController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.controller;

import com.company.project_service.dto.ProjectAssignmentRequest;
import com.company.project_service.dto.ProjectAssignmentResponse;

import com.company.project_service.service.ProjectAssignmentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// Triggers Bean Validation for the object supplied to the method.
@Validated
// Defines the common/base URL path handled by this controller.
@RequestMapping("/api/project-assignments")
// This declaration defines the main type represented by this source file.
public class ProjectAssignmentController {

    // Dependency/state used by this class. Constructor injection supplies `assignmentService` when the class is created.
    private final ProjectAssignmentService assignmentService;

    public ProjectAssignmentController(
            ProjectAssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    // ASSIGN EMPLOYEE
    // Maps an HTTP POST request to the method below.
    @PostMapping
    public ResponseEntity<ProjectAssignmentResponse> assignEmployee(
            // Triggers Bean Validation for the object supplied to the method.
            @Valid @RequestBody ProjectAssignmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assignmentService.assignEmployee(request));
    }

    // GET ASSIGNMENTS BY PROJECT
    // Maps an HTTP GET request to the method below.
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectAssignmentResponse>> getByProject(
            @PathVariable @Positive Long projectId) {

        return ResponseEntity.ok(
                assignmentService.getByProject(projectId)
        );
    }

    // GET ASSIGNMENTS BY EMPLOYEE
    // Maps an HTTP GET request to the method below.
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ProjectAssignmentResponse>> getByEmployee(
            @PathVariable @Positive Long employeeId) {

        return ResponseEntity.ok(
                assignmentService.getByEmployee(employeeId)
        );
    }

    // REMOVE ASSIGNMENT
    // Maps an HTTP DELETE request to the method below.
    @DeleteMapping("/employee/{employeeId}/project/{projectId}")
    public ResponseEntity<Void> removeAssignment(
            @PathVariable @Positive Long employeeId,
            @PathVariable @Positive Long projectId) {

        assignmentService.removeAssignment(employeeId, projectId);

        return ResponseEntity.noContent().build();
    }
}
