package com.shopmanagement.activity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.shopmanagement.model.User;
import com.shopmanagement.util.DesktopUtils;
import com.shopmanagement.ShopManagementApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * LoginActivity - Simple login interface for desktop application
 */
@Component
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
public class LoginActivity extends JFrame implements ActionListener {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    
    public LoginActivity() {
        super("Shop Management System - Login");
        initializeComponents();
        layoutComponents();
        setupWindow();
    }
    
    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        // Set Enter key for password field
        passwordField.addActionListener(this);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Shop Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        
        // Add panels to main frame
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton || e.getSource() == passwordField) {
            performLogin();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            DesktopUtils.showErrorMessage(this, "Please enter both username and password.", "Login Error");
            return;
        }
        
        try {
            // Basic authentication check
            int status = User.checkStatus(username, password);
            if (status == 0 || status == 1) {
                // Login successful
                this.setVisible(false);
                this.dispose();
                
                // Open dashboard
                try {
                    DashboardActivity dashboard = ShopManagementApplication.getApplicationContext().getBean(DashboardActivity.class);
                    dashboard.setVisible(true);
                } catch (Exception ex) {
                    // Fallback
                    new DashboardActivity().setVisible(true);
                }
            } else {
                DesktopUtils.showErrorMessage(this, "Invalid username or password.", "Login Error");
            }
        } catch (Exception ex) {
            DesktopUtils.showErrorMessage(this, "Login error: " + ex.getMessage(), "Error");
            ex.printStackTrace();
        }
    }
}
