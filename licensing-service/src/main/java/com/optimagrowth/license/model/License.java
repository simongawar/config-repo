package com.optimagrowth.license.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
@Entity
@Table(name="licenses")
public class License extends RepresentationModel<License> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    @Column(name = "description")
    private String description;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    
    // ----------------------------------------------------------------------
    // PROBABLE FIXES (Missing Fields 5, 6, 7):
    
    @Column(name = "organization_name", nullable = false)
    private String organizationName; // <-- FIX 5: Added missing organization name
    
    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;      // <-- FIX 6: Added license max count
    
    @Column(name = "cost")
    private String cost;             // <-- FIX 7: Added cost field

    // ----------------------------------------------------------------------

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