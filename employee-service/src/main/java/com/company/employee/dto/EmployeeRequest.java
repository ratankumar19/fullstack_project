/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeRequest.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// This declaration defines the main type represented by this source file.
public class EmployeeRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")

    @NotNull(message = "Department id is required")
    @Positive(message = "Department id must be greater than zero")
    private Long departmentId;

    private Double salary;

    public EmployeeRequest() {
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
    public String getEmail() {
        return email;
    }

    /**
     * Handles the `setEmail` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Handles the `setDesignation` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Handles the `setSalary` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Long getDepartmentId() {
    return departmentId;
}

    public void setDepartmentId(
        Long departmentId
    ) {
    this.departmentId = departmentId;
    }
}
