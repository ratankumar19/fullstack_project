/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentMapper.java
 * Purpose: Mapper layer: converts between database entities and API DTOs.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.mapper;

import com.company.department.dto.DepartmentRequest;
import com.company.department.dto.DepartmentResponse;
import com.company.department.entity.Department;

import org.springframework.stereotype.Component;

@Component
// This declaration defines the main type represented by this source file.
public class DepartmentMapper {

    public Department toEntity(
            DepartmentRequest request
    ) {

        Department department =
                new Department();

        department.setName(
                request.getName()
        );

        department.setCode(
                request.getCode()
        );

        department.setDescription(
                request.getDescription()
        );

        return department;
    }

    public DepartmentResponse toResponse(
            Department department
    ) {

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getDescription()
        );
    }

    public void updateEntity(
            Department department,
            DepartmentRequest request
    ) {

        department.setName(
                request.getName()
        );

        department.setCode(
                request.getCode()
        );

        department.setDescription(
                request.getDescription()
        );
    }
}
