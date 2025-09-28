package com.optimagrowth.license.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable; // <--- ADDED: Needed for JPA entities

/**
 * Represents the License Entity used for persistence with a MySQL database
 * via Spring Data JPA.
 *
 * This entity extends RepresentationModel for HATEOAS support.
 */
@Getter @Setter @ToString
@Entity
@Table(name="licenses")
public class License extends RepresentationModel<License> implements Serializable { // <--- ADDED: Implements Serializable

    // Recommended for Serializable classes
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    // ADDED: Explicit @Column annotation for the description field
    @Column(name = "description")
    private String description;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name="comment")
    private String comment;

    // Default constructor required by JPA
    public License() {
        // Default constructor
    }

    /**
     * Helper method to set the comment field for method chaining.
     * @param comment The comment text.
     * @return The License object.
     */
    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
