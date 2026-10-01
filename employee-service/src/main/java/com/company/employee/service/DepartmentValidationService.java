/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentValidationService.java
 * Purpose: Service layer: contains business logic and coordinates repositories or other microservices.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.service;

import com.company.employee.client.DepartmentClient;
import com.company.employee.client.DepartmentResponse;
import com.company.employee.exception.DepartmentNotFoundException;
import com.company.employee.exception.DepartmentServiceUnavailableException;

import feign.FeignException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

import io.github.resilience4j.retry.annotation.Retry;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class DepartmentValidationService {

    // Dependency/state used by this class. Constructor injection supplies `departmentClient` when the class is created.
    private final DepartmentClient departmentClient;

    public DepartmentValidationService(
            DepartmentClient departmentClient
    ) {
        this.departmentClient = departmentClient;
    }

    // Protects the remote call with a circuit breaker to avoid repeatedly calling an unhealthy dependency.
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "departmentFallback"
    )
    // Retries a failed remote call according to the configured retry policy.
    @Retry(
            name = "departmentService",
            fallbackMethod = "departmentFallback"
    )
    public DepartmentResponse validateDepartment(
            Long departmentId
    ) {

        try {

            // Return the completed result to the caller of this service method.
            return departmentClient
                    .getDepartmentById(departmentId);

        } catch (FeignException.NotFound exception) {

            // Business rule failed, so throw a domain-specific exception for the global handler to translate.
            throw new DepartmentNotFoundException(
                    "Department not found with id: "
                            + departmentId
            );
        }
    }

    public DepartmentResponse departmentFallback(
            Long departmentId,
            Throwable throwable
    ) {

        /*
         * Don't convert our business 404/invalid-reference
         * case into "service unavailable".
         */
        if (throwable instanceof
                DepartmentNotFoundException) {

            throw (DepartmentNotFoundException)
                    throwable;
        }

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DepartmentServiceUnavailableException(
                "Department Service is currently unavailable"
        );
    }
}
