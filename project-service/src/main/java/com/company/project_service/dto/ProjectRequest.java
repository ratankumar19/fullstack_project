/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectRequest.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.dto;

import com.company.project_service.entity.ProjectStatus;
//import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// This declaration defines the main type represented by this source file.
public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    @NotBlank(message = "Project code is required")
    private String code;

    private String description;

    @NotNull(message = "Project status is required")
    private ProjectStatus status;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be greater than 0")
    private Long departmentId;
}
