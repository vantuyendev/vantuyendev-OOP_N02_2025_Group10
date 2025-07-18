package activity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import attr.*;

public class ThemeDemo extends JFrame {
	private JPanel mainPanel;
	private JComboBox<String> themeSelector;
	private JButton[] sampleButtons;
	private JTextField sampleTextField;
	private JLabel[] sampleLabels;
	
	public ThemeDemo() {
		super("Theme Demo - Shop Management System");
		initializeComponents();
		layoutComponents();
		setupEventHandlers();
		
		this.setSize(600, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	private void initializeComponents() {
		mainPanel = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(mainPanel, ThemeManager.PanelStyle.MAIN);
		
		// Theme selector
		String[] themes = {
			"Professional Blue",
			"Modern Dark", 
			"Elegant Purple",
			"Fresh Green",
			"Warm Orange",
			"Classic Gray"
		};
		
		themeSelector = new JComboBox<>(themes);
		themeSelector.setSelectedIndex(0);
		themeSelector.addActionListener(e -> changeTheme());
		
		// Sample components
		sampleLabels = new JLabel[4];
		sampleLabels[0] = new JLabel("Title Sample");
		sampleLabels[1] = new JLabel("Subtitle Sample");
		sampleLabels[2] = new JLabel("Regular Text Sample");
		sampleLabels[3] = new JLabel("Caption Sample");
		
		ThemeManager.styleLabel(sampleLabels[0], ThemeManager.LabelStyle.TITLE);
		ThemeManager.styleLabel(sampleLabels[1], ThemeManager.LabelStyle.SUBTITLE);
		ThemeManager.styleLabel(sampleLabels[2], ThemeManager.LabelStyle.REGULAR);
		ThemeManager.styleLabel(sampleLabels[3], ThemeManager.LabelStyle.CAPTION);
		
		sampleButtons = new JButton[6];
		sampleButtons[0] = new JButton("Primary");
		sampleButtons[1] = new JButton("Secondary");
		sampleButtons[2] = new JButton("Success");
		sampleButtons[3] = new JButton("Danger");
		sampleButtons[4] = new JButton("Warning");
		sampleButtons[5] = new JButton("Info");
		
		ThemeManager.styleButton(sampleButtons[0], ThemeManager.ButtonStyle.PRIMARY);
		ThemeManager.styleButton(sampleButtons[1], ThemeManager.ButtonStyle.SECONDARY);
		ThemeManager.styleButton(sampleButtons[2], ThemeManager.ButtonStyle.SUCCESS);
		ThemeManager.styleButton(sampleButtons[3], ThemeManager.ButtonStyle.DANGER);
		ThemeManager.styleButton(sampleButtons[4], ThemeManager.ButtonStyle.WARNING);
		ThemeManager.styleButton(sampleButtons[5], ThemeManager.ButtonStyle.INFO);
		
		sampleTextField = new JTextField("Sample Input Field");
		ThemeManager.styleTextField(sampleTextField);
	}
	
	private void layoutComponents() {
		// Header
		JPanel headerPanel = new JPanel(new FlowLayout());
		ThemeManager.stylePanel(headerPanel, ThemeManager.PanelStyle.HEADER);
		
		JLabel headerLabel = new JLabel("Theme Demonstration");
		ThemeManager.styleLabel(headerLabel, ThemeManager.LabelStyle.TITLE);
		headerLabel.setForeground(Theme.getWhiteColor());
		
		headerPanel.add(headerLabel);
		
		// Theme selector panel
		JPanel selectorPanel = new JPanel(new FlowLayout());
		ThemeManager.stylePanel(selectorPanel, ThemeManager.PanelStyle.MAIN);
		
		JLabel selectorLabel = new JLabel("Select Theme:");
		ThemeManager.styleLabel(selectorLabel, ThemeManager.LabelStyle.REGULAR);
		
		selectorPanel.add(selectorLabel);
		selectorPanel.add(themeSelector);
		
		// Content panel
		JPanel contentPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		ThemeManager.stylePanel(contentPanel, ThemeManager.PanelStyle.MAIN);
		contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Labels section
		JPanel labelsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		ThemeManager.stylePanel(labelsPanel, ThemeManager.PanelStyle.CARD);
		labelsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Text Styles"));
		
		for (JLabel label : sampleLabels) {
			labelsPanel.add(label);
		}
		
		// Buttons section
		JPanel buttonsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
		ThemeManager.stylePanel(buttonsPanel, ThemeManager.PanelStyle.CARD);
		buttonsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Button Styles"));
		
		for (JButton button : sampleButtons) {
			buttonsPanel.add(button);
		}
		
		// Input section
		JPanel inputPanel = new JPanel(new FlowLayout());
		ThemeManager.stylePanel(inputPanel, ThemeManager.PanelStyle.CARD);
		inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Fields"));
		
		JLabel inputLabel = new JLabel("Sample Input:");
		ThemeManager.styleLabel(inputLabel, ThemeManager.LabelStyle.REGULAR);
		inputPanel.add(inputLabel);
		inputPanel.add(sampleTextField);
		
		// Color info section
		JPanel colorPanel = new JPanel(new FlowLayout());
		ThemeManager.stylePanel(colorPanel, ThemeManager.PanelStyle.CARD);
		colorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Theme Colors"));
		
		JLabel colorInfo = new JLabel("Theme: " + Theme.getThemeName());
		ThemeManager.styleLabel(colorInfo, ThemeManager.LabelStyle.REGULAR);
		colorPanel.add(colorInfo);
		
		contentPanel.add(labelsPanel);
		contentPanel.add(buttonsPanel);
		contentPanel.add(inputPanel);
		contentPanel.add(colorPanel);
		
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(selectorPanel, BorderLayout.CENTER);
		mainPanel.add(contentPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
	}
	
	private void setupEventHandlers() {
		// No additional event handlers needed
	}
	
	private void changeTheme() {
		String selectedTheme = (String) themeSelector.getSelectedItem();
		Theme.ThemeVariant themeVariant = Theme.ThemeVariant.PROFESSIONAL_BLUE;
		
		switch (selectedTheme) {
			case "Modern Dark":
				themeVariant = Theme.ThemeVariant.MODERN_DARK;
				break;
			case "Elegant Purple":
				themeVariant = Theme.ThemeVariant.ELEGANT_PURPLE;
				break;
			case "Fresh Green":
				themeVariant = Theme.ThemeVariant.FRESH_GREEN;
				break;
			case "Warm Orange":
				themeVariant = Theme.ThemeVariant.WARM_ORANGE;
				break;
			case "Classic Gray":
				themeVariant = Theme.ThemeVariant.CLASSIC_GRAY;
				break;
		}
		
		// Change theme and update UI
		Theme.setTheme(themeVariant);
		updateAllComponents();
		this.repaint();
	}
	
	private void updateAllComponents() {
		// Update all styled components
		ThemeManager.stylePanel(mainPanel, ThemeManager.PanelStyle.MAIN);
		
		// Re-style all components
		ThemeManager.styleLabel(sampleLabels[0], ThemeManager.LabelStyle.TITLE);
		ThemeManager.styleLabel(sampleLabels[1], ThemeManager.LabelStyle.SUBTITLE);
		ThemeManager.styleLabel(sampleLabels[2], ThemeManager.LabelStyle.REGULAR);
		ThemeManager.styleLabel(sampleLabels[3], ThemeManager.LabelStyle.CAPTION);
		
		ThemeManager.styleButton(sampleButtons[0], ThemeManager.ButtonStyle.PRIMARY);
		ThemeManager.styleButton(sampleButtons[1], ThemeManager.ButtonStyle.SECONDARY);
		ThemeManager.styleButton(sampleButtons[2], ThemeManager.ButtonStyle.SUCCESS);
		ThemeManager.styleButton(sampleButtons[3], ThemeManager.ButtonStyle.DANGER);
		ThemeManager.styleButton(sampleButtons[4], ThemeManager.ButtonStyle.WARNING);
		ThemeManager.styleButton(sampleButtons[5], ThemeManager.ButtonStyle.INFO);
		
		ThemeManager.styleTextField(sampleTextField);
		
		// Update all panels
		updatePanelColors(mainPanel);
	}
	
	private void updatePanelColors(Container container) {
		for (Component component : container.getComponents()) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				// You might want to track panel styles to update them correctly
				if (panel.getBackground() != null) {
					panel.setBackground(Theme.getBackgroundPanel());
				}
			}
			if (component instanceof Container) {
				updatePanelColors((Container) component);
			}
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new ThemeDemo().setVisible(true);
		});
	}
}
