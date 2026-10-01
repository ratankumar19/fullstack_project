/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentResponse.java
 * Purpose: Client layer: declares communication with another microservice.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.client;

// This declaration defines the main type represented by this source file.
public class DepartmentResponse {

    private Long id;
    private String name;
    private String code;
    private String description;

    public DepartmentResponse() {
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

    /**
     * Handles the `setDescription` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
