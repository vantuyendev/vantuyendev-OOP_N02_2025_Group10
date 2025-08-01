package com.shopmanagement.desktop.ui;

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
 * Modern Login Frame for desktop application
 * Provides authentication interface with improved UI/UX
 */
@Component
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
public class LoginFrame extends JFrame implements ActionListener {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    private JLabel titleLabel, usernameLabel, passwordLabel;

    public LoginFrame() {
        super("Shop Management System - Login");
        initializeComponents();
        layoutComponents();
        setupWindow();
    }

    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        // Text fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        // Buttons
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        
        // Labels
        titleLabel = new JLabel("Shop Management System");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        
        // Setup fonts and styles
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        
        // Button styling
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        loginButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        
        // Event handlers
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        passwordField.addActionListener(this); // Allow Enter key to login
        
        // Set preferred sizes
        Dimension fieldSize = new Dimension(250, 35);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        
        Dimension buttonSize = new Dimension(100, 35);
        loginButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
    }

    /**
     * Layout all components
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(Color.WHITE);
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Username row
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);
        
        // Password row
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    /**
     * Setup window properties
     */
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Set window icon if available
        try {
            // You can add an icon here if you have one
            // setIconImage(Toolkit.getDefaultToolkit().getImage("path/to/icon.png"));
        } catch (Exception e) {
            // Icon loading failed, continue without icon
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton || e.getSource() == passwordField) {
            performLogin();
        } else if (e.getSource() == exitButton) {
            exitApplication();
        }
    }

    /**
     * Perform login authentication
     */
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            DesktopUtils.showErrorMessage(this, "Please enter both username and password.", "Login Error");
            return;
        }
        
        try {
            // Authenticate user
            int status = User.checkStatus(username, password);
            if (status == 0 || status == 1) {
                // Login successful
                loginSuccessful();
            } else {
                DesktopUtils.showErrorMessage(this, "Invalid username or password.", "Login Error");
                clearPassword();
            }
        } catch (Exception ex) {
            DesktopUtils.showErrorMessage(this, "Login error: " + ex.getMessage(), "Error");
            ex.printStackTrace();
            clearPassword();
        }
    }

    /**
     * Handle successful login
     */
    private void loginSuccessful() {
        this.setVisible(false);
        this.dispose();
        
        // Open main dashboard
        SwingUtilities.invokeLater(() -> {
            try {
                DashboardFrame dashboard = ShopManagementApplication.getBean(DashboardFrame.class);
                if (dashboard != null) {
                    dashboard.setVisible(true);
                } else {
                    new DashboardFrame().setVisible(true);
                }
            } catch (Exception ex) {
                System.err.println("Failed to open dashboard: " + ex.getMessage());
                // Fallback - you might want to create a simple dashboard here
                DesktopUtils.showErrorMessage(null, "Failed to open dashboard: " + ex.getMessage(), "Error");
            }
        });
    }

    /**
     * Clear password field for security
     */
    private void clearPassword() {
        passwordField.setText("");
        passwordField.requestFocus();
    }

    /**
     * Exit the application
     */
    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            ShopManagementApplication.shutdown();
            System.exit(0);
        }
    }
}
