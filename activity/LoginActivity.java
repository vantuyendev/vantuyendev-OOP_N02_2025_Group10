package activity;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import attr.*;

public class LoginActivity extends JFrame implements ActionListener {
	private JPanel mainPanel, headerPanel, formPanel, buttonPanel;
	private JButton buttonExit, buttonLogin, buttonSignup, buttonThemeSettings;
	private JLabel titleLabel, usernameLabel, passwordLabel, logoLabel;
	private JTextField usernameTF;
	private JPasswordField passwordF;
	
	public LoginActivity() {
		super("Shop Management System - Login");
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
		titleLabel = new JLabel("Shop Management System");
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
		
		buttonExit = new JButton("Exit");
		ThemeManager.styleButton(buttonExit, ThemeManager.ButtonStyle.DANGER);
		buttonExit.setPreferredSize(new Dimension(80, 30));
		buttonExit.addActionListener(this);
		
		headerButtonPanel.add(buttonThemeSettings);
		headerButtonPanel.add(Box.createHorizontalStrut(10));
		headerButtonPanel.add(buttonExit);
		headerButtonPanel.setBorder(new EmptyBorder(0, 0, 0, Theme.PANEL_PADDING));
		
		headerPanel.add(titleLabel, BorderLayout.CENTER);
		headerPanel.add(headerButtonPanel, BorderLayout.EAST);
		
		// Form panel
		formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		ThemeManager.stylePanel(formPanel, ThemeManager.PanelStyle.CARD);
		formPanel.setBorder(new CompoundBorder(
			new EmptyBorder(50, 0, 50, 0),
			new CompoundBorder(
				new LineBorder(Theme.getPrimaryColor(), 2, true),
				new EmptyBorder(40, 40, 40, 40)
			)
		));
		formPanel.setMaximumSize(new Dimension(400, 300));
		
		// Login form title
		JLabel loginTitle = new JLabel("Login to Your Account");
		ThemeManager.styleLabel(loginTitle, ThemeManager.LabelStyle.SUBTITLE);
		loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginTitle.setBorder(new EmptyBorder(0, 0, 30, 0));
		
		// Username field
		usernameLabel = new JLabel("User ID:");
		ThemeManager.styleLabel(usernameLabel, ThemeManager.LabelStyle.REGULAR);
		usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		usernameTF = new JTextField();
		ThemeManager.styleTextField(usernameTF);
		usernameTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, Theme.INPUT_HEIGHT));
		usernameTF.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Password field
		passwordLabel = new JLabel("Password:");
		ThemeManager.styleLabel(passwordLabel, ThemeManager.LabelStyle.REGULAR);
		passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		passwordF = new JPasswordField();
		ThemeManager.stylePasswordField(passwordF);
		passwordF.setMaximumSize(new Dimension(Integer.MAX_VALUE, Theme.INPUT_HEIGHT));
		passwordF.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Login button
		buttonLogin = new JButton("Login");
		ThemeManager.styleButton(buttonLogin, ThemeManager.ButtonStyle.PRIMARY);
		buttonLogin.setPreferredSize(new Dimension(200, Theme.BUTTON_HEIGHT));
		buttonLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonLogin.addActionListener(this);
		
		// Add components to form panel
		formPanel.add(loginTitle);
		formPanel.add(usernameLabel);
		formPanel.add(Box.createVerticalStrut(5));
		formPanel.add(usernameTF);
		formPanel.add(Box.createVerticalStrut(Theme.COMPONENT_SPACING));
		formPanel.add(passwordLabel);
		formPanel.add(Box.createVerticalStrut(5));
		formPanel.add(passwordF);
		formPanel.add(Box.createVerticalStrut(25));
		formPanel.add(buttonLogin);
		
		// Bottom button panel
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ThemeManager.stylePanel(buttonPanel, ThemeManager.PanelStyle.MAIN);
		
		JLabel signupLabel = new JLabel("Don't have an account? ");
		ThemeManager.styleLabel(signupLabel, ThemeManager.LabelStyle.REGULAR);
		
		buttonSignup = new JButton("Sign Up");
		ThemeManager.styleButton(buttonSignup, ThemeManager.ButtonStyle.OUTLINE);
		buttonSignup.setPreferredSize(new Dimension(100, 30));
		buttonSignup.addActionListener(this);
		
		buttonPanel.add(signupLabel);
		buttonPanel.add(buttonSignup);
		buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
	}
	
	private void layoutComponents() {
		// Center the form panel
		JPanel centerPanel = new JPanel(new GridBagLayout());
		ThemeManager.stylePanel(centerPanel, ThemeManager.PanelStyle.MAIN);
		centerPanel.add(formPanel);
		
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
	}
	
	private void setupEventHandlers() {
		// Add Enter key support for login
		KeyAdapter enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performLogin();
				}
			}
		};
		
		usernameTF.addKeyListener(enterKeyListener);
		passwordF.addKeyListener(enterKeyListener);
	}
	
	private void performLogin() {
		String username = usernameTF.getText().trim();
		String password = new String(passwordF.getPassword());
		
		if (username.isEmpty() || password.isEmpty()) {
			showErrorMessage("Please enter both username and password.");
			return;
		}
		
		int status = User.checkStatus(username, password);
		if (status == 0) {
			this.setVisible(false);
			new EmployeeActivity(username).setVisible(true);
		} else if (status == 1) {
			this.setVisible(false);
			new CustomerActivity(username).setVisible(true);
		} else {
			showErrorMessage("Invalid username or password. Please try again.");
		}
	}
	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(buttonExit)) {
			System.exit(0);
		} else if (ae.getSource().equals(buttonSignup)) {
			this.setVisible(false);
			new SignupActivity().setVisible(true);
		} else if (ae.getSource().equals(buttonLogin)) {
			performLogin();
		} else if (ae.getSource().equals(buttonThemeSettings)) {
			new ThemeSettingsActivity(this).setVisible(true);
		}
	}
}