/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentRequest.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// This declaration defines the main type represented by this source file.
public class DepartmentRequest {

    @NotBlank(
            message = "Department name is required"
    )
    private String name;

    @NotBlank(
            message = "Department code is required"
    )
    @Size(
            min = 2,
            max = 10,
            message = "Department code must be between 2 and 10 characters"
    )
    private String code;

    private String description;

    public DepartmentRequest() {
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getName() {
        return name;
    }

    /**
     * Handles the `setName` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getCode() {
        return code;
    }

    /**
     * Handles the `setCode` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }
}
