/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: GlobalExceptionHandler.java
 * Purpose: Exception layer: defines application errors and/or converts them into clear HTTP error responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestControllerAdvice
// This declaration defines the main type represented by this source file.
public class GlobalExceptionHandler {

    // 404
    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> error = new LinkedHashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // 400 - Validation errors
    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(DuplicateResourceException.class)
public ResponseEntity<Map<String, Object>> handleDuplicateResource(
        DuplicateResourceException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.CONFLICT.value());
    error.put("error", "Conflict");
    error.put("message", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, Object>> handleIllegalArgument(
        IllegalArgumentException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.BAD_REQUEST.value());
    error.put("error", "Bad Request");
    error.put("message", ex.getMessage());

    return ResponseEntity
            .badRequest()
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(DepartmentNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleDepartmentNotFound(
        DepartmentNotFoundException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.NOT_FOUND.value());
    error.put("error", "Not Found");
    error.put("message", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(DepartmentServiceUnavailableException.class)
public ResponseEntity<Map<String, Object>> handleDepartmentUnavailable(
        DepartmentServiceUnavailableException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("timestamp", LocalDateTime.now());

    error.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());

    error.put("error", "Service Unavailable");

    error.put("message", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(EmployeeNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleEmployeeNotFound(
        EmployeeNotFoundException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", 404);
    error.put("error", "Not Found");
    error.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(EmployeeServiceUnavailableException.class)
public ResponseEntity<Map<String, Object>> handleEmployeeUnavailable(
        EmployeeServiceUnavailableException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", 503);
    error.put("error", "Service Unavailable");
    error.put("message", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(DuplicateAssignmentException.class)
public ResponseEntity<Map<String, Object>> handleDuplicateAssignment(
        DuplicateAssignmentException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", 409);
    error.put("error", "Conflict");
    error.put("message", ex.getMessage());

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<Map<String, Object>> handleConstraintViolation(
        ConstraintViolationException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", 400);
    error.put("error", "Bad Request");
    error.put("message", ex.getMessage());

    return ResponseEntity.badRequest().body(error);
}

// Handles the specified exception and converts it into an HTTP response.
@ExceptionHandler(HandlerMethodValidationException.class)
public ResponseEntity<Map<String, Object>> handleMethodValidation(
        HandlerMethodValidationException ex) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", 400);
    error.put("error", "Bad Request");
    error.put("message", "Invalid request parameter");

    return ResponseEntity.badRequest().body(error);
}
}
