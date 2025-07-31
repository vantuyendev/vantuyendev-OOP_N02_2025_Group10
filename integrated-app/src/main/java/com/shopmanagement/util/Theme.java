package com.shopmanagement.util;

import java.awt.*;
import javax.swing.border.*;

/**
 * Class Theme - quản lý giao diện và màu sắc cho toàn bộ ứng dụng
 * Chứa các constants và theme variants để customization giao diện
 */
public class Theme {
	// === LAYOUT CONSTANTS - Các hằng số về kích thước giao diện (Optimized for VNC) ===
	public static final int GUI_WIDTH = 1024;
	public static final int GUI_HEIGHT = 768;
	public static final int BUTTON_PRIMARY_WIDTH = 140;
	public static final int BUTTON_SECONDARY_WIDTH = 120;
	public static final int BUTTON_HEIGHT = 42;
	public static final int INPUT_HEIGHT = 38;
	public static final int PANEL_PADDING = 24;
	public static final int COMPONENT_SPACING = 18;
	public static final int HEADER_HEIGHT = 90;
	public static final int CARD_RADIUS = 12;
	public static final int SHADOW_OFFSET = 4;
	
	// === CURRENT THEME SELECTION - Theme hiện tại được chọn ===
	public static ThemeVariant currentTheme = ThemeVariant.PROFESSIONAL_BLUE;
	
	// === THEME VARIANTS - Các biến thể theme có sẵn ===
	public enum ThemeVariant {
		PROFESSIONAL_BLUE,
		MODERN_DARK,
		ELEGANT_PURPLE,
		FRESH_GREEN,
		WARM_ORANGE,
		CLASSIC_GRAY,
		ULTRA_MODERN,    // New modern theme
		MIDNIGHT_BLUE,   // New dark blue theme
		CHERRY_BLOSSOM   // New pink theme
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
	
	// Ultra Modern Theme - New sleek design
	private static final Color[] ULTRA_MODERN_COLORS = {
		new Color(250, 251, 252), // Background Panel - Ultra light
		new Color(59, 130, 246),  // Primary Color - Modern blue
		new Color(71, 85, 105),   // Secondary Color - Slate
		new Color(255, 255, 255), // White
		new Color(15, 23, 42),    // Dark Text - Deep slate
		new Color(248, 250, 252), // Light Background
		new Color(239, 68, 68),   // Danger/Error - Modern red
		new Color(34, 197, 94),   // Success - Modern green
		new Color(245, 158, 11),  // Warning - Modern amber
		new Color(14, 165, 233)   // Info - Modern sky
	};
	
	// Midnight Blue Theme - Professional dark
	private static final Color[] MIDNIGHT_BLUE_COLORS = {
		new Color(15, 23, 42),    // Background Panel - Dark slate
		new Color(56, 189, 248),  // Primary Color - Light blue
		new Color(71, 85, 105),   // Secondary Color - Slate
		new Color(248, 250, 252), // White - Off white
		new Color(226, 232, 240), // Light Text
		new Color(30, 41, 59),    // Dark Background
		new Color(248, 113, 113), // Danger/Error - Light red
		new Color(74, 222, 128),  // Success - Light green
		new Color(251, 191, 36),  // Warning - Light amber
		new Color(56, 189, 248)   // Info - Light blue
	};
	
