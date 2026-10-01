/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: Employee.java
 * Purpose: Entity layer: represents data persisted in the database.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.entity;

import jakarta.persistence.*;

// Marks this class as a JPA entity that can be stored in a database table.
@Entity
// Configures the database table used by this JPA entity.
@Table(name = "employees") // Table name in the database
// This declaration defines the main type represented by this source file.
public class Employee {

    // Marks the field below as the primary key of the entity.
    @Id
    // Tells JPA how the primary-key value should be generated.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //private String email; below code is for validation and unique constraint

    // Configures how the field below maps to a database column.
    @Column(nullable = false, unique = true)
    private String email;

    private String designation;

    private Double salary;

    // Configures how the field below maps to a database column.
    @Column(name = "department_id")
    private Long departmentId;

    public Employee() {
    }

    public Employee(
            String name,
            String email,
            String designation,
            Double salary
    ) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.salary = salary;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Long getId() {
        return id;
    }

    /**
     * Handles the `setId` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getName() {
        return name;
    }

    /**
     * Handles the `setName` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Handles the `setEmail` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Handles the `setDesignation` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Handles the `setSalary` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Long getDepartmentId() {
    return departmentId;
}

public void setDepartmentId(
        Long departmentId
) {
    this.departmentId = departmentId;
}
}
