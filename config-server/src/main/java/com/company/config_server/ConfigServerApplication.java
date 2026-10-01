/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ConfigServerApplication.java
 * Purpose: Application bootstrap/infrastructure class used to start or configure this microservice.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

// Turns this application into a Spring Cloud Config Server.
@EnableConfigServer
// Main Spring Boot annotation: enables configuration, component scanning, and auto-configuration.
@SpringBootApplication
// This declaration defines the main type represented by this source file.
public class ConfigServerApplication {

    /**
     * Application entry point. SpringApplication.run(...) creates the Spring context and starts the embedded server.
     */
    public static void main(String[] args) {

        SpringApplication.run(
                ConfigServerApplication.class,
                args
        );
    }
}
