package com.optimagrowth.license.repository;

import java.util.List;
import java.util.Optional; // Added Optional for single-result finds

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.optimagrowth.license.model.License;

/**
 * JPA Repository interface for License entities.
 * Extends CrudRepository, specifying License as the entity and String as the ID type.
 * Includes custom methods for finding licenses by organization ID and specific license ID.
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * Finds all License entities associated with a given organization ID.
     * @param organizationId The organization identifier.
     * @return A list of matching License entities.
     */
    public List<License> findByOrganizationId(String organizationId);

    /**
     * Finds a single License entity by both organization ID and license ID.
     * Uses Optional as the return type for safe null handling.
     * @param organizationId The organization identifier.
     * @param licenseId The license identifier.
     * @return An Optional containing the matching License entity, or empty if not found.
     */
    public Optional<License> findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
