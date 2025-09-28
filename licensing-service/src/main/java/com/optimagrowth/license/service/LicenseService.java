package com.optimagrowth.license.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.repository.LicenseRepository;

@Service
public class LicenseService {

    @Autowired
    MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;

    /**
     * Retrieves a license by ID and organization ID.
     * Uses Optional to handle the case where the license is not found,
     * throwing an IllegalArgumentException if missing.
     * @param licenseId The license ID.
     * @param organizationId The organization ID.
     * @return The found License object.
     */
    public License getLicense(String licenseId, String organizationId){
        // FIX 1: Use Optional.orElseThrow() to handle the result from the repository.
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
            .orElseThrow(() -> new IllegalArgumentException(
                // FIX 2: Fixed message argument formatting.
                String.format(messages.getMessage("license.search.error.message", null, null), licenseId, organizationId)
            ));

        // FIX 3: Use the correct getter for the configuration property (changed from getExampleProperty() to getProperty()).
        return license.withComment(config.getProperty());
    }

    /**
     * Creates and saves a new license.
     * @param license The license object to save.
     * @return The created License object.
     */
    public License createLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        // FIX 4: Use the correct getter for the configuration property (changed from getExampleProperty() to getProperty()).
        return license.withComment(config.getProperty());
    }

    /**
     * Updates an existing license.
     * @param license The license object to update.
     * @return The updated License object.
     */
    public License updateLicense(License license){
        licenseRepository.save(license);

        // FIX 5: Use the correct getter for the configuration property (changed from getExampleProperty() to getProperty()).
        return license.withComment(config.getProperty());
    }

    /**
     * Deletes a license by its ID.
     * @param licenseId The ID of the license to delete.
     * @return A response message confirming the deletion.
     */
    public String deleteLicense(String licenseId){
        // Note: For simplicity, delete() is used directly. A robust service would
        // first check for existence before deleting.
        licenseRepository.deleteById(licenseId);
        String responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
