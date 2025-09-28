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

    public License getLicense(String licenseId, String organizationId){
        // FIX 1: The repository method is assumed to return Optional<License>.
        Optional<License> licenseOptional = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (!licenseOptional.isPresent()) {
            // FIX 5 (Part A): Correctly use MessageSource to format the error message.
            String errorMessage = messages.getMessage(
                "license.search.error.message", 
                new Object[] {licenseId, organizationId}, // Pass arguments
                null // Locale
            );
            throw new IllegalArgumentException(errorMessage);
        }

        License license = licenseOptional.get();

        // FIX 2 & 3: Call the correct config getter (e.g., getExampleProperty) and 
        // FIX 4 (Part A): Use a setter if withComment() doesn't exist.
        license.setComment(config.getExampleProperty()); 
        
        return license;
    }

    public License createLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        // FIX 2 & 4: Call the correct config getter (e.g., getExampleProperty) and 
        // use a setter if withComment() doesn't exist.
        license.setComment(config.getExampleProperty());
        
        return license;
    }

    public License updateLicense(License license){
        licenseRepository.save(license);

        // FIX 2 & 4: Call the correct config getter (e.g., getExampleProperty) and 
        // use a setter if withComment() doesn't exist.
        license.setComment(config.getExampleProperty());
        
        return license;
    }

    public String deleteLicense(String licenseId){
        licenseRepository.deleteById(licenseId);
        
        // FIX 5 (Part B): Correctly use MessageSource to format the deletion message.
        String responseMessage = messages.getMessage(
            "license.delete.message", 
            new Object[] {licenseId}, // Pass licenseId as the argument
            null // Locale
        );
        return responseMessage;
    }
}