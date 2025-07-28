package com.shopmanagement;

import java.lang.*;
import javax.swing.*;
import com.shopmanagement.activity.*;
import com.shopmanagement.util.*;

/**
 * Main class khởi tạo ứng dụng quản lý cửa hàng
 * Class này chứa phương thức main để khởi chạy ứng dụng với giao diện hiện đại
 */
public class Start {
	/**
	 * Phương thức main - điểm khởi đầu của ứng dụng
	 * Khởi tạo Look and Feel hiện đại và hiển thị splash screen
	 */
	public static void main(String args[]) {
		// Set modern Look and Feel for better VNC experience
		try {
			// Try to set system look and feel for better integration
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			// Configure for better VNC rendering
			System.setProperty("awt.useSystemAAFontSettings", "on");
			System.setProperty("swing.aatext", "true");
			System.setProperty("sun.java2d.xrender", "true");
			
		} catch (Exception e) {
			// Fallback to default if system LAF fails
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
				// Use default LAF
			}
		}
		
		// Set default theme to Ultra Modern for better VNC experience
		Theme.setTheme(Theme.ThemeVariant.ULTRA_MODERN);
		
		// Launch application with splash screen
		SwingUtilities.invokeLater(() -> {
			new SplashScreen().setVisible(true);
		});
	}
}