	// Cherry Blossom Theme - Elegant pink
	private static final Color[] CHERRY_BLOSSOM_COLORS = {
		new Color(253, 244, 255), // Background Panel - Very light pink
		new Color(219, 39, 119),  // Primary Color - Pink
		new Color(107, 114, 128), // Secondary Color - Gray
		new Color(255, 255, 255), // White
		new Color(17, 24, 39),    // Dark Text
		new Color(253, 244, 255), // Light Background
		new Color(239, 68, 68),   // Danger/Error
		new Color(34, 197, 94),   // Success
		new Color(245, 158, 11),  // Warning
		new Color(168, 85, 247)   // Info - Purple
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
			case ULTRA_MODERN:
				return ULTRA_MODERN_COLORS;
			case MIDNIGHT_BLUE:
				return MIDNIGHT_BLUE_COLORS;
			case CHERRY_BLOSSOM:
				return CHERRY_BLOSSOM_COLORS;
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
	
	// === ENHANCED TYPOGRAPHY (VNC Optimized) ===
	public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 36);
	public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 28);
	public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 22);
	public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 18);
	public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
	public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 16);
	public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 16);
	public static final Font FONT_CAPTION = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
	
	// Alternative fonts for better VNC compatibility
	public static final Font FONT_TITLE_ALT = new Font("SansSerif", Font.BOLD, 36);
	public static final Font FONT_SUBTITLE_ALT = new Font("SansSerif", Font.BOLD, 28);
	public static final Font FONT_HEADING_ALT = new Font("SansSerif", Font.BOLD, 22);
	public static final Font FONT_SUBHEADING_ALT = new Font("SansSerif", Font.BOLD, 18);
	public static final Font FONT_BUTTON_ALT = new Font("SansSerif", Font.BOLD, 16);
	public static final Font FONT_REGULAR_ALT = new Font("SansSerif", Font.PLAIN, 16);
	public static final Font FONT_INPUT_ALT = new Font("SansSerif", Font.PLAIN, 16);
	
	// Method to get best available font
	public static Font getBestFont(Font primary, Font fallback) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();
		for (String fontName : fontNames) {
			if (fontName.equals(primary.getFontName())) {
				return primary;
			}
		}
		return fallback;
	}
	
	// === ENHANCED BORDERS (Modern Design) ===
	public static Border getInputBorder() {
		return new CompoundBorder(
			new LineBorder(getInputBorderColor(), 2),
			new EmptyBorder(10, 14, 10, 14)
		);
	}
	
	public static Border getInputFocusBorder() {
		return new CompoundBorder(
			new LineBorder(getInputFocusBorderColor(), 3),
			new EmptyBorder(9, 13, 9, 13)
		);
	}
	
	public static Border getCardBorder() {
		return new CompoundBorder(
			new LineBorder(new Color(229, 231, 235), 1),
			new EmptyBorder(20, 20, 20, 20)
		);
	}
	
	public static Border getRoundedBorder() {
		return new EmptyBorder(12, 20, 12, 20);
	}
	
	public static Border getModernCardBorder() {
		return new CompoundBorder(
			new LineBorder(new Color(229, 231, 235), 1),
			new EmptyBorder(24, 24, 24, 24)
		);
	}
	
	public static Border getElevatedBorder() {
		return new CompoundBorder(
			new LineBorder(getCardShadowColor(), 2),
			new EmptyBorder(16, 20, 20, 20)
		);
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
			case ULTRA_MODERN:
				return "Ultra Modern";
			case MIDNIGHT_BLUE:
				return "Midnight Blue";
			case CHERRY_BLOSSOM:
				return "Cherry Blossom";
			default:
				return "Professional Blue";
		}
	}
	
	// === MODERN GRADIENT COLORS ===
	public static Color[] getPrimaryGradient() {
		Color primary = getPrimaryColor();
		Color lighter = new Color(
			Math.min(255, primary.getRed() + 30),
			Math.min(255, primary.getGreen() + 30),
			Math.min(255, primary.getBlue() + 30)
		);
		return new Color[]{primary, lighter};
	}
	
	public static Color getHoverOverlayColor() {
		return new Color(255, 255, 255, 20);
	}
	
	public static Color getPressedOverlayColor() {
		return new Color(0, 0, 0, 20);
	}
	
	// === VNC SPECIFIC OPTIMIZATIONS ===
	public static boolean isHighContrastMode() {
		return currentTheme == ThemeVariant.MODERN_DARK || 
			   currentTheme == ThemeVariant.MIDNIGHT_BLUE;
	}
	
	public static Color getOptimalTextColor() {
		return isHighContrastMode() ? getWhiteColor() : getTextColor();
	}
	
	public static int getOptimalFontSize(int baseSize) {
		// Increase font size slightly for better VNC readability
		return baseSize + 2;
	}
}