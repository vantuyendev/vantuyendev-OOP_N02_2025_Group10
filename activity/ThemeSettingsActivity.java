package activity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import attr.*;

public class ThemeSettingsActivity extends JDialog implements ActionListener {
	private JPanel mainPanel, previewPanel, buttonPanel;
	private JButton[] themeButtons;
	private JButton applyButton, cancelButton;
	private JLabel titleLabel, previewLabel;
	private Theme.ThemeVariant selectedTheme;
	private Theme.ThemeVariant originalTheme;
	private JFrame parentFrame;
	
	public ThemeSettingsActivity(JFrame parent) {
		super(parent, "Theme Settings", true);
		this.parentFrame = parent;
		this.originalTheme = Theme.getCurrentTheme();
		this.selectedTheme = originalTheme;
		
		initializeComponents();
		layoutComponents();
		setupEventHandlers();
		
		this.setSize(500, 400);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
	}
	
	private void initializeComponents() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Theme.getBackgroundPanel());
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		// Title
		titleLabel = new JLabel("Choose Theme");
		ThemeManager.styleLabel(titleLabel, ThemeManager.LabelStyle.TITLE);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Theme buttons
		Theme.ThemeVariant[] themes = Theme.ThemeVariant.values();
		themeButtons = new JButton[themes.length];
		
		for (int i = 0; i < themes.length; i++) {
			themeButtons[i] = new JButton(getThemeDisplayName(themes[i]));
			themeButtons[i].setActionCommand(themes[i].name());
			themeButtons[i].addActionListener(this);
			themeButtons[i].setPreferredSize(new Dimension(200, 40));
			
			// Style the button
			if (themes[i] == selectedTheme) {
				ThemeManager.styleButton(themeButtons[i], ThemeManager.ButtonStyle.PRIMARY);
			} else {
				ThemeManager.styleButton(themeButtons[i], ThemeManager.ButtonStyle.OUTLINE);
			}
		}
		
		// Preview panel
		previewPanel = new JPanel();
		previewPanel.setLayout(new BorderLayout());
		previewPanel.setPreferredSize(new Dimension(300, 120));
		previewPanel.setBorder(new TitledBorder("Preview"));
		updatePreview();
		
		// Action buttons
		applyButton = new JButton("Apply");
		ThemeManager.styleButton(applyButton, ThemeManager.ButtonStyle.SUCCESS);
		applyButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		ThemeManager.styleButton(cancelButton, ThemeManager.ButtonStyle.SECONDARY);
		cancelButton.addActionListener(this);
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBackground(Theme.getBackgroundPanel());
		buttonPanel.add(cancelButton);
		buttonPanel.add(applyButton);
	}
	
	private void layoutComponents() {
		// Main title
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(Theme.getBackgroundPanel());
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		
		// Theme selection panel
		JPanel themePanel = new JPanel(new GridLayout(3, 2, 10, 10));
		themePanel.setBackground(Theme.getBackgroundPanel());
		for (JButton button : themeButtons) {
			themePanel.add(button);
		}
		
		// Center content
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Theme.getBackgroundPanel());
		centerPanel.add(themePanel, BorderLayout.CENTER);
		centerPanel.add(previewPanel, BorderLayout.SOUTH);
		centerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
	}
	
	private void setupEventHandlers() {
		// Close operation
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		// Window listener to restore original theme on close
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!selectedTheme.equals(originalTheme)) {
					Theme.setTheme(originalTheme);
				}
			}
		});
	}
	
	private void updatePreview() {
		previewPanel.removeAll();
		
		// Create preview components with selected theme
		Theme.ThemeVariant currentTheme = Theme.getCurrentTheme();
		Theme.setTheme(selectedTheme);
		
		JPanel samplePanel = new JPanel(new FlowLayout());
		samplePanel.setBackground(Theme.getBackgroundPanel());
		
		JLabel sampleLabel = new JLabel("Sample Text");
		ThemeManager.styleLabel(sampleLabel, ThemeManager.LabelStyle.REGULAR);
		
		JButton sampleButton = new JButton("Sample Button");
		ThemeManager.styleButton(sampleButton, ThemeManager.ButtonStyle.PRIMARY);
		sampleButton.setPreferredSize(new Dimension(120, 30));
		
		JTextField sampleField = new JTextField("Sample Input", 10);
		ThemeManager.styleTextField(sampleField);
		
		samplePanel.add(sampleLabel);
		samplePanel.add(sampleButton);
		samplePanel.add(sampleField);
		
		previewPanel.add(samplePanel, BorderLayout.CENTER);
		
		// Restore current theme
		Theme.setTheme(currentTheme);
		
		previewPanel.revalidate();
		previewPanel.repaint();
	}
	
	private void updateThemeButtons() {
		Theme.ThemeVariant[] themes = Theme.ThemeVariant.values();
		for (int i = 0; i < themes.length; i++) {
			if (themes[i] == selectedTheme) {
				ThemeManager.styleButton(themeButtons[i], ThemeManager.ButtonStyle.PRIMARY);
			} else {
				ThemeManager.styleButton(themeButtons[i], ThemeManager.ButtonStyle.OUTLINE);
			}
		}
	}
	
	private String getThemeDisplayName(Theme.ThemeVariant theme) {
		switch (theme) {
			case PROFESSIONAL_BLUE:
				return "Professional Blue";
			case MODERN_DARK:
				return "Modern Dark";
			case ELEGANT_PURPLE:
				return "Elegant Purple";
			case FRESH_GREEN:
				return "Fresh Green";
			case WARM_ORANGE:
				return "Warm Orange";
			case CLASSIC_GRAY:
				return "Classic Gray";
			default:
				return theme.name();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == applyButton) {
			// Apply the selected theme
			ThemeManager.changeTheme(selectedTheme);
			this.dispose();
		} else if (e.getSource() == cancelButton) {
			// Cancel and restore original theme
			Theme.setTheme(originalTheme);
			this.dispose();
		} else {
			// Theme button clicked
			try {
				selectedTheme = Theme.ThemeVariant.valueOf(e.getActionCommand());
				updateThemeButtons();
				updatePreview();
			} catch (IllegalArgumentException ex) {
				// Handle invalid theme
				System.err.println("Invalid theme: " + e.getActionCommand());
			}
		}
	}
}
