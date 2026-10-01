/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: GlobalExceptionHandler.java
 * Purpose: Exception layer: defines application errors and/or converts them into clear HTTP error responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestControllerAdvice
// This declaration defines the main type represented by this source file.
public class GlobalExceptionHandler {

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleResourceNotFound(
            ResourceNotFoundException exception
    ) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(
            DuplicateResourceException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleDuplicateResource(
            DuplicateResourceException exception
    ) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleValidation(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errors =
                new HashMap<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        errors
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
