package com.shopmanagement;

import com.formdev.flatlaf.FlatLightLaf;
import com.shopmanagement.desktop.ui.LoginFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

/**
 * Main Spring Boot Application class
 * Combined Web and Desktop Shop Management System
 * 
 * This application can run in two modes:
 * - Web mode: Traditional Spring Boot web application
 * - Desktop mode: Swing-based desktop application with Spring Boot backend
 */
@SpringBootApplication
public class ShopManagementApplication {

    private static ConfigurableApplicationContext applicationContext;

    /**
     * Main method - entry point for the application
     * @param args command line arguments
     *             Use --mode=web for web application (default)
     *             Use --mode=desktop for desktop application
     */
    public static void main(String[] args) {
        // Determine application mode from command line arguments
        String mode = getApplicationMode(args);
        
        System.out.println("Starting Shop Management System in " + mode + " mode...");
        
        if ("desktop".equals(mode)) {
            startDesktopMode(args);
        } else {
            startWebMode(args);
        }
    }
    
    /**
     * Start application in desktop mode with Swing UI
     */
    private static void startDesktopMode(String[] args) {
        // Set up modern Look and Feel for Swing
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf Look and Feel: " + e.getMessage());
            // Continue without system look and feel fallback
        }

        // Configure Spring Boot for desktop mode (no web server)
        System.setProperty("java.awt.headless", "false");
        System.setProperty("spring.main.web-application-type", "none");
        
        // Start Spring Boot application context
        applicationContext = SpringApplication.run(ShopManagementApplication.class, args);
        
        // Launch desktop GUI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Get login frame from Spring context or create new one
                LoginFrame loginFrame = getBean(LoginFrame.class);
                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                } else {
                    new LoginFrame().setVisible(true);
                }
                System.out.println("Desktop application launched successfully!");
            } catch (Exception e) {
                System.err.println("Failed to launch desktop application: " + e.getMessage());
                e.printStackTrace();
                // Fallback - create LoginFrame directly
                new LoginFrame().setVisible(true);
            }
        });
    }
    
    /**
     * Start application in web mode with embedded server
     */
    private static void startWebMode(String[] args) {
        // Configure Spring Boot for web mode
        System.setProperty("spring.main.web-application-type", "servlet");
        
        // Start Spring Boot web application
        applicationContext = SpringApplication.run(ShopManagementApplication.class, args);
        
        System.out.println("Web application started successfully!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("Shop dashboard: http://localhost:8080/shop/");
    }
    
    /**
     * Extract application mode from command line arguments
     * @param args command line arguments
     * @return "web" or "desktop", defaults to "web"
     */
    private static String getApplicationMode(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--mode=")) {
                return arg.substring("--mode=".length()).toLowerCase();
            }
        }
        return "web"; // Default to web mode
    }

    /**
     * Get a Spring bean by class type
     * @param clazz the class type
     * @return the bean instance or null if not found
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext != null ? applicationContext.getBean(clazz) : null;
        } catch (Exception e) {
            return null;
        }
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
