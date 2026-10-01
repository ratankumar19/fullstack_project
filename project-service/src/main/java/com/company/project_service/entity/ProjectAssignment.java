/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectAssignment.java
 * Purpose: Entity layer: represents data persisted in the database.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Marks this class as a JPA entity that can be stored in a database table.
@Entity
// Configures the database table used by this JPA entity.
@Table(
    name = "project_assignments",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_employee_project",
            columnNames = {"employee_id", "project_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// This declaration defines the main type represented by this source file.
public class ProjectAssignment {

    // Marks the field below as the primary key of the entity.
    @Id
    // Tells JPA how the primary-key value should be generated.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Configures how the field below maps to a database column.
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Configures how the field below maps to a database column.
    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    /**
     * Handles the `prePersist` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void prePersist() {
        if (assignedAt == null) {
            assignedAt = LocalDateTime.now();
        }
    }
}
