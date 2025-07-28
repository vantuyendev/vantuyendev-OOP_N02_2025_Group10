package com.shopmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot Application class
 * Khởi tạo ứng dụng web quản lý cửa hàng với Spring Boot
 */
@SpringBootApplication
public class Start {

    /**
     * Phương thức main - điểm khởi đầu của Spring Boot application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting Shop Management System with Spring Boot...");
        SpringApplication.run(Start.class, args);
    }

    /**
     * CORS Configuration để cho phép các request từ frontend
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
