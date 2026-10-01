/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeMapper.java
 * Purpose: Mapper layer: converts between database entities and API DTOs.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.mapper;

import com.company.employee.dto.EmployeeRequest;
import com.company.employee.dto.EmployeeResponse;
import com.company.employee.entity.Employee;
import org.springframework.stereotype.Component;

@Component
// This declaration defines the main type represented by this source file.
public class EmployeeMapper {

    public Employee toEntity(
            EmployeeRequest request
    ) {

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDesignation(
                request.getDesignation()
        );
        employee.setSalary(request.getSalary());
        employee.setDepartmentId(request.getDepartmentId());
        return employee;
    }

    public EmployeeResponse toResponse(
            Employee employee
    ) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());       
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setDesignation(employee.getDesignation());
        response.setSalary(employee.getSalary());
        response.setDepartmentId(employee.getDepartmentId());
        return response;
    }

    public void updateEntity(
            Employee employee,
            EmployeeRequest request
    ) {

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDesignation(
                request.getDesignation()
        );
        employee.setSalary(request.getSalary());
        employee.setDepartmentId(request.getDepartmentId());
    }
}
