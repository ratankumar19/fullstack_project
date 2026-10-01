/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentNotFoundException.java
 * Purpose: Exception layer: defines application errors and/or converts them into clear HTTP error responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.exception;

// This declaration defines the main type represented by this source file.
public class DepartmentNotFoundException
        extends RuntimeException {

    public DepartmentNotFoundException(
            String message
    ) {
        super(message);
    }
}   
