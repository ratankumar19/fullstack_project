/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: Department.java
 * Purpose: Entity layer: represents data persisted in the database.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.department.entity;

import jakarta.persistence.*;

// Marks this class as a JPA entity that can be stored in a database table.
@Entity
// Configures the database table used by this JPA entity.
@Table(
        name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_department_code",
                        columnNames = "code"
                )
        }
)
// This declaration defines the main type represented by this source file.
public class Department {

    // Marks the field below as the primary key of the entity.
    @Id
    // Tells JPA how the primary-key value should be generated.
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private String name;

    // Configures how the field below maps to a database column.
    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    public Department() {
    }

    public Department(
            String name,
            String code,
            String description
    ) {
        this.name = name;
        this.code = code;
        this.description = description;
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
    public String getCode() {
        return code;
    }

    /**
     * Handles the `setCode` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }
}
