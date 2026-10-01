/*
 * BEGINNER-FRIENDLY WALKTHROUGH
 * File: EmployeeRepository.java
 * Purpose: Repository layer: provides database access through Spring Data JPA.
 *
 * Reading tip: annotations beginning with @ give instructions to Spring/JPA;
 * constructors receive dependencies; public methods expose the main operations;
 * and return statements send the final result back to the caller.
 */
package com.company.employee.repository;

import com.company.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// This declaration defines the main type represented by this source file.
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

        //new custom query 
    boolean existsByEmail(String email);
//if email exists for another employee with a different id, 
// return true in case we r doing update 
     boolean existsByEmailAndIdNot(
            String email,
            Long id
    );
}
