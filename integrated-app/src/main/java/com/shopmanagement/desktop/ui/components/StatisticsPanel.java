package com.shopmanagement.desktop.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern statistical display panel with animated numbers and icons
 */
public class StatisticsPanel extends JPanel {
    private JLabel numberLabel;
    private JLabel titleLabel;
    private JLabel iconLabel;
    private Color accentColor;
    private int targetValue;
    private int currentValue = 0;
    private Timer animationTimer;

    public StatisticsPanel(String icon, String title, int value, Color color) {
        this.accentColor = color;
        this.targetValue = value;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        setOpaque(false);
        
        initializeComponents(icon, title);
        setupAnimation();
    }
    
    private void initializeComponents(String icon, String title) {
        // Icon
        iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setForeground(accentColor);
        
        // Number
        numberLabel = new JLabel("0");
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setForeground(new Color(44, 62, 80));
        
        // Title
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(127, 140, 141));
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(numberLabel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(topPanel, BorderLayout.CENTER);
        centerPanel.add(titleLabel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setupAnimation() {
        animationTimer = new Timer(50, e -> {
            if (currentValue < targetValue) {
                currentValue += Math.max(1, (targetValue - currentValue) / 10);
                numberLabel.setText(String.valueOf(currentValue));
            } else {
                animationTimer.stop();
                numberLabel.setText(String.valueOf(targetValue));
            }
        });
    }
    
    public void startAnimation() {
        currentValue = 0;
        animationTimer.start();
    }
    
    public void updateValue(int newValue) {
        this.targetValue = newValue;
        startAnimation();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Background
        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));
        
        // Border with accent color
        g2d.setColor(accentColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new RoundRectangle2D.Double(1, 1, width - 2, height - 2, 10, 10));
        
        // Accent stripe at the top
        g2d.setColor(accentColor);
        g2d.fill(new RoundRectangle2D.Double(1, 1, width - 2, 5, 10, 10));
        
        g2d.dispose();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 120);
    }
}
