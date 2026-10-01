/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeRetryGateway.java
 * Purpose: Gateway/resilience layer: wraps remote-service calls with retry/circuit-breaker behavior.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.gateway;

import com.company.project_service.client.EmployeeClient;
import com.company.project_service.dto.EmployeeResponse;
import com.company.project_service.exception.EmployeeNotFoundException;

import feign.FeignException;

import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.stereotype.Service;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class EmployeeRetryGateway {

    // Dependency/state used by this class. Constructor injection supplies `employeeClient` when the class is created.
    private final EmployeeClient employeeClient;

    public EmployeeRetryGateway(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    // Retries a failed remote call according to the configured retry policy.
    @Retry(name = "employeeRetry")
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public EmployeeResponse getEmployee(Long employeeId) {

        try {

            return employeeClient.getEmployeeById(employeeId);

        } catch (FeignException.NotFound ex) {

            throw new EmployeeNotFoundException(
                    "Employee not found with id: " + employeeId
            );
        }
    }
}
