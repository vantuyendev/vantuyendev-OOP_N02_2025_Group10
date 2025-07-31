package com.shopmanagement;

import com.formdev.flatlaf.FlatLightLaf;
import com.shopmanagement.activity.LoginActivity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

/**
 * Main Spring Boot Application class
 * Combined Web and Desktop Shop Management System
 */
@SpringBootApplication
public class ShopManagementApplication {

    private static ConfigurableApplicationContext applicationContext;

    /**
     * Main method - entry point for the application
     * @param args command line arguments
     *             Use --mode=web for web application
     *             Use --mode=desktop for desktop application
     *             Default is web mode
     */
    public static void main(String[] args) {
        // Determine application mode
        String mode = getApplicationMode(args);
        
        if ("desktop".equals(mode)) {
            startDesktopMode(args);
        } else {
            startWebMode(args);
        }
    }
    
    /**
     * Start application in desktop mode
     */
    private static void startDesktopMode(String[] args) {
        // Set up Look and Feel for Swing
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf Look and Feel: " + e.getMessage());
        }

        // Configure for desktop mode
        System.setProperty("java.awt.headless", "false");
        System.setProperty("spring.main.web-application-type", "none");
        
        System.out.println("Starting Shop Management Desktop System with Spring Boot...");
        
        // Start Spring Boot context
        applicationContext = SpringApplication.run(ShopManagementApplication.class, args);
        
        // Launch GUI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                LoginActivity loginActivity = applicationContext.getBean(LoginActivity.class);
                loginActivity.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                // Fallback - create LoginActivity directly if can't get from Spring context
                new LoginActivity().setVisible(true);
            }
        });
    }
    
    /**
     * Start application in web mode
     */
    private static void startWebMode(String[] args) {
        System.setProperty("spring.main.web-application-type", "servlet");
        System.out.println("Starting Shop Management Web System with Spring Boot...");
        
        // Start Spring Boot web application
        applicationContext = SpringApplication.run(ShopManagementApplication.class, args);
        
        System.out.println("Shop Management Web System started successfully!");
        System.out.println("Access the application at: http://localhost:8080");
    }
    
    /**
     * Extract application mode from command line arguments
     */
    private static String getApplicationMode(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--mode=")) {
                return arg.substring("--mode=".length());
            }
        }
        return "web"; // Default to web mode
    }

    /**
     * Get the Spring application context
     * @return the application context
     */
    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Shutdown the application gracefully
     */
    public static void shutdown() {
        if (applicationContext != null) {
            System.out.println("Shutting down Shop Management System...");
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }
}
