package activity;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import attr.*;

/**
 * CustomerActivity - Giao diện chính dành cho khách hàng
 * Hiển thị dashboard và các chức năng của khách hàng
 */
public class CustomerActivity extends JFrame implements ActionListener {
	// Các components của giao diện
	private JPanel mainPanel, headerPanel, contentPanel, dashboardPanel;
	private Customer customer;
	private JButton buttonLogout, buttonProfile, buttonViewProduct, buttonMyProduct, buttonThemeSettings;
	private JLabel titleLabel, welcomeLabel, statsLabel;
	private JPanel[] actionCards;
	
	/**
	 * Constructor khởi tạo giao diện cho khách hàng
	 * @param userId ID của khách hàng
	 */
	public CustomerActivity(String userId) {
		super("Customer Dashboard - Shop Management System");
		
		customer = new Customer(userId);
		customer.fetch();
		
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
		
		// Title and welcome
		titleLabel = new JLabel("Customer Dashboard");
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
		
		welcomeLabel = new JLabel("Welcome back, " + customer.getCustomerName() + "!");
		ThemeManager.styleLabel(welcomeLabel, ThemeManager.LabelStyle.SUBTITLE);
		
		statsLabel = new JLabel("Customer ID: " + customer.getUserId());
		ThemeManager.styleLabel(statsLabel, ThemeManager.LabelStyle.CAPTION);
		
		welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
		welcomePanel.add(statsLabel, BorderLayout.CENTER);
		
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
		actionCards = new JPanel[4];
		
		// View Products Card
		actionCards[0] = createActionCard(
			"View Products",
			"Browse our product catalog",
			"🛍️",
			Theme.getPrimaryColor(),
			buttonViewProduct = new JButton("Browse Products")
		);
		ThemeManager.styleButton(buttonViewProduct, ThemeManager.ButtonStyle.PRIMARY);
		buttonViewProduct.addActionListener(this);
		
		// Purchase History Card
		actionCards[1] = createActionCard(
			"Purchase History",
			"View your order history",
			"📋",
			Theme.getInfoColor(),
			buttonMyProduct = new JButton("View History")
		);
		ThemeManager.styleButton(buttonMyProduct, ThemeManager.ButtonStyle.INFO);
		buttonMyProduct.addActionListener(this);
		
		// Profile Card
		actionCards[2] = createActionCard(
			"My Profile",
			"Manage your account settings",
			"👤",
			Theme.getSuccessColor(),
			createProfileButton()
		);
		
		// Support Card
		actionCards[3] = createActionCard(
			"Support",
			"Get help and support",
			"❓",
			Theme.getWarningColor(),
			createSupportButton()
		);
		
		// Add cards to dashboard
		for (JPanel card : actionCards) {
			dashboardPanel.add(card);
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
	
	private JButton createSupportButton() {
		JButton button = new JButton("Contact Support");
		ThemeManager.styleButton(button, ThemeManager.ButtonStyle.WARNING);
		button.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, 
				"For support, please contact:\nEmail: support@shopmanagement.com\nPhone: 1-800-SHOP-HELP",
				"Support Information", 
				JOptionPane.INFORMATION_MESSAGE);
		});
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
			new MyProfileActivity(this, customer).setVisible(true);
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
			new ViewProductActivity(this, customer).setVisible(true);
		} else if (ae.getSource().equals(buttonMyProduct)) {
			this.setVisible(false);
			new MyProductActivity(this, customer).setVisible(true);
		} else if (ae.getSource().equals(buttonThemeSettings)) {
			new ThemeSettingsActivity(this).setVisible(true);
		}
	}
}