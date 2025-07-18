package attr;

import java.awt.*;
import javax.swing.border.*;

public class Theme {
	// === LAYOUT CONSTANTS ===
	public static final int GUI_WIDTH = 900;
	public static final int GUI_HEIGHT = 700;
	public static final int BUTTON_PRIMARY_WIDTH = 120;
	public static final int BUTTON_SECONDARY_WIDTH = 100;
	public static final int BUTTON_HEIGHT = 35;
	public static final int INPUT_HEIGHT = 30;
	public static final int PANEL_PADDING = 20;
	public static final int COMPONENT_SPACING = 15;
	public static final int HEADER_HEIGHT = 80;
	
	// === CURRENT THEME SELECTION ===
	public static ThemeVariant currentTheme = ThemeVariant.PROFESSIONAL_BLUE;
	
	// === THEME VARIANTS ===
	public enum ThemeVariant {
		PROFESSIONAL_BLUE,
		MODERN_DARK,
		ELEGANT_PURPLE,
		FRESH_GREEN,
		WARM_ORANGE,
		CLASSIC_GRAY
	}
	
	// === COLOR PALETTES ===
	// Professional Blue Theme
	private static final Color[] PROFESSIONAL_BLUE_COLORS = {
		new Color(248, 249, 250), // Background Panel
		new Color(13, 110, 253),  // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(255, 255, 255), // White
		new Color(33, 37, 41),    // Dark Text
		new Color(248, 249, 250), // Light Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(25, 135, 84),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(13, 202, 240)   // Info
	};
	
	// Modern Dark Theme
	private static final Color[] MODERN_DARK_COLORS = {
		new Color(33, 37, 41),    // Background Panel
		new Color(0, 123, 255),   // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(248, 249, 250), // White
		new Color(248, 249, 250), // Light Text
		new Color(52, 58, 64),    // Dark Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(40, 167, 69),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(23, 162, 184)   // Info
	};
	
	// Elegant Purple Theme
	private static final Color[] ELEGANT_PURPLE_COLORS = {
		new Color(248, 246, 252), // Background Panel
		new Color(102, 16, 242),  // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(255, 255, 255), // White
		new Color(33, 37, 41),    // Dark Text
		new Color(248, 246, 252), // Light Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(25, 135, 84),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(111, 66, 193)   // Info
	};
	
	// Fresh Green Theme
	private static final Color[] FRESH_GREEN_COLORS = {
		new Color(247, 254, 231), // Background Panel
		new Color(52, 168, 83),   // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(255, 255, 255), // White
		new Color(33, 37, 41),    // Dark Text
		new Color(247, 254, 231), // Light Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(25, 135, 84),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(23, 162, 184)   // Info
	};
	
	// Warm Orange Theme
	private static final Color[] WARM_ORANGE_COLORS = {
		new Color(255, 248, 240), // Background Panel
		new Color(255, 109, 0),   // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(255, 255, 255), // White
		new Color(33, 37, 41),    // Dark Text
		new Color(255, 248, 240), // Light Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(25, 135, 84),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(253, 126, 20)   // Info
	};
	
	// Classic Gray Theme
	private static final Color[] CLASSIC_GRAY_COLORS = {
		new Color(248, 249, 250), // Background Panel
		new Color(73, 80, 87),    // Primary Color
		new Color(108, 117, 125), // Secondary Color
		new Color(255, 255, 255), // White
		new Color(33, 37, 41),    // Dark Text
		new Color(248, 249, 250), // Light Background
		new Color(220, 53, 69),   // Danger/Error
		new Color(25, 135, 84),   // Success
		new Color(255, 193, 7),   // Warning
		new Color(23, 162, 184)   // Info
	};
	
	// === DYNAMIC COLOR GETTERS ===
	public static Color getBackgroundPanel() {
		return getCurrentThemeColors()[0];
	}
	
	public static Color getPrimaryColor() {
		return getCurrentThemeColors()[1];
	}
	
	public static Color getSecondaryColor() {
		return getCurrentThemeColors()[2];
	}
	
