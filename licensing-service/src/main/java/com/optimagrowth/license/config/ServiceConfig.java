package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class designed to fetch the 'example.property'
 * from Spring Cloud Config Server (or local environment) using 
 * the modern @ConfigurationProperties approach.
 */
@Configuration
@ConfigurationProperties(prefix = "example")
@Getter @Setter
public class ServiceConfig {

    // This property will be populated from application.properties or the Config Server.
    // The key expected in the configuration file is 'example.property'.
    private String property;

    /**
     * Provides the configured property value, resolving the 'getProperty()'
     * method call used elsewhere (like in the LicenseService).
     * Since Lombok is used, this method is generated automatically.
     * @return The configured string property.
     */
    public String getProperty(){
        return property;
    }
}
