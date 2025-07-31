package com.shopmanagement.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * ModernCard - A modern card component with shadow effects and rounded corners
 * Optimized for VNC display with high contrast and clear typography
 */
public class ModernCard extends JPanel {
    private String title;
    private String subtitle;
    private Color accentColor;
    private boolean isHoverable;
    private boolean isSelected;
    
    public ModernCard(String title) {
        this(title, null, Theme.getPrimaryColor(), false);
    }
    
    public ModernCard(String title, String subtitle) {
        this(title, subtitle, Theme.getPrimaryColor(), false);
    }
    
    public ModernCard(String title, String subtitle, Color accentColor, boolean isHoverable) {
        this.title = title;
        this.subtitle = subtitle;
        this.accentColor = accentColor;
        this.isHoverable = isHoverable;
        this.isSelected = false;
        
        initializeCard();
        if (isHoverable) {
            addHoverEffects();
        }
    }
    
    private void initializeCard() {
        setLayout(new BorderLayout());
        setBackground(Theme.getCardBackgroundColor());
        setBorder(createCardBorder());
        setOpaque(true);
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Theme.getCardBackgroundColor());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title label
        if (title != null) {
            JLabel titleLabel = new JLabel(title);
            ThemeManager.styleLabel(titleLabel, ThemeManager.LabelStyle.HEADING);
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(titleLabel);
            
            if (subtitle != null) {
                contentPanel.add(Box.createVerticalStrut(8));
            }
        }
        
        // Subtitle label
        if (subtitle != null) {
            JLabel subtitleLabel = new JLabel(subtitle);
            ThemeManager.styleLabel(subtitleLabel, ThemeManager.LabelStyle.REGULAR);
            subtitleLabel.setForeground(Theme.getSecondaryColor());
            subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(subtitleLabel);
        }
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Add accent strip
        if (accentColor != null) {
            JPanel accentPanel = new JPanel();
            accentPanel.setBackground(accentColor);
            accentPanel.setPreferredSize(new Dimension(4, 0));
            add(accentPanel, BorderLayout.WEST);
        }
    }
    
    private Border createCardBorder() {
        return new CompoundBorder(
            new LineBorder(new Color(229, 231, 235), 1),
            new EmptyBorder(0, 0, 4, 4) // Shadow effect
        );
    }
    
    private Border createHoverBorder() {
        return new CompoundBorder(
            new LineBorder(accentColor, 2),
            new EmptyBorder(0, 0, 6, 6) // Elevated shadow effect
        );
    }
    
    private Border createSelectedBorder() {
        return new CompoundBorder(
            new LineBorder(accentColor, 3),
            new EmptyBorder(0, 0, 8, 8) // More elevated shadow effect
        );
    }
    
    private void addHoverEffects() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) {
                    setBorder(createHoverBorder());
                    setBackground(Theme.getHoverOverlayColor());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) {
                    setBorder(createCardBorder());
                    setBackground(Theme.getCardBackgroundColor());
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isSelected) {
                    setBackground(Theme.getPressedOverlayColor());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isSelected && contains(e.getPoint())) {
                    setBackground(Theme.getHoverOverlayColor());
                } else if (!isSelected) {
                    setBackground(Theme.getCardBackgroundColor());
                }
            }
        });
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            setBorder(createSelectedBorder());
            setBackground(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 30));
        } else {
            setBorder(createCardBorder());
            setBackground(Theme.getCardBackgroundColor());
        }
        repaint();
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setAccentColor(Color color) {
        this.accentColor = color;
        repaint();
    }
    
    public Color getAccentColor() {
        return accentColor;
    }
    
    public void setCardTitle(String title) {
        this.title = title;
        removeAll();
        initializeCard();
        revalidate();
        repaint();
    }
    
    public void setCardSubtitle(String subtitle) {
        this.subtitle = subtitle;
        removeAll();
        initializeCard();
        revalidate();
        repaint();
    }
    
    public String getCardTitle() {
        return title;
    }
    
    public String getCardSubtitle() {
        return subtitle;
    }
    
    // Factory methods for common card types
    public static ModernCard createActionCard(String title, String subtitle, ActionListener actionListener) {
        ModernCard card = new ModernCard(title, subtitle, Theme.getPrimaryColor(), true);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, "card_clicked"));
                }
            }
        });
        return card;
    }
    
    public static ModernCard createInfoCard(String title, String subtitle) {
        return new ModernCard(title, subtitle, Theme.getInfoColor(), false);
    }
    
    public static ModernCard createWarningCard(String title, String subtitle) {
        return new ModernCard(title, subtitle, Theme.getWarningColor(), false);
    }
    
    public static ModernCard createSuccessCard(String title, String subtitle) {
        return new ModernCard(title, subtitle, Theme.getSuccessColor(), false);
    }
    
    public static ModernCard createDangerCard(String title, String subtitle) {
        return new ModernCard(title, subtitle, Theme.getDangerColor(), false);
    }
}
