/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeResponse.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.dto;

// This declaration defines the main type represented by this source file.
public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String designation;
    private Double salary;
    private Long departmentId;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long id,
            String name,
            String email,
            String designation,
            Double salary,
            Long departmentId
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Long getId() {
        return id;
    }

    /**
     * Handles the `setId` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * Handles the `setDepartmentId` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
