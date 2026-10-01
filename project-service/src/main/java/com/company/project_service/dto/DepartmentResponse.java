/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: DepartmentResponse.java
 * Purpose: DTO layer: defines request/response objects exchanged across API boundaries.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.project_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// This declaration defines the main type represented by this source file.
public class DepartmentResponse {

    private Long id;
    private String name;
    private String code;
}
