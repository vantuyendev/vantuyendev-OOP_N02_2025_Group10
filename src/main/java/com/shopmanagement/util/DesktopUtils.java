package com.shopmanagement.util;

import javax.swing.*;
import java.awt.*;

/**
 * Desktop GUI Utilities
 * Các tiện ích hỗ trợ cho desktop application
 */
public class DesktopUtils {
    
    /**
     * Thiết lập Look and Feel mặc định cho ứng dụng
     */
    public static void setupLookAndFeel() {
        try {
            // Sử dụng Metal Look and Feel (default)
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            // Thiết lập font mặc định
            Font defaultFont = new Font("SansSerif", Font.PLAIN, 12);
            UIManager.put("Label.font", defaultFont);
            UIManager.put("Button.font", defaultFont);
            UIManager.put("TextField.font", defaultFont);
            UIManager.put("TextArea.font", defaultFont);
            UIManager.put("ComboBox.font", defaultFont);
            UIManager.put("Table.font", defaultFont);
            UIManager.put("TableHeader.font", defaultFont.deriveFont(Font.BOLD));
            
        } catch (Exception e) {
            System.err.println("Could not set Look and Feel: " + e.getMessage());
        }
    }
    
    /**
     * Hiển thị message dialog với styling phù hợp
     */
    public static void showInfoMessage(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Hiển thị error dialog
     */
    public static void showErrorMessage(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Hiển thị confirmation dialog
     */
    public static int showConfirmDialog(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
    }
    
    /**
     * Căn giữa window trên màn hình
     */
    public static void centerWindow(Window window) {
        window.setLocationRelativeTo(null);
    }
    
    /**
     * Thiết lập kích thước và position cho window
     */
    public static void setupWindow(Window window, int width, int height) {
        window.setSize(width, height);
        centerWindow(window);
    }
}
