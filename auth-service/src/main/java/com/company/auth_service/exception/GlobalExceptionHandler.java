
package com.company.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestControllerAdvice
// This declaration defines the main type represented by this source file.
public class GlobalExceptionHandler {

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiError> handleDuplicateEmail(
            DuplicateEmailException ex
    ) {
        return buildError(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }


    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(InvalidCredentialsException.class)
    /**
     * Handles the `handleInvalidCredentials` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException ex
    ) {
        return buildError(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                    error.getField() + ": " +
                    error.getDefaultMessage()
                )
                .findFirst()
                .orElse("Invalid request");

        return buildError(
                HttpStatus.BAD_REQUEST,
                message
        );
    }

    // Handles the specified exception and converts it into an HTTP response.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDatabaseConflict(
            DataIntegrityViolationException ex
    ) {
        return buildError(
                HttpStatus.CONFLICT,
                "A database constraint was violated"
        );
    }

    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message
    ) {

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }
}
