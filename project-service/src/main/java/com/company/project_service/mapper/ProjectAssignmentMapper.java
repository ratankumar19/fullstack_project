/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectAssignmentMapper.java
 * Purpose: Mapper layer: converts between database entities and API DTOs.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.mapper;

import com.company.project_service.dto.ProjectAssignmentResponse;
import com.company.project_service.entity.ProjectAssignment;

import org.springframework.stereotype.Component;

@Component
// This declaration defines the main type represented by this source file.
public class ProjectAssignmentMapper {

    public ProjectAssignmentResponse mapToResponse(
            ProjectAssignment assignment) {

        return ProjectAssignmentResponse.builder()
                .id(assignment.getId())
                .employeeId(assignment.getEmployeeId())
                .projectId(assignment.getProject().getId())
                .assignedAt(assignment.getAssignedAt())
                .build();
    }
}
