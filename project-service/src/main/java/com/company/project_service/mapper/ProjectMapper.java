/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectMapper.java
 * Purpose: Mapper layer: converts between database entities and API DTOs.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.mapper;

import com.company.project_service.dto.ProjectRequest;
import com.company.project_service.dto.ProjectResponse;
import com.company.project_service.entity.Project;

import org.springframework.stereotype.Component;

@Component
// This declaration defines the main type represented by this source file.
public class ProjectMapper {

    /**
     * Converts one application object representation into another.
     */
    public Project mapToEntity(ProjectRequest request) {

        return Project.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .status(request.getStatus())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .departmentId(request.getDepartmentId())
                .build();
    }

    /**
     * Converts one application object representation into another.
     */
    public ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .code(project.getCode())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .departmentId(project.getDepartmentId())
                .build();
    }
}
