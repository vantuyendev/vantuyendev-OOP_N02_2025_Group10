package com.shopmanagement.activity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.shopmanagement.util.*;

/**
 * SplashScreen - Modern loading screen for the application
 * Displays when the application starts with a beautiful animated loading interface
 */
public class SplashScreen extends JFrame {
    private JPanel mainPanel;
    private JLabel logoLabel, titleLabel, subtitleLabel, loadingLabel;
    private JProgressBar progressBar;
    private Timer progressTimer;
    private int progress = 0;
    
    public SplashScreen() {
        super();
        initializeComponents();
        layoutComponents();
        startLoadingAnimation();
        
        this.setUndecorated(true);
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add rounded corners effect
        this.setShape(new java.awt.geom.RoundRectangle2D.Float(0, 0, 500, 350, 20, 20));
    }
    
    private void initializeComponents() {
        // Main panel with gradient background
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                Color[] gradient = Theme.getPrimaryGradient();
                GradientPaint gp = new GradientPaint(0, 0, gradient[0], 0, getHeight(), gradient[1]);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        // Logo/Icon (using text for now, can be replaced with actual image)
        logoLabel = new JLabel("🏪");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setForeground(Theme.getWhiteColor());
        
        // Title
        titleLabel = new JLabel("Shop Management System");
        titleLabel.setFont(Theme.getBestFont(
            new Font("Segoe UI", Font.BOLD, 28),
            new Font("SansSerif", Font.BOLD, 28)
        ));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Theme.getWhiteColor());
        
        // Subtitle
        subtitleLabel = new JLabel("Modern Business Management Solution");
        subtitleLabel.setFont(Theme.getBestFont(
            new Font("Segoe UI", Font.PLAIN, 14),
            new Font("SansSerif", Font.PLAIN, 14)
        ));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        
        // Loading label
        loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(Theme.getBestFont(
            new Font("Segoe UI", Font.PLAIN, 12),
            new Font("SansSerif", Font.PLAIN, 12)
        ));
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setForeground(new Color(255, 255, 255, 200));
        
        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(300, 8));
        progressBar.setBackground(new Color(255, 255, 255, 100));
        progressBar.setForeground(Theme.getWhiteColor());
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(false);
    }
    
    private void layoutComponents() {
        // Center panel for content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        // Add components with spacing
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(40));
        
        // Progress section
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);
        progressPanel.add(loadingLabel, BorderLayout.NORTH);
        progressPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        
        JPanel progressBarPanel = new JPanel(new FlowLayout());
        progressBarPanel.setOpaque(false);
        progressBarPanel.add(progressBar);
        progressPanel.add(progressBarPanel, BorderLayout.SOUTH);
        
        centerPanel.add(progressPanel);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Version info at bottom
        JLabel versionLabel = new JLabel("Version 2.0 - VNC Optimized");
        versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionLabel.setForeground(new Color(255, 255, 255, 150));
        mainPanel.add(versionLabel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
    }
    
    private void startLoadingAnimation() {
        String[] loadingTexts = {
            "Initializing system...",
            "Loading components...",
            "Connecting to database...",
            "Applying modern theme...",
            "Optimizing for VNC...",
            "Ready to launch!"
        };
        
        progressTimer = new Timer(500, new ActionListener() {
            private int textIndex = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 15 + (int)(Math.random() * 10);
                
                if (progress >= 100) {
                    progress = 100;
                    progressBar.setValue(progress);
                    loadingLabel.setText("Ready to launch!");
                    
                    Timer closeTimer = new Timer(800, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            closeSplashAndOpenLogin();
                            ((Timer)e.getSource()).stop();
                        }
                    });
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                    
                    progressTimer.stop();
                } else {
                    progressBar.setValue(progress);
                    if (textIndex < loadingTexts.length) {
                        loadingLabel.setText(loadingTexts[textIndex]);
                        textIndex++;
                    }
                }
            }
        });
        
        progressTimer.start();
    }
    
    private void closeSplashAndOpenLogin() {
        this.setVisible(false);
        this.dispose();
        
        // Show login activity
        SwingUtilities.invokeLater(() -> {
            new LoginActivity().setVisible(true);
        });
    }
    
    // Add click to skip functionality
    private void addClickToSkip() {
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (progressTimer.isRunning()) {
                    progressTimer.stop();
                    closeSplashAndOpenLogin();
                }
            }
        });
        
        JLabel skipLabel = new JLabel("Click anywhere to skip");
        skipLabel.setFont(new Font("SansSerif", Font.ITALIC, 9));
        skipLabel.setForeground(new Color(255, 255, 255, 120));
        skipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add(skipLabel, BorderLayout.NORTH);
    }
}
