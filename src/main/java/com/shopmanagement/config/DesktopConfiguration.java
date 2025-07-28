package com.shopmanagement.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Desktop Application Configuration
 * Cấu hình Spring Boot cho desktop application
 */
@Configuration
@ComponentScan(basePackages = {
    "com.shopmanagement.activity",
    "com.shopmanagement.service",
    "com.shopmanagement.repository",
    "com.shopmanagement.util"
})
@EntityScan(basePackages = "com.shopmanagement.entity")
@EnableJpaRepositories(basePackages = "com.shopmanagement.repository")
public class DesktopConfiguration {
    
    // Additional configuration beans can be added here if needed
    
}
