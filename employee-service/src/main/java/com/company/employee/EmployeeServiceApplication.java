/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeServiceApplication.java
 * Purpose: Application bootstrap/infrastructure class used to start or configure this microservice.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



// Main Spring Boot annotation: enables configuration, component scanning, and auto-configuration.
@SpringBootApplication
//it gives the same effect as the below three annotations combined
// @SpringBootConfiguration
// @EnableAutoConfiguration
// @ComponentScan


// Enables scanning and runtime creation of OpenFeign clients.
@EnableFeignClients
// This declaration defines the main type represented by this source file.
public class EmployeeServiceApplication {

 /**
  * Application entry point. SpringApplication.run(...) creates the Spring context and starts the embedded server.
  */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}
