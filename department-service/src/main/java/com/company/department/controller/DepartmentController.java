/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.controller;

import com.company.department.dto.DepartmentRequest;
import com.company.department.dto.DepartmentResponse;
import com.company.department.service.DepartmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// Defines the common/base URL path handled by this controller.
@RequestMapping("/api/departments")
// This declaration defines the main type represented by this source file.
public class DepartmentController {

    // Dependency/state used by this class. Constructor injection supplies `departmentService` when the class is created.
    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService
    ) {
        this.departmentService =
                departmentService;
    }

    // Maps an HTTP POST request to the method below.
    @PostMapping
    public ResponseEntity<DepartmentResponse>
    createDepartment(
            // Triggers Bean Validation for the object supplied to the method.
            @Valid
            @RequestBody
            DepartmentRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        departmentService
                                .createDepartment(request)
                );
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>>
    getAllDepartments() {

        return ResponseEntity.ok(
                departmentService
                        .getAllDepartments()
        );
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse>
    getDepartmentById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                departmentService
                        .getDepartmentById(id)
        );
    }

    // Maps an HTTP PUT request to the method below.
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse>
    updateDepartment(
            @PathVariable Long id,
            // Triggers Bean Validation for the object supplied to the method.
            @Valid
            @RequestBody
            DepartmentRequest request
    ) {

        return ResponseEntity.ok(
                departmentService
                        .updateDepartment(
                                id,
                                request
                        )
        );
    }

    // Maps an HTTP DELETE request to the method below.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteDepartment(
            @PathVariable Long id
    ) {

        departmentService.deleteDepartment(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
