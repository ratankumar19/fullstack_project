/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeClient.java
 * Purpose: Client layer: declares communication with another microservice.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.client;

import com.company.project_service.dto.EmployeeResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Creates a declarative HTTP client; Spring generates the implementation at runtime.
@FeignClient(
    name = "employee-service"
    //url = "${employee.service.url}"
)
// This declaration defines the main type represented by this source file.
public interface EmployeeClient {

    // Maps an HTTP GET request to the method below.
    @GetMapping("/api/employees/{id}")
    EmployeeResponse getEmployeeById(
            @PathVariable("id") Long id
    );
}
