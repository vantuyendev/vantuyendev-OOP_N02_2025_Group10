package com.shopmanagement.util;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ThemeManager - quản lý việc thay đổi theme cho toàn bộ ứng dụng
 * Sử dụng Observer pattern để cập nhật theme cho tất cả các frame
 */
public class ThemeManager {
	private static List<JFrame> registeredFrames = new ArrayList<>();       // Danh sách các frame đã đăng ký
	private static List<ThemeChangeListener> listeners = new ArrayList<>(); // Danh sách các listener
	
	// === THEME CHANGE LISTENER INTERFACE - Interface để lắng nghe thay đổi theme ===
	public interface ThemeChangeListener {
		void onThemeChanged(Theme.ThemeVariant newTheme);
	}
	
	// === FRAME REGISTRATION - Đăng ký frame để cập nhật theme ===
	public static void registerFrame(JFrame frame) {
		if (!registeredFrames.contains(frame)) {
			registeredFrames.add(frame);
		}
	}
	
	public static void unregisterFrame(JFrame frame) {
		registeredFrames.remove(frame);
	}
	
	// === LISTENER MANAGEMENT - Quản lý các listener ===
	public static void addThemeChangeListener(ThemeChangeListener listener) {
		listeners.add(listener);
	}
	
	public static void removeThemeChangeListener(ThemeChangeListener listener) {
		listeners.remove(listener);
	}
	
	// === THEME SWITCHING ===
	public static void changeTheme(Theme.ThemeVariant newTheme) {
		Theme.setTheme(newTheme);
		
		// Notify all listeners
		for (ThemeChangeListener listener : listeners) {
			listener.onThemeChanged(newTheme);
		}
		
		// Update all registered frames
		for (JFrame frame : registeredFrames) {
			if (frame != null && frame.isDisplayable()) {
				SwingUtilities.invokeLater(() -> {
					updateFrameTheme(frame);
					frame.repaint();
				});
			}
		}
	}
	
	// === UI COMPONENT STYLING (Enhanced for VNC) ===
	public static void styleButton(JButton button, ButtonStyle style) {
		button.setFont(Theme.getBestFont(Theme.FONT_BUTTON, Theme.FONT_BUTTON_ALT));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setOpaque(true);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setPreferredSize(new Dimension(
			Math.max(button.getPreferredSize().width, Theme.BUTTON_PRIMARY_WIDTH), 
			Theme.BUTTON_HEIGHT
		));
		
		switch (style) {
			case PRIMARY:
				styleButtonPrimary(button);
				break;
			case SECONDARY:
				button.setBackground(Theme.getSecondaryColor());
				button.setForeground(Theme.getWhiteColor());
				break;
			case SUCCESS:
				button.setBackground(Theme.getSuccessColor());
				button.setForeground(Theme.getWhiteColor());
				break;
			case DANGER:
				button.setBackground(Theme.getDangerColor());
				button.setForeground(Theme.getWhiteColor());
				break;
			case WARNING:
				button.setBackground(Theme.getWarningColor());
				button.setForeground(Theme.getTextColor());
				break;
			case INFO:
				button.setBackground(Theme.getInfoColor());
				button.setForeground(Theme.getWhiteColor());
				break;
			case OUTLINE:
				styleButtonOutline(button);
				break;
		}
		
		addButtonHoverEffects(button, style);
		addButtonShadowEffect(button);
	}
	
	private static void styleButtonPrimary(JButton button) {
		button.setBackground(Theme.getPrimaryColor());
		button.setForeground(Theme.getWhiteColor());
		button.setBorder(Theme.getRoundedBorder());
	}
	
