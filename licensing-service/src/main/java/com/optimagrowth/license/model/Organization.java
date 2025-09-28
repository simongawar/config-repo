package com.optimagrowth.license.model;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; // Recommended to explicitly include a no-arg constructor
import lombok.ToString;

/**
 * Represents the Organization Entity for persistence with a database 
 * via Spring Data JPA, and extends RepresentationModel for HATEOAS support.
 */
@Getter @Setter @ToString
@NoArgsConstructor // Lombok annotation for a default constructor (required by JPA)
@Entity // Marks this class as a JPA entity
@Table(name="organizations") // Maps the entity to a database table named 'organizations'
public class Organization extends RepresentationModel<Organization> implements Serializable {

    // Recommended for Serializable classes
    private static final long serialVersionUID = 1L;

    @Id // Designates this field as the primary key
    @Column(name = "organization_id", nullable = false)
    String id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "contact_name")
    String contactName;

    @Column(name = "contact_email")
    String contactEmail;

    @Column(name = "contact_phone")
    String contactPhone;
    
    // Note: No need for an explicit default constructor if using @NoArgsConstructor
}