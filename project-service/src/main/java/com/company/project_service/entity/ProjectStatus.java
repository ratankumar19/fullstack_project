/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: ProjectStatus.java
 * Purpose: Entity layer: represents data persisted in the database.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.entity;

// This declaration defines the main type represented by this source file.
public enum ProjectStatus {

    PLANNED,
    ACTIVE,
    ON_HOLD,
    COMPLETED,
    CANCELLED
}
