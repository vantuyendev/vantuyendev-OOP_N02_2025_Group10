package com.shopmanagement.activity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.shopmanagement.util.*;

/**
 * DemoActivity - Demonstrates the modern UI improvements for VNC
 * Shows off new themes, components, and VNC optimizations
 */
public class DemoActivity extends JFrame implements ActionListener {
    private JPanel mainPanel, headerPanel, contentPanel;
    private JButton backButton, themeButton;
    private ModernCard[] demoCards;
    private JLabel titleLabel;
    
    public DemoActivity() {
        super("Shop Management System - UI Demo");
        initializeComponents();
        layoutComponents();
        setupEventHandlers();
        
        ThemeManager.registerFrame(this);
        
        this.setSize(Theme.GUI_WIDTH, Theme.GUI_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        ThemeManager.stylePanel(mainPanel, ThemeManager.PanelStyle.MAIN);
        
        // Header
        headerPanel = new JPanel(new BorderLayout());
        ThemeManager.stylePanel(headerPanel, ThemeManager.PanelStyle.HEADER);
        headerPanel.setPreferredSize(new Dimension(Theme.GUI_WIDTH, Theme.HEADER_HEIGHT));
        
        titleLabel = new JLabel("Modern UI Demo - VNC Optimized");
        ThemeManager.styleLabel(titleLabel, ThemeManager.LabelStyle.TITLE);
        titleLabel.setForeground(Theme.getWhiteColor());
        titleLabel.setBorder(new EmptyBorder(0, Theme.PANEL_PADDING, 0, 0));
        
        // Header buttons
        JPanel headerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerButtonPanel.setOpaque(false);
        
        themeButton = new JButton("Themes");
        ThemeManager.styleButton(themeButton, ThemeManager.ButtonStyle.OUTLINE);
        themeButton.setForeground(Theme.getWhiteColor());
        themeButton.addActionListener(this);
        
        backButton = new JButton("Back");
        ThemeManager.styleButton(backButton, ThemeManager.ButtonStyle.DANGER);
        backButton.addActionListener(this);
        
        headerButtonPanel.add(themeButton);
        headerButtonPanel.add(Box.createHorizontalStrut(10));
        headerButtonPanel.add(backButton);
        headerButtonPanel.setBorder(new EmptyBorder(0, 0, 0, Theme.PANEL_PADDING));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(headerButtonPanel, BorderLayout.EAST);
        
        // Content panel
        contentPanel = new JPanel(new BorderLayout());
        ThemeManager.stylePanel(contentPanel, ThemeManager.PanelStyle.MAIN);
        contentPanel.setBorder(new EmptyBorder(Theme.PANEL_PADDING, Theme.PANEL_PADDING, 
            Theme.PANEL_PADDING, Theme.PANEL_PADDING));
        
        createDemoCards();
    }
    
    private void createDemoCards() {
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsPanel.setBackground(Theme.getBackgroundPanel());
        
        // Demo cards showing different features
        demoCards = new ModernCard[6];
        
        demoCards[0] = ModernCard.createInfoCard(
            "VNC Optimized", 
            "1024x768 resolution, enhanced fonts, high contrast colors"
        );
        
        demoCards[1] = ModernCard.createSuccessCard(
            "9 Beautiful Themes", 
            "Ultra Modern, Midnight Blue, Cherry Blossom and more"
        );
        
        demoCards[2] = ModernCard.createActionCard(
            "Modern Components", 
            "Cards, buttons, inputs with hover effects",
            e -> showComponentDemo()
        );
        
        demoCards[3] = new ModernCard(
            "Enhanced Typography", 
            "Larger fonts, better readability, fallback fonts",
            Theme.getWarningColor(),
            false
        );
        
        demoCards[4] = ModernCard.createDangerCard(
            "Performance", 
            "Optimized rendering, better responsiveness"
        );
        
        demoCards[5] = ModernCard.createActionCard(
            "Professional UI", 
            "Splash screen, animations, modern design",
            e -> showSplashDemo()
        );
        
        for (ModernCard card : demoCards) {
            cardsPanel.add(card);
        }
        
        // Add description
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Theme.getBackgroundPanel());
        descPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        JLabel descLabel = new JLabel("<html><center>" +
            "<h2>Shop Management System v2.0</h2>" +
            "<p>Completely redesigned for VNC environments with modern UI components,<br>" +
            "beautiful themes, and optimized performance.</p>" +
            "<p><strong>Click the cards above to explore features!</strong></p>" +
            "</center></html>");
        ThemeManager.styleLabel(descLabel, ThemeManager.LabelStyle.REGULAR);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        descPanel.add(descLabel, BorderLayout.CENTER);
        
        contentPanel.add(cardsPanel, BorderLayout.CENTER);
        contentPanel.add(descPanel, BorderLayout.SOUTH);
    }
    
    private void layoutComponents() {
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        this.add(mainPanel);
    }
    
    private void setupEventHandlers() {
        // Add keyboard shortcuts
        KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKey, "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void showComponentDemo() {
        JDialog dialog = new JDialog(this, "Component Demo", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Theme.getBackgroundPanel());
        
        // Demo different button styles
        String[] buttonStyles = {"Primary", "Secondary", "Success", "Danger", "Warning", "Info", "Outline"};
        ThemeManager.ButtonStyle[] styles = {
            ThemeManager.ButtonStyle.PRIMARY,
            ThemeManager.ButtonStyle.SECONDARY,
            ThemeManager.ButtonStyle.SUCCESS,
            ThemeManager.ButtonStyle.DANGER,
            ThemeManager.ButtonStyle.WARNING,
            ThemeManager.ButtonStyle.INFO,
            ThemeManager.ButtonStyle.OUTLINE
        };
        
        for (int i = 0; i < buttonStyles.length; i++) {
            JButton btn = new JButton(buttonStyles[i] + " Button");
            ThemeManager.styleButton(btn, styles[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    private void showSplashDemo() {
        JOptionPane.showMessageDialog(this,
            "The modern splash screen is shown when the application starts.\n" +
            "It features:\n" +
            "• Beautiful gradient background\n" +
            "• Loading animation\n" +
            "• Progress indicator\n" +
            "• Click to skip functionality\n" +
            "• Professional appearance",
            "Splash Screen Demo",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
        } else if (e.getSource() == themeButton) {
            new ThemeSettingsActivity(this).setVisible(true);
        }
    }
    
    // Static method to launch demo
    public static void showDemo() {
        SwingUtilities.invokeLater(() -> {
            new DemoActivity().setVisible(true);
        });
    }
}
