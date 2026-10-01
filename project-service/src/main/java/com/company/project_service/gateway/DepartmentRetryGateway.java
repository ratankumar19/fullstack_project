/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentRetryGateway.java
 * Purpose: Gateway/resilience layer: wraps remote-service calls with retry/circuit-breaker behavior.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.gateway;

import com.company.project_service.client.DepartmentClient;
import com.company.project_service.dto.DepartmentResponse;
import com.company.project_service.exception.DepartmentNotFoundException;

import feign.FeignException;

import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.stereotype.Service;

// Registers this class as a Spring service containing business logic.
@Service
// This declaration defines the main type represented by this source file.
public class DepartmentRetryGateway {

    // Dependency/state used by this class. Constructor injection supplies `departmentClient` when the class is created.
    private final DepartmentClient departmentClient;

    public DepartmentRetryGateway(
            DepartmentClient departmentClient) {

        this.departmentClient = departmentClient;
    }

    // Retries a failed remote call according to the configured retry policy.
    @Retry(name = "departmentRetry")
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public DepartmentResponse getDepartment(Long departmentId) {

        try {

            return departmentClient.getDepartmentById(departmentId);

        } catch (FeignException.NotFound ex) {

            throw new DepartmentNotFoundException(
                    "Department not found with id: " + departmentId
            );
        }
    }
}
