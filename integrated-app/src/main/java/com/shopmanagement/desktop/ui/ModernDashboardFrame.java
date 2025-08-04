package com.shopmanagement.desktop.ui;

import com.shopmanagement.desktop.ui.components.ModernCardButton;
import com.shopmanagement.desktop.ui.components.StatisticsPanel;
import com.shopmanagement.service.EmployeeService;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Modern Dashboard Frame with improved UI design
 */
@Component
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
public class ModernDashboardFrame extends JFrame {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel statisticsPanel;
    private JPanel actionPanel;
    private JPanel footerPanel;
    
    private StatisticsPanel employeeStats;
    private StatisticsPanel productStats;
    private StatisticsPanel customerStats;
    
    public ModernDashboardFrame() {
        super("Shop Management System - Modern Dashboard");
        setupLookAndFeel();
        initializeComponents();
        layoutComponents();
        setupWindow();
        loadStatistics();
    }
    
    private void setupLookAndFeel() {
        try {
            // Use FlatLaf for modern appearance
            com.formdev.flatlaf.FlatLightLaf.setup();
            
            // Custom UI properties
            UIManager.put("Panel.background", new Color(248, 249, 250));
            UIManager.put("Button.background", new Color(255, 255, 255));
            UIManager.put("Button.foreground", new Color(44, 62, 80));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeComponents() {
        // Main container
        mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        createHeaderPanel();
        
        // Statistics
        createStatisticsPanel();
        
        // Action buttons
        createActionPanel();
        
        // Footer
        createFooterPanel();
    }
    
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setOpaque(false);
        
        // Title and subtitle
        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Shop Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(44, 62, 80));
        
        JLabel subtitleLabel = new JLabel("Dashboard - Manage your business efficiently");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        // System status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setOpaque(false);
        
        JLabel statusLabel = new JLabel("● System Online");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(new Color(39, 174, 96));
        
        JLabel timeLabel = new JLabel(java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm")));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(127, 140, 141));
        
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(20));
        statusPanel.add(timeLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(statusPanel, BorderLayout.EAST);
        
        // Add separator line
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(226, 232, 240));
        headerPanel.add(separator, BorderLayout.SOUTH);
    }
    
    private void createStatisticsPanel() {
        statisticsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statisticsPanel.setOpaque(false);
        statisticsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240)), 
            "System Statistics", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            new Color(100, 116, 139)));
        
        // Initialize statistics panels
        employeeStats = new StatisticsPanel("👥", "Total Employees", 0, new Color(59, 130, 246));
        productStats = new StatisticsPanel("📦", "Total Products", 0, new Color(34, 197, 94));
        customerStats = new StatisticsPanel("👤", "Total Customers", 0, new Color(168, 85, 247));
        
        statisticsPanel.add(employeeStats);
        statisticsPanel.add(productStats);
        statisticsPanel.add(customerStats);
    }
    
    private void createActionPanel() {
        actionPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240)), 
            "Quick Actions", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            new Color(100, 116, 139)));
        
        // Create modern card buttons
        ModernCardButton employeesCard = new ModernCardButton(
            "👥", "Employee Management", 
            "Add, edit, and manage your workforce", 
            new Color(59, 130, 246));
        employeesCard.setClickAction(() -> openEmployeeManagement());
        
        ModernCardButton productsCard = new ModernCardButton(
            "📦", "Product Management", 
            "Manage your inventory and products", 
            new Color(34, 197, 94));
        productsCard.setClickAction(() -> openProductManagement());
        
        ModernCardButton customersCard = new ModernCardButton(
            "👤", "Customer Management", 
            "Track and manage customer relationships", 
            new Color(168, 85, 247));
        customersCard.setClickAction(() -> openCustomerManagement());
        
        ModernCardButton reportsCard = new ModernCardButton(
            "📊", "Reports & Analytics", 
            "View business insights and reports", 
            new Color(245, 158, 11));
        reportsCard.setClickAction(() -> openReports());
        
        actionPanel.add(employeesCard);
        actionPanel.add(productsCard);
        actionPanel.add(customersCard);
        actionPanel.add(reportsCard);
    }
    
    private void createFooterPanel() {
        footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        
        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooter.setOpaque(false);
        
        JButton settingsButton = new JButton("⚙️ Settings");
        settingsButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        settingsButton.setFocusPainted(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton helpButton = new JButton("❓ Help");
        helpButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        helpButton.setFocusPainted(false);
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        leftFooter.add(settingsButton);
        leftFooter.add(helpButton);
        
        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightFooter.setOpaque(false);
        
        JButton refreshButton = new JButton("🔄 Refresh Data");
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addActionListener(e -> loadStatistics());
        
        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        
        rightFooter.add(refreshButton);
        rightFooter.add(logoutButton);
        
        footerPanel.add(leftFooter, BorderLayout.WEST);
        footerPanel.add(rightFooter, BorderLayout.EAST);
    }
    
    private void layoutComponents() {
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(statisticsPanel, BorderLayout.NORTH);
        centerPanel.add(actionPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Window close event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                    ModernDashboardFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void loadStatistics() {
        SwingUtilities.invokeLater(() -> {
            try {
                int employeeCount = employeeService != null ? employeeService.findAll().size() : 0;
                int productCount = productService != null ? productService.findAll().size() : 0;
                int customerCount = customerService != null ? customerService.findAll().size() : 0;
                
                employeeStats.updateValue(employeeCount);
                productStats.updateValue(productCount);
                customerStats.updateValue(customerCount);
                
                // Start animations with slight delays for visual effect
                Timer delayTimer = new Timer(100, null);
                delayTimer.addActionListener(new ActionListener() {
                    int step = 0;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (step) {
                            case 0: employeeStats.startAnimation(); break;
                            case 1: productStats.startAnimation(); break;
                            case 2: customerStats.startAnimation(); delayTimer.stop(); break;
                        }
                        step++;
                    }
                });
                delayTimer.start();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading statistics: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void openEmployeeManagement() {
        JOptionPane.showMessageDialog(this, 
            "Employee Management module will be opened here.", 
            "Employee Management", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openProductManagement() {
        JOptionPane.showMessageDialog(this, 
            "Product Management module will be opened here.", 
            "Product Management", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openCustomerManagement() {
        JOptionPane.showMessageDialog(this, 
            "Customer Management module will be opened here.", 
            "Customer Management", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openReports() {
        JOptionPane.showMessageDialog(this, 
            "Reports & Analytics module will be opened here.", 
            "Reports", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void logout() {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    public void showDashboard() {
        setVisible(true);
        loadStatistics();
    }
}
