package activity;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import attr.*;

public class EmployeeActivity extends JFrame implements ActionListener {
	private JPanel mainPanel, headerPanel, contentPanel, dashboardPanel;
	private Employee employee;
	private JButton buttonLogout, buttonProfile, buttonViewProduct;
	private JButton buttonViewCustomer, buttonViewEmployee, buttonThemeSettings;
	private JLabel titleLabel, welcomeLabel, statsLabel, roleLabel;
	private JPanel[] actionCards;
	
	public EmployeeActivity(String userId) {
		super("Employee Dashboard - Shop Management System");
		
		employee = new Employee(userId);
		employee.fetch();
		
		initializeComponents();
		layoutComponents();
		setupEventHandlers();
		
		// Register with theme manager
		ThemeManager.registerFrame(this);
		
		this.setSize(Theme.GUI_WIDTH, Theme.GUI_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private void initializeComponents() {
		// Main panel
		mainPanel = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(mainPanel, ThemeManager.PanelStyle.MAIN);
		
		// Header panel
		headerPanel = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(headerPanel, ThemeManager.PanelStyle.HEADER);
		headerPanel.setPreferredSize(new Dimension(Theme.GUI_WIDTH, Theme.HEADER_HEIGHT));
		
		// Title
		titleLabel = new JLabel("Employee Dashboard");
		ThemeManager.styleLabel(titleLabel, ThemeManager.LabelStyle.TITLE);
		titleLabel.setForeground(Theme.getWhiteColor());
		titleLabel.setBorder(new EmptyBorder(0, Theme.PANEL_PADDING, 0, 0));
		
		// Header buttons
		JPanel headerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		headerButtonPanel.setOpaque(false);
		
		buttonThemeSettings = new JButton("Theme");
		ThemeManager.styleButton(buttonThemeSettings, ThemeManager.ButtonStyle.OUTLINE);
		buttonThemeSettings.setForeground(Theme.getWhiteColor());
		buttonThemeSettings.setPreferredSize(new Dimension(80, 30));
		buttonThemeSettings.addActionListener(this);
		
		buttonProfile = new JButton("Profile");
		ThemeManager.styleButton(buttonProfile, ThemeManager.ButtonStyle.INFO);
		buttonProfile.setPreferredSize(new Dimension(80, 30));
		buttonProfile.addActionListener(this);
		
		buttonLogout = new JButton("Logout");
		ThemeManager.styleButton(buttonLogout, ThemeManager.ButtonStyle.DANGER);
		buttonLogout.setPreferredSize(new Dimension(80, 30));
		buttonLogout.addActionListener(this);
		
		headerButtonPanel.add(buttonThemeSettings);
		headerButtonPanel.add(Box.createHorizontalStrut(10));
		headerButtonPanel.add(buttonProfile);
		headerButtonPanel.add(Box.createHorizontalStrut(10));
		headerButtonPanel.add(buttonLogout);
		headerButtonPanel.setBorder(new EmptyBorder(0, 0, 0, Theme.PANEL_PADDING));
		
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		headerPanel.add(headerButtonPanel, BorderLayout.EAST);
		
		// Content panel
		contentPanel = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(contentPanel, ThemeManager.PanelStyle.MAIN);
		contentPanel.setBorder(new EmptyBorder(Theme.PANEL_PADDING, Theme.PANEL_PADDING, 
			Theme.PANEL_PADDING, Theme.PANEL_PADDING));
		
		// Welcome section
		JPanel welcomePanel = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(welcomePanel, ThemeManager.PanelStyle.CARD);
		welcomePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		welcomeLabel = new JLabel("Welcome back, " + employee.getEmployeeName() + "!");
		ThemeManager.styleLabel(welcomeLabel, ThemeManager.LabelStyle.SUBTITLE);
		
		JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statsPanel.setOpaque(false);
		
		statsLabel = new JLabel("Employee ID: " + employee.getUserId());
		ThemeManager.styleLabel(statsLabel, ThemeManager.LabelStyle.CAPTION);
		
		roleLabel = new JLabel("Role: " + employee.getRole());
		ThemeManager.styleLabel(roleLabel, ThemeManager.LabelStyle.CAPTION);
		if (employee.getRole().equals("Manager")) {
			roleLabel.setForeground(Theme.getSuccessColor());
		}
		
		statsPanel.add(statsLabel);
		statsPanel.add(Box.createHorizontalStrut(20));
		statsPanel.add(roleLabel);
		
		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(statsPanel, BorderLayout.CENTER);
		
		// Dashboard panel with action cards
		dashboardPanel = new JPanel(new GridLayout(2, 2, 20, 20));
		ThemeManager.stylePanel(dashboardPanel, ThemeManager.PanelStyle.MAIN);
		dashboardPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
		
		// Create action cards
		createActionCards();
		
		contentPanel.add(welcomePanel, BorderLayout.NORTH);
		contentPanel.add(dashboardPanel, BorderLayout.CENTER);
	}
	
	private void createActionCards() {
		int cardCount = employee.getRole().equals("Manager") ? 4 : 3;
		actionCards = new JPanel[cardCount];
		
		// Products Management Card
		actionCards[0] = createActionCard(
			"Products",
			"Manage product inventory",
			"📦",
			Theme.getPrimaryColor(),
			buttonViewProduct = new JButton("Manage Products")
		);
		ThemeManager.styleButton(buttonViewProduct, ThemeManager.ButtonStyle.PRIMARY);
		buttonViewProduct.addActionListener(this);
		
		// Customer Management Card
		actionCards[1] = createActionCard(
			"Customers",
			"View customer information",
			"👥",
			Theme.getInfoColor(),
			buttonViewCustomer = new JButton("View Customers")
		);
		ThemeManager.styleButton(buttonViewCustomer, ThemeManager.ButtonStyle.INFO);
		buttonViewCustomer.addActionListener(this);
		
		// Profile Card
		actionCards[2] = createActionCard(
			"My Profile",
			"Manage your account settings",
			"👤",
			Theme.getSuccessColor(),
			createProfileButton()
		);
		
		// Manager-only Employee Management Card
		if (employee.getRole().equals("Manager")) {
			actionCards[3] = createActionCard(
				"Employees",
				"Manage employee accounts",
				"👨‍💼",
				Theme.getWarningColor(),
				buttonViewEmployee = new JButton("Manage Employees")
			);
			ThemeManager.styleButton(buttonViewEmployee, ThemeManager.ButtonStyle.WARNING);
			buttonViewEmployee.addActionListener(this);
		}
		
		// Add cards to dashboard
		for (JPanel card : actionCards) {
			if (card != null) {
				dashboardPanel.add(card);
			}
		}
		
		// Adjust layout for 3 cards if not manager
		if (!employee.getRole().equals("Manager")) {
			dashboardPanel.setLayout(new GridLayout(2, 2, 20, 20));
			dashboardPanel.add(new JPanel()); // Empty panel for spacing
		}
	}
	
	private JPanel createActionCard(String title, String description, String icon, 
									Color accentColor, JButton actionButton) {
		JPanel card = new JPanel(new BorderLayout());
		ThemeManager.stylePanel(card, ThemeManager.PanelStyle.CARD);
		card.setBorder(new CompoundBorder(
			new LineBorder(accentColor, 2),
			new EmptyBorder(20, 20, 20, 20)
		));
		
		// Card header
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		headerPanel.setOpaque(false);
		
		JLabel iconLabel = new JLabel(icon);
		iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
		
		JLabel titleLabel = new JLabel(title);
		ThemeManager.styleLabel(titleLabel, ThemeManager.LabelStyle.HEADING);
		titleLabel.setForeground(accentColor);
		
		headerPanel.add(iconLabel);
		headerPanel.add(Box.createHorizontalStrut(10));
		headerPanel.add(titleLabel);
		
		// Card content
		JLabel descLabel = new JLabel(description);
		ThemeManager.styleLabel(descLabel, ThemeManager.LabelStyle.REGULAR);
		descLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
		
		card.add(headerPanel, BorderLayout.NORTH);
		card.add(descLabel, BorderLayout.CENTER);
		card.add(actionButton, BorderLayout.SOUTH);
		
		return card;
	}
	
	private JButton createProfileButton() {
		JButton button = new JButton("Manage Profile");
		ThemeManager.styleButton(button, ThemeManager.ButtonStyle.SUCCESS);
		button.addActionListener(this);
		return button;
	}
	
	private void layoutComponents() {
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
	}
	
	private void setupEventHandlers() {
		// Window close operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(buttonProfile) || 
			ae.getSource().equals(createProfileButton())) {
			this.setVisible(false);
			new MyProfileActivity(this, employee).setVisible(true);
		} else if (ae.getSource().equals(buttonLogout)) {
			int choice = JOptionPane.showConfirmDialog(this, 
				"Are you sure you want to logout?", 
				"Logout Confirmation", 
				JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				this.setVisible(false);
				new LoginActivity().setVisible(true);
			}
		} else if (ae.getSource().equals(buttonViewProduct)) {
			this.setVisible(false);
			new ViewProductActivity(this, employee).setVisible(true);
		} else if (ae.getSource().equals(buttonViewCustomer)) {
			this.setVisible(false);
			new ViewCustomerActivity(this, employee).setVisible(true);
		} else if (ae.getSource().equals(buttonViewEmployee)) {
			this.setVisible(false);
			new ViewEmployeeActivity(this, employee).setVisible(true);
		} else if (ae.getSource().equals(buttonThemeSettings)) {
			new ThemeSettingsActivity(this).setVisible(true);
		}
	}
}