/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ConfigTestController.java
 * Purpose: Controller layer: receives HTTP requests, validates input, calls the service layer, and returns HTTP responses.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// This declaration defines the main type represented by this source file.
public class ConfigTestController {

    // Injects a configuration/property value into the field or parameter below.
    @Value("${company.service.config-test}")
    private String configValue;

    // Maps an HTTP GET request to the method below.
    @GetMapping("/api/config-test")
    /**
     * Handles the `testConfig` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public String testConfig() {

        return configValue;
    }
}
