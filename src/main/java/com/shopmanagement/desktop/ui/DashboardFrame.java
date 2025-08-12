package com.shopmanagement.desktop.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.shopmanagement.ShopManagementApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Main Dashboard Frame for desktop application
 * Central hub for all shop management operations
 */
@Component
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
public class DashboardFrame extends JFrame implements ActionListener {
    
    private JButton employeesButton, productsButton, customersButton;
    private JButton settingsButton, logoutButton;
    private JLabel titleLabel, welcomeLabel;
    private JPanel mainPanel, headerPanel, contentPanel, buttonPanel;

    public DashboardFrame() {
        super("Shop Management System - Dashboard");
        initializeComponents();
        layoutComponents();
        setupWindow();
    }

    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        // Labels
        titleLabel = new JLabel("Shop Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(70, 130, 180));
        
        welcomeLabel = new JLabel("Welcome! Choose an option to manage your shop:");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(100, 100, 100));
        
        // Main action buttons
        employeesButton = createDashboardButton("👥 Manage Employees", new Color(52, 152, 219));
        productsButton = createDashboardButton("📦 Manage Products", new Color(46, 204, 113));
        customersButton = createDashboardButton("🏪 Manage Customers", new Color(155, 89, 182));
        
        // Secondary buttons
        settingsButton = createSecondaryButton("⚙️ Settings", new Color(149, 165, 166));
        logoutButton = createSecondaryButton("🚪 Logout", new Color(231, 76, 60));
        
        // Add action listeners
        employeesButton.addActionListener(this);
        productsButton.addActionListener(this);
        customersButton.addActionListener(this);
        settingsButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }

    /**
     * Create a styled dashboard button
     */
    private JButton createDashboardButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(300, 80));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }

    /**
     * Create a styled secondary button
     */
    private JButton createSecondaryButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(140, 40));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }

    /**
     * Layout all components
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        JPanel welcomePanel = new JPanel(new FlowLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        
        headerPanel.add(titlePanel, BorderLayout.NORTH);
        headerPanel.add(welcomePanel, BorderLayout.SOUTH);
        
        // Content panel with main buttons
        contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(employeesButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(productsButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(customersButton, gbc);
        
        // Button panel for secondary actions
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        buttonPanel.add(settingsButton);
        buttonPanel.add(logoutButton);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    /**
     * Setup window properties
     */
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == employeesButton) {
            openEmployeeManagement();
        } else if (e.getSource() == productsButton) {
            openProductManagement();
        } else if (e.getSource() == customersButton) {
            openCustomerManagement();
        } else if (e.getSource() == settingsButton) {
            openSettings();
        } else if (e.getSource() == logoutButton) {
            logout();
        }
    }

    /**
     * Open employee management window
     */
    private void openEmployeeManagement() {
        try {
            JOptionPane.showMessageDialog(
                this,
                "Employee Management feature will be implemented here.",
                "Feature Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
            );
            // TODO: Implement EmployeeManagementFrame
        } catch (Exception ex) {
            showError("Failed to open Employee Management: " + ex.getMessage());
        }
    }

    /**
     * Open product management window
     */
    private void openProductManagement() {
        try {
            JOptionPane.showMessageDialog(
                this,
                "Product Management feature will be implemented here.",
                "Feature Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
            );
            // TODO: Implement ProductManagementFrame
        } catch (Exception ex) {
            showError("Failed to open Product Management: " + ex.getMessage());
        }
    }

    /**
     * Open customer management window
     */
    private void openCustomerManagement() {
        try {
            JOptionPane.showMessageDialog(
                this,
                "Customer Management feature will be implemented here.",
                "Feature Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
            );
            // TODO: Implement CustomerManagementFrame
        } catch (Exception ex) {
            showError("Failed to open Customer Management: " + ex.getMessage());
        }
    }

    /**
     * Open settings window
     */
    private void openSettings() {
        try {
            JOptionPane.showMessageDialog(
                this,
                "Settings feature will be implemented here.",
                "Feature Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
            );
            // TODO: Implement SettingsFrame
        } catch (Exception ex) {
            showError("Failed to open Settings: " + ex.getMessage());
        }
    }

    /**
     * Logout and return to login screen
     */
    private void logout() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            this.dispose();
            
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginFrame loginFrame = ShopManagementApplication.getBean(LoginFrame.class);
                    if (loginFrame != null) {
                        loginFrame.setVisible(true);
                    } else {
                        new LoginFrame().setVisible(true);
                    }
                } catch (Exception ex) {
                    showError("Failed to return to login: " + ex.getMessage());
                }
            });
        }
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