	private static void styleButtonOutline(JButton button) {
		button.setBackground(new Color(0, 0, 0, 0));
		button.setForeground(Theme.getPrimaryColor());
		button.setBorderPainted(true);
		button.setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.LineBorder(Theme.getPrimaryColor(), 2),
			new javax.swing.border.EmptyBorder(8, 16, 8, 16)
		));
	}
	
	private static void addButtonShadowEffect(JButton button) {
		// Add subtle shadow effect for depth
		button.putClientProperty("JButton.buttonType", "roundRect");
	}
	
	public static void styleTextField(JTextField textField) {
		textField.setFont(Theme.getBestFont(Theme.FONT_INPUT, Theme.FONT_INPUT_ALT));
		textField.setBackground(Theme.getWhiteColor());
		textField.setForeground(Theme.getTextColor());
		textField.setBorder(Theme.getInputBorder());
		textField.setCaretColor(Theme.getPrimaryColor());
		textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, Theme.INPUT_HEIGHT));
		
		// Add focus effects
		textField.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				textField.setBorder(Theme.getInputFocusBorder());
			}
			
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				textField.setBorder(Theme.getInputBorder());
			}
		});
	}
	
	public static void stylePasswordField(JPasswordField passwordField) {
		passwordField.setFont(Theme.getBestFont(Theme.FONT_INPUT, Theme.FONT_INPUT_ALT));
		passwordField.setBackground(Theme.getWhiteColor());
		passwordField.setForeground(Theme.getTextColor());
		passwordField.setBorder(Theme.getInputBorder());
		passwordField.setCaretColor(Theme.getPrimaryColor());
		passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, Theme.INPUT_HEIGHT));
		
		// Add focus effects
		passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				passwordField.setBorder(Theme.getInputFocusBorder());
			}
			
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				passwordField.setBorder(Theme.getInputBorder());
			}
		});
	}
	
	public static void styleLabel(JLabel label, LabelStyle style) {
		label.setForeground(Theme.getOptimalTextColor());
		
		switch (style) {
			case TITLE:
				label.setFont(Theme.getBestFont(Theme.FONT_TITLE, Theme.FONT_TITLE_ALT));
				label.setForeground(Theme.getPrimaryColor());
				break;
			case SUBTITLE:
				label.setFont(Theme.getBestFont(Theme.FONT_SUBTITLE, Theme.FONT_SUBTITLE_ALT));
				break;
			case HEADING:
				label.setFont(Theme.getBestFont(Theme.FONT_HEADING, Theme.FONT_HEADING_ALT));
				break;
			case SUBHEADING:
				label.setFont(Theme.getBestFont(Theme.FONT_SUBHEADING, Theme.FONT_SUBHEADING_ALT));
				break;
			case REGULAR:
				label.setFont(Theme.getBestFont(Theme.FONT_REGULAR, Theme.FONT_REGULAR_ALT));
				break;
			case CAPTION:
				label.setFont(Theme.FONT_CAPTION);
				label.setForeground(Theme.getSecondaryColor());
				break;
			case SMALL:
				label.setFont(Theme.FONT_SMALL);
				label.setForeground(Theme.getSecondaryColor());
				break;
		}
	}
	
	public static void stylePanel(JPanel panel, PanelStyle style) {
		switch (style) {
			case MAIN:
				panel.setBackground(Theme.getBackgroundPanel());
				break;
			case HEADER:
				panel.setBackground(Theme.getPrimaryColor());
				break;
			case CARD:
				panel.setBackground(Theme.getCardBackgroundColor());
				panel.setBorder(Theme.getCardBorder());
				break;
			case LIGHT:
				panel.setBackground(Theme.getLightBackground());
				break;
		}
	}
	
	// === ENUMS FOR STYLING ===
	public enum ButtonStyle {
		PRIMARY, SECONDARY, SUCCESS, DANGER, WARNING, INFO, OUTLINE
	}
	
	public enum LabelStyle {
		TITLE, SUBTITLE, HEADING, SUBHEADING, REGULAR, CAPTION, SMALL
	}
	
	public enum PanelStyle {
		MAIN, HEADER, CARD, LIGHT
	}
	
	// === PRIVATE HELPER METHODS (Enhanced) ===
	private static void addButtonHoverEffects(JButton button, ButtonStyle style) {
		Color originalColor = button.getBackground();
		Color originalForeground = button.getForeground();
		
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				if (style != ButtonStyle.OUTLINE) {
					button.setBackground(Theme.getButtonHoverColor());
				} else {
					button.setBackground(Theme.getHoverOverlayColor());
				}
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				button.setBackground(originalColor);
				button.setForeground(originalForeground);
			}
			
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (style != ButtonStyle.OUTLINE) {
					button.setBackground(Theme.getButtonPressedColor());
				} else {
					button.setBackground(Theme.getPressedOverlayColor());
				}
			}
			
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (button.contains(e.getPoint())) {
					// Mouse still over button
					if (style != ButtonStyle.OUTLINE) {
						button.setBackground(Theme.getButtonHoverColor());
					} else {
						button.setBackground(Theme.getHoverOverlayColor());
					}
				} else {
					// Mouse not over button
					button.setBackground(originalColor);
					button.setForeground(originalForeground);
				}
			}
		});
	}
	
	private static void updateFrameTheme(JFrame frame) {
		updateComponentTheme(frame.getContentPane());
	}
	
	private static void updateComponentTheme(Container container) {
		for (Component component : container.getComponents()) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				// Update panel colors based on current background
				if (panel.getBackground().equals(Theme.BACKGROUND_PANEL)) {
					panel.setBackground(Theme.getBackgroundPanel());
				}
			} else if (component instanceof JButton) {
				JButton button = (JButton) component;
				// Update button colors - you may need to track button styles
				// This is a simplified update
				if (button.getBackground().equals(Theme.BACKGROUND_BUTTON_PRIMARY)) {
					button.setBackground(Theme.getPrimaryColor());
				}
			} else if (component instanceof JLabel) {
				JLabel label = (JLabel) component;
				// Update label colors
				if (label.getForeground().equals(Theme.COLOR_TITLE)) {
					label.setForeground(Theme.getPrimaryColor());
				}
			}
			
			// Recursively update child components
			if (component instanceof Container) {
				updateComponentTheme((Container) component);
			}
		}
	}
}
