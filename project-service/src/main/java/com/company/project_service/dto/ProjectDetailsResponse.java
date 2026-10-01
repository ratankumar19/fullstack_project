/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectDetailsResponse.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.dto;

import com.company.project_service.entity.ProjectStatus;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// This declaration defines the main type represented by this source file.
public class ProjectDetailsResponse {

    private Long id;

    private String name;

    private String code;

    private String description;

    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long departmentId;

    private List<EmployeeResponse> employees;
}
