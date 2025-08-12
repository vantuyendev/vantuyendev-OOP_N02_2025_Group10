package com.shopmanagement.desktop.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern card-style button component with hover effects and rounded corners
 */
public class ModernCardButton extends JPanel {
    private JLabel iconLabel;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private Color defaultColor;
    private Color hoverColor;
    private boolean isHovered = false;
    private Runnable clickAction;

    public ModernCardButton(String icon, String title, String description, Color color) {
        this.defaultColor = color;
        this.hoverColor = brightenColor(color, 0.1f);
        
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(defaultColor);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        initializeComponents(icon, title, description);
        setupEventHandlers();
    }
    
    private void initializeComponents(String icon, String title, String description) {
        // Icon
        iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setForeground(Color.WHITE);
        
        // Title
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        
        // Description
        descriptionLabel = new JLabel("<html><center>" + description + "</center></html>");
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setForeground(new Color(255, 255, 255, 200));
        
        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(iconLabel, BorderLayout.NORTH);
        centerPanel.add(titleLabel, BorderLayout.CENTER);
        centerPanel.add(descriptionLabel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickAction != null) {
                    clickAction.run();
                }
            }
        });
    }
    
    public void setClickAction(Runnable action) {
        this.clickAction = action;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color currentColor = isHovered ? hoverColor : defaultColor;
        g2d.setColor(currentColor);
        
        int width = getWidth();
        int height = getHeight();
        
        // Draw rounded rectangle with shadow effect
        if (isHovered) {
            // Shadow
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fill(new RoundRectangle2D.Double(2, 2, width - 2, height - 2, 15, 15));
        }
        
        // Main card
        g2d.setColor(currentColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width - 2, height - 2, 15, 15));
        
        // Border
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new RoundRectangle2D.Double(0, 0, width - 2, height - 2, 15, 15));
        
        g2d.dispose();
    }
    
    private Color brightenColor(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 180);
    }
}
