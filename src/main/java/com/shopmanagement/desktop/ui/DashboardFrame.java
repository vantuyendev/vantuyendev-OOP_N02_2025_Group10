package com.shopmanagement.desktop.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private JPanel headerPanel, contentPanel;
    private JLabel titleLabel;
    private JButton productsButton, customersButton, logoutButton;
    
    public DashboardFrame() {
        setTitle("Shop Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        createComponents();
        setupLayout();
        setVisible(true);
    }
    
    private void createComponents() {
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 52, 64));
        titleLabel = new JLabel("Shop Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        
        contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        productsButton = new JButton("Manage Products");
        customersButton = new JButton("Manage Customers");
        logoutButton = new JButton("Logout");
        
        productsButton.addActionListener(this);
        customersButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        contentPanel.add(productsButton);
        contentPanel.add(customersButton);
        contentPanel.add(logoutButton);
    }
    
    private void setupLayout() {
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == productsButton) {
            JOptionPane.showMessageDialog(this, "Product Management");
        } else if (e.getSource() == customersButton) {
            JOptionPane.showMessageDialog(this, "Customer Management");
        } else if (e.getSource() == logoutButton) {
            dispose();
            System.exit(0);
        }
    }
}
