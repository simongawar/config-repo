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

    /**
     * Retrieves a specific license by organizationId and licenseId.
     * HATEOAS links are added to the response for related actions.
     * * @param organizationId The organization identifier from the URL path.
     * @param licenseId The license identifier from the URL path.
     * @return ResponseEntity containing the License object with HATEOAS links.
     */
    @GetMapping(value="/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId);
        
        // Add HATEOAS links to the License object
        license.add( 
                // Self link (GET)
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                // Create link (POST)
                linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
                // Update link (PUT)
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
                // Delete link (DELETE)
                linkTo(methodOn(LicenseController.class).deleteLicense(organizationId, license.getLicenseId())).withRel("deleteLicense")
        );
        
        return ResponseEntity.ok(license);
    }

    /**
     * Updates an existing license via a PUT request.
     * * @param request The License object to update from the request body.
     * @return ResponseEntity containing the updated License object.
     */
    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }
    
    /**
     * Creates a new license via a POST request.
     * * @param request The License object to create from the request body.
     * @return ResponseEntity containing the created License object and HTTP status 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return new ResponseEntity<>(licenseService.createLicense(request), HttpStatus.CREATED);
    }

    /**
     * Deletes a license by its ID via a DELETE request.
     * Note: OrganizationId is required in the path but not used in the service method here, 
     * but we pass it into the method signature to make the HATEOAS link resolution correct.
     * * @param organizationId The organization identifier from the URL path (for HATEOAS link).
     * @param licenseId The license identifier from the URL path.
     * @return ResponseEntity containing the deletion status message.
     */
    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }
}
