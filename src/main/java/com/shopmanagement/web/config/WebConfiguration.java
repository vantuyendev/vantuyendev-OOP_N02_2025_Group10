package com.shopmanagement.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Application Configuration
 * Configures Spring Boot components specifically for web mode
 */
@Configuration
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "servlet", matchIfMissing = true)
@ComponentScan(basePackages = {
    "com.shopmanagement.web.controller",
    "com.shopmanagement.service",
    "com.shopmanagement.repository",
    "com.shopmanagement.model"
})
@EntityScan(basePackages = "com.shopmanagement.entity")
@EnableJpaRepositories(basePackages = "com.shopmanagement.repository")
public class WebConfiguration implements WebMvcConfigurer {
    
    /**
     * Configure view controllers for simple redirects
     */
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // Redirect root to shop dashboard
        registry.addRedirectViewController("/", "/shop/");
        
        // Add other simple view controllers if needed
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/contact").setViewName("contact");
    }
    
    /**
     * Web-specific configuration can be added here
     * For example: interceptors, resource handlers, etc.
     */
    
}
