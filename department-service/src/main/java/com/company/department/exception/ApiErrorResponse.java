/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ApiErrorResponse.java
 * Purpose: Exception layer: defines application errors and/or converts them into clear HTTP error responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.exception;

import java.time.LocalDateTime;
import java.util.Map;

// This declaration defines the main type represented by this source file.
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;

    public ApiErrorResponse(
            LocalDateTime timestamp,
            int status,
            String message,
            Map<String, String> errors
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Map<String, String> getErrors() {
        return errors;
    }
}
