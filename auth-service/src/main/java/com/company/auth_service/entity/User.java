
package com.company.auth_service.entity;

import jakarta.persistence.*;

// Marks this class as a JPA entity that can be stored in a database table.
@Entity
// Configures the database table used by this JPA entity.
@Table(name = "users")
// This declaration defines the main type represented by this source file.
public class User {

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
    private String email;

    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    // Configures how the field below maps to a database column.
    @Column(nullable = false)
    private Role role = Role.EMPLOYEE;

    public User() {}

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Long getId() {
        return id;
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
    public String getPassword() {
        return password;
    }

    /**
     * Handles the `setPassword` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Handles the `setRole` operation. Read the statements inside in order: inputs are received, required work is performed, and the result is returned.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
