/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentCircuitBreakerGateway.java
 * Purpose: Gateway/resilience layer: wraps remote-service calls with retry/circuit-breaker behavior.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.gateway;

import com.company.project_service.dto.DepartmentResponse;

import com.company.project_service.exception.DepartmentNotFoundException;
import com.company.project_service.exception.DepartmentServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class DepartmentCircuitBreakerGateway {

    // Dependency/state used by this class. Constructor injection supplies `retryGateway` when the class is created.
    private final DepartmentRetryGateway retryGateway;

    public DepartmentCircuitBreakerGateway(
            DepartmentRetryGateway retryGateway) {

        this.retryGateway = retryGateway;
    }

    // Protects the remote call with a circuit breaker to avoid repeatedly calling an unhealthy dependency.
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "departmentFallback"
    )
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public DepartmentResponse getDepartment(Long departmentId) {

        System.out.println("Calling Department Service...");

        return retryGateway.getDepartment(departmentId);
    }

    public DepartmentResponse departmentFallback(
            Long departmentId,
            Throwable ex) {

        System.out.println("===== CIRCUIT BREAKER FALLBACK CALLED =====");

        System.out.println(
                "Original exception: " + ex.getClass().getName()
        );

        if (ex instanceof DepartmentNotFoundException notFound) {
            throw notFound;
        }

        throw new DepartmentServiceUnavailableException(
                "Department Service is currently unavailable. Please try again later.",
                ex
        );
    }
}
