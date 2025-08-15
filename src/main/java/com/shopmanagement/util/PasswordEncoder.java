package com.shopmanagement.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class cho việc mã hóa password
 * Sử dụng SHA-256 với salt để bảo mật password
 */
@Component
public class PasswordEncoder {
    
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    
    /**
     * Mã hóa password với salt ngẫu nhiên
     * @param plainPassword password gốc
     * @return password đã mã hóa kèm salt
     */
    public String encode(String plainPassword) {
        try {
            // Tạo salt ngẫu nhiên
            byte[] salt = generateSalt();
            
            // Mã hóa password với salt
            String hashedPassword = hashPassword(plainPassword, salt);
            
            // Kết hợp salt và hash (Base64 encoded)
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            
            return saltBase64 + ":" + hashedPassword;
            
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mã hóa password", e);
        }
    }
    
    /**
     * Kiểm tra password có khớp không
     * @param plainPassword password gốc
     * @param encodedPassword password đã mã hóa
     * @return true nếu khớp
     */
    public boolean matches(String plainPassword, String encodedPassword) {
        try {
            // Tách salt và hash
            String[] parts = encodedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            String saltBase64 = parts[0];
            String storedHash = parts[1];
            
            // Decode salt
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            
            // Hash password với salt đã lưu
            String hashedPassword = hashPassword(plainPassword, salt);
            
            // So sánh hash
            return hashedPassword.equals(storedHash);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Tạo salt ngẫu nhiên
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Hash password với salt
     */
    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        
        // Thêm salt vào đầu
        md.update(salt);
        
        // Hash password
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        
        // Encode thành Base64
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
    
    /**
     * Kiểm tra password có cần upgrade không (cho backward compatibility)
     * @param encodedPassword password đã mã hóa
     * @return true nếu cần upgrade (password cũ không có salt)
     */
    public boolean upgradeEncoding(String encodedPassword) {
        return !encodedPassword.contains(":");
    }
    
    /**
     * Tạo password ngẫu nhiên
     * @param length độ dài password
     * @return password ngẫu nhiên
     */
    public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}
