package com.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value="/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId);
        
        // FIX: Pass organizationId into methodOn for createLicense and updateLicense
        license.add( 
                // Self link (GET)
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                // Create link (POST) - FIX: Pass organizationId
                linkTo(methodOn(LicenseController.class).createLicense(organizationId, license)).withRel("createLicense"),
                // Update link (PUT) - FIX: Pass organizationId
                linkTo(methodOn(LicenseController.class).updateLicense(organizationId, license)).withRel("updateLicense"),
                // Delete link (DELETE)
                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId())).withRel("deleteLicense")
        );
        
        return ResponseEntity.ok(license);
    }

    /**
     * Updates an existing license via a PUT request.
     * FIX: Added @PathVariable("organizationId") to match the class-level @RequestMapping.
     */
    @PutMapping
    public ResponseEntity<License> updateLicense(
            // FIX: Added PathVariable
            @PathVariable("organizationId") String organizationId, 
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }
    
    /**
     * Creates a new license via a POST request.
     * FIX: Added @PathVariable("organizationId") to match the class-level @RequestMapping.
     */
    @PostMapping
    public ResponseEntity<License> createLicense(
            // FIX: Added PathVariable
            @PathVariable("organizationId") String organizationId, 
            @RequestBody License request) {
        return new ResponseEntity<>(licenseService.createLicense(request), HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }
}