/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeGateway.java
 * Purpose: Gateway/resilience layer: wraps remote-service calls with retry/circuit-breaker behavior.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.gateway;

import com.company.project_service.dto.EmployeeResponse;

import com.company.project_service.exception.EmployeeNotFoundException;
import com.company.project_service.exception.EmployeeServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class EmployeeGateway {

    // Dependency/state used by this class. Constructor injection supplies `retryGateway` when the class is created.
    private final EmployeeRetryGateway retryGateway;

    public EmployeeGateway(EmployeeRetryGateway retryGateway) {
        this.retryGateway = retryGateway;
    }

    // Protects the remote call with a circuit breaker to avoid repeatedly calling an unhealthy dependency.
    @CircuitBreaker(
            name = "employeeService",
            fallbackMethod = "employeeFallback"
    )
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public EmployeeResponse getEmployee(Long employeeId) {

        EmployeeResponse employee =
                retryGateway.getEmployee(employeeId);

        if (employee == null || employee.getId() == null) {

            throw new EmployeeServiceUnavailableException(
                    "Employee Service returned an invalid response",
                    null
            );
        }

        return employee;
    }

    public EmployeeResponse employeeFallback(
            Long employeeId,
            Throwable ex) {

        System.out.println(
                "Employee Circuit Breaker fallback: "
                        + ex.getClass().getSimpleName()
        );

        if (ex instanceof EmployeeNotFoundException notFound) {
            throw notFound;
        }

        throw new EmployeeServiceUnavailableException(
                "Employee Service is currently unavailable. Please try again later.",
                ex
        );
    }
}
