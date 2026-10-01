/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: Project.java
 * Purpose: Entity layer: represents data persisted in the database.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Marks this class as a JPA entity that can be stored in a database table.
@Entity
// Configures the database table used by this JPA entity.
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_project_code",
                        columnNames = "code"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// This declaration defines the main type represented by this source file.
public class Project {

    // Marks the field below as the primary key of the entity.
    @Id
    // Tells JPA how the primary-key value should be generated.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private String name;


    // Configures how the field below maps to a database column.
    @Column(nullable = false, unique = true)
    private String code;


    private String description;


    @Enumerated(EnumType.STRING)
    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private ProjectStatus status;


    private LocalDate startDate;


    private LocalDate endDate;


    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private Long departmentId;
}
