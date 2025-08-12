package com.shopmanagement.desktop.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Desktop Application Configuration
 * Configures Spring Boot components specifically for desktop mode
 */
@Configuration
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
@ComponentScan(basePackages = {
    "com.shopmanagement.desktop.ui",
    "com.shopmanagement.service",
    "com.shopmanagement.repository",
    "com.shopmanagement.util",
    "com.shopmanagement.model"
})
@EntityScan(basePackages = "com.shopmanagement.entity")
@EnableJpaRepositories(basePackages = "com.shopmanagement.repository")
public class DesktopConfiguration {
    
    /**
     * Desktop-specific configuration can be added here
     * For example: custom beans, theme configuration, etc.
     */
    
}