	public static Color getWhiteColor() {
		return getCurrentThemeColors()[3];
	}
	
	public static Color getTextColor() {
		return getCurrentThemeColors()[4];
	}
	
	public static Color getLightBackground() {
		return getCurrentThemeColors()[5];
	}
	
	public static Color getDangerColor() {
		return getCurrentThemeColors()[6];
	}
	
	public static Color getSuccessColor() {
		return getCurrentThemeColors()[7];
	}
	
	public static Color getWarningColor() {
		return getCurrentThemeColors()[8];
	}
	
	public static Color getInfoColor() {
		return getCurrentThemeColors()[9];
	}
	
	// === HELPER METHOD ===
	private static Color[] getCurrentThemeColors() {
		switch (currentTheme) {
			case MODERN_DARK:
				return MODERN_DARK_COLORS;
			case ELEGANT_PURPLE:
				return ELEGANT_PURPLE_COLORS;
			case FRESH_GREEN:
				return FRESH_GREEN_COLORS;
			case WARM_ORANGE:
				return WARM_ORANGE_COLORS;
			case CLASSIC_GRAY:
				return CLASSIC_GRAY_COLORS;
			default:
				return PROFESSIONAL_BLUE_COLORS;
		}
	}
	
	// === BACKWARD COMPATIBILITY ===
	public static final Color BACKGROUND_PANEL = getBackgroundPanel();
	public static final Color BACKGROUND_HEADER = getPrimaryColor();
	public static final Color COLOR_TITLE = getPrimaryColor();
	public static final Color COLOR_BUTTON_PRIMARY = getWhiteColor();
	public static final Color BACKGROUND_BUTTON_PRIMARY = getPrimaryColor();
	
	// === ENHANCED COLOR SCHEME ===
	public static Color getButtonHoverColor() {
		Color primary = getPrimaryColor();
		return new Color(
			Math.max(0, primary.getRed() - 20),
			Math.max(0, primary.getGreen() - 20),
			Math.max(0, primary.getBlue() - 20)
		);
	}
	
	public static Color getButtonPressedColor() {
		Color primary = getPrimaryColor();
		return new Color(
			Math.max(0, primary.getRed() - 40),
			Math.max(0, primary.getGreen() - 40),
			Math.max(0, primary.getBlue() - 40)
		);
	}
	
	public static Color getInputBorderColor() {
		return new Color(206, 212, 218);
	}
	
	public static Color getInputFocusBorderColor() {
		return getPrimaryColor();
	}
	
	public static Color getCardBackgroundColor() {
		return getWhiteColor();
	}
	
	public static Color getCardShadowColor() {
		return new Color(0, 0, 0, 20);
	}
	
	// === ENHANCED TYPOGRAPHY ===
	public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 32);
	public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 24);
	public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 20);
	public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 16);
	public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
	public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font FONT_CAPTION = new Font("Segoe UI", Font.PLAIN, 12);
	public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 10);
	
	// === ENHANCED BORDERS ===
	public static Border getInputBorder() {
		return new CompoundBorder(
			new LineBorder(getInputBorderColor(), 1),
			new EmptyBorder(8, 12, 8, 12)
		);
	}
	
	public static Border getInputFocusBorder() {
		return new CompoundBorder(
			new LineBorder(getInputFocusBorderColor(), 2),
			new EmptyBorder(7, 11, 7, 11)
		);
	}
	
	public static Border getCardBorder() {
		return new CompoundBorder(
			new LineBorder(new Color(222, 226, 230), 1),
			new EmptyBorder(16, 16, 16, 16)
		);
	}
	
	public static Border getRoundedBorder() {
		return new EmptyBorder(8, 16, 8, 16);
	}
	
	// === THEME SWITCHING ===
	public static void setTheme(ThemeVariant theme) {
		currentTheme = theme;
	}
	
	public static ThemeVariant getCurrentTheme() {
		return currentTheme;
	}
	
	public static String getThemeName() {
		switch (currentTheme) {
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
				return "Professional Blue";
		}
	}
}