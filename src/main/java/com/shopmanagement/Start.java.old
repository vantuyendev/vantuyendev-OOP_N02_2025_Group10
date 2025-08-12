package com.shopmanagement;

import com.formdev.flatlaf.FlatLightLaf;
import com.shopmanagement.activity.LoginActivity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

/**
 * Main Spring Boot Desktop Application class
 * Khởi tạo ứng dụng desktop quản lý cửa hàng với Spring Boot và Swing
 */
@SpringBootApplication
public class Start {

    private static ConfigurableApplicationContext applicationContext;

    /**
     * Phương thức main - điểm khởi đầu của Spring Boot desktop application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Thiết lập Look and Feel cho Swing
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf Look and Feel: " + e.getMessage());
        }

        // Thiết lập hệ thống không hiển thị web server
        System.setProperty("java.awt.headless", "false");
        System.setProperty("spring.main.web-application-type", "none");
        
        System.out.println("Starting Shop Management Desktop System with Spring Boot...");
        
        // Khởi tạo Spring Boot context
        applicationContext = SpringApplication.run(Start.class, args);
        
        // Khởi chạy GUI trong Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                LoginActivity loginActivity = applicationContext.getBean(LoginActivity.class);
                loginActivity.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                // Fallback - tạo LoginActivity trực tiếp nếu không lấy được từ Spring context
                new LoginActivity().setVisible(true);
            }
        });
    }

    /**
     * Lấy Spring Application Context
     * @return ConfigurableApplicationContext
     */
    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
