package com.shopmanagement.activity;

import com.shopmanagement.util.DesktopUtils;
import com.shopmanagement.util.Theme;
import com.shopmanagement.ShopManagementApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Dashboard for Desktop Application
 * Giao diện chính của ứng dụng desktop
 */
@Component
@ConditionalOnProperty(name = "spring.main.web-application-type", havingValue = "none")
public class DashboardActivity extends JFrame implements ActionListener {
    
    private JMenuBar menuBar;
    private JMenu productMenu, customerMenu, settingsMenu;
    private JMenuItem manageProducts, addProduct, viewProducts;
    private JMenuItem manageCustomers, addCustomer, viewCustomers;
    private JMenuItem changePassword, themeSettings, logout;
    
    private JPanel mainPanel, welcomePanel, statsPanel;
    private JLabel welcomeLabel, statsLabel;
    
    public DashboardActivity() {
        super("Shop Management System - Dashboard");
        initializeComponents();
        layoutComponents();
        setupEventHandlers();
        setupWindow();
    }
    
    private void initializeComponents() {
        // Menu Bar
        menuBar = new JMenuBar();
        
        // Product Menu
        productMenu = new JMenu("Products");
        manageProducts = new JMenuItem("Manage Products");
        addProduct = new JMenuItem("Add Product");
        viewProducts = new JMenuItem("View Products");
        
        productMenu.add(manageProducts);
        productMenu.add(addProduct);
        productMenu.add(viewProducts);
        
        // Customer Menu
        customerMenu = new JMenu("Customers");
        manageCustomers = new JMenuItem("Manage Customers");
        addCustomer = new JMenuItem("Add Customer");
        viewCustomers = new JMenuItem("View Customers");
        
        customerMenu.add(manageCustomers);
        customerMenu.add(addCustomer);
        customerMenu.add(viewCustomers);
        
        // Settings Menu
        settingsMenu = new JMenu("Settings");
        changePassword = new JMenuItem("Change Password");
        themeSettings = new JMenuItem("Theme Settings");
        logout = new JMenuItem("Logout");
        
        settingsMenu.add(changePassword);
        settingsMenu.add(themeSettings);
        settingsMenu.addSeparator();
        settingsMenu.add(logout);
        
        // Add menus to menu bar
        menuBar.add(productMenu);
        menuBar.add(customerMenu);
        menuBar.add(settingsMenu);
        
        // Main panels
        mainPanel = new JPanel(new BorderLayout());
        welcomePanel = new JPanel(new FlowLayout());
        statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        // Labels
        welcomeLabel = new JLabel("Welcome to Shop Management System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        statsLabel = new JLabel("System Statistics", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
    }
    
    private void layoutComponents() {
        this.setJMenuBar(menuBar);
        
        welcomePanel.add(welcomeLabel);
        
        // Add some placeholder statistics panels
        statsPanel.add(createStatCard("Total Products", "0"));
        statsPanel.add(createStatCard("Total Customers", "0"));
        statsPanel.add(createStatCard("Active Sessions", "1"));
        statsPanel.add(createStatCard("System Status", "Online"));
        
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(statsLabel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }
    
    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(title));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }
    
    private void setupEventHandlers() {
        // Product menu actions
        manageProducts.addActionListener(this);
        addProduct.addActionListener(this);
        viewProducts.addActionListener(this);
        
        // Customer menu actions
        manageCustomers.addActionListener(this);
        addCustomer.addActionListener(this);
        viewCustomers.addActionListener(this);
        
        // Settings menu actions
        changePassword.addActionListener(this);
        themeSettings.addActionListener(this);
        logout.addActionListener(this);
        
        // Window close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setupWindow() {
        DesktopUtils.setupWindow(this, Theme.GUI_WIDTH, Theme.GUI_HEIGHT);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(800, 600));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "Manage Products":
                openManageProducts();
                break;
            case "Add Product":
                openAddProduct();
                break;
            case "View Products":
                openViewProducts();
                break;
            case "Manage Customers":
                openManageCustomers();
                break;
            case "Add Customer":
                openAddCustomer();
                break;
            case "View Customers":
                openViewCustomers();
                break;
            case "Change Password":
                openChangePassword();
                break;
            case "Theme Settings":
                openThemeSettings();
                break;
            case "Logout":
                logout();
                break;
        }
    }
    
    // Menu action methods
    private void openManageProducts() {
        DesktopUtils.showInfoMessage(this, "Product Management feature will be available soon.", "Info");
    }
    
    private void openAddProduct() {
        DesktopUtils.showInfoMessage(this, "Add Product feature will be available soon.", "Info");
    }
    
    private void openViewProducts() {
        DesktopUtils.showInfoMessage(this, "View Products feature will be available soon.", "Info");
    }
    
    private void openManageCustomers() {
        DesktopUtils.showInfoMessage(this, "Customer Management feature will be available soon.", "Info");
    }
    
    private void openAddCustomer() {
        DesktopUtils.showInfoMessage(this, "Add Customer feature will be available soon.", "Info");
    }
    
    private void openViewCustomers() {
        DesktopUtils.showInfoMessage(this, "View Customers feature will be available soon.", "Info");
    }
    
    private void openChangePassword() {
        DesktopUtils.showInfoMessage(this, "Change Password feature will be available soon.", "Info");
    }
    
    private void openThemeSettings() {
        DesktopUtils.showInfoMessage(this, "Theme Settings feature will be available soon.", "Info");
    }
    
    private void logout() {
        int confirm = DesktopUtils.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout");
        
        if (confirm == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            this.dispose();
            
            try {
                LoginActivity loginActivity = ShopManagementApplication.getApplicationContext().getBean(LoginActivity.class);
                loginActivity.setVisible(true);
            } catch (Exception e) {
                // Fallback
                new LoginActivity().setVisible(true);
            }
        }
    }
}
