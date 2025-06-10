package com.madhu.vendor.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValidator {

    private final ResourceLoader resourceLoader;
    private final String propertiesLocation;

    public PropertiesValidator(ResourceLoader resourceLoader,
            @Value("${app.config.file:classpath:application.properties}") String propertiesLocation) {
        this.resourceLoader = resourceLoader;
        this.propertiesLocation = propertiesLocation;
    }

    @PostConstruct
    public void validatePropertiesFile() {
        Resource resource = resourceLoader.getResource(propertiesLocation);
        if (!resource.exists()) {
            throw new IllegalStateException("Required properties file missing: " + propertiesLocation);
        }
    }
}
