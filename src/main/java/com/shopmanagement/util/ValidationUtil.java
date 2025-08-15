package com.shopmanagement.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Utility class cho việc validation dữ liệu
 */
@Component
public class ValidationUtil {
    
    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9+\\-\\s()]{10,15}$"
    );
    
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );
    
    /**
     * Kiểm tra email hợp lệ
     */
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Kiểm tra số điện thoại hợp lệ
     */
    public boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Kiểm tra password mạnh
     * Ít nhất 8 ký tự, có chữ hoa, chữ thường, số và ký tự đặc biệt
     */
    public boolean isStrongPassword(String password) {
        return password != null && STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Kiểm tra string không null và không rỗng
     */
    public boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Kiểm tra userId hợp lệ
     * 3-50 ký tự, chỉ chứa chữ, số và dấu gạch dưới
     */
    public boolean isValidUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return userId.matches("^[a-zA-Z0-9_]{3,50}$");
    }
    
    /**
     * Kiểm tra tên hợp lệ
     * 2-100 ký tự, có thể chứa dấu
     */
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.trim().length() >= 2 && name.trim().length() <= 100;
    }
    
    /**
     * Làm sạch và chuẩn hóa string
     */
    public String sanitizeString(String str) {
        if (str == null) {
            return null;
        }
        return str.trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Kiểm tra giá tiền hợp lệ
     */
    public boolean isValidPrice(java.math.BigDecimal price) {
        return price != null && price.compareTo(java.math.BigDecimal.ZERO) > 0;
    }
    
    /**
     * Kiểm tra số lượng hợp lệ
     */
    public boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity >= 0;
    }
    
    /**
     * Kiểm tra mã barcode hợp lệ
     */
    public boolean isValidBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return true; // Barcode có thể để trống
        }
        return barcode.matches("^[A-Za-z0-9]{5,50}$");
    }
    
    /**
     * Validate tất cả các trường của Customer
     */
    public String validateCustomer(String userId, String customerName, String email, String phone) {
        if (!isValidUserId(userId)) {
            return "User ID không hợp lệ. Phải có 3-50 ký tự, chỉ chứa chữ, số và dấu gạch dưới.";
        }
        
        if (!isValidName(customerName)) {
            return "Tên khách hàng phải có từ 2-100 ký tự.";
        }
        
        if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
            return "Email không hợp lệ.";
        }
        
        if (phone != null && !phone.trim().isEmpty() && !isValidPhone(phone)) {
            return "Số điện thoại không hợp lệ.";
        }
        
        return null; // Không có lỗi
    }
    
    /**
     * Validate tất cả các trường của Employee
     */
    public String validateEmployee(String userId, String employeeName, String email, String phone) {
        if (!isValidUserId(userId)) {
            return "User ID không hợp lệ. Phải có 3-50 ký tự, chỉ chứa chữ, số và dấu gạch dưới.";
        }
        
        if (!isValidName(employeeName)) {
            return "Tên nhân viên phải có từ 2-100 ký tự.";
        }
        
        if (email != null && !email.trim().isEmpty() && !isValidEmail(email)) {
            return "Email không hợp lệ.";
        }
        
        if (phone != null && !phone.trim().isEmpty() && !isValidPhone(phone)) {
            return "Số điện thoại không hợp lệ.";
        }
        
        return null; // Không có lỗi
    }
    
    /**
     * Validate Product
     */
    public String validateProduct(String productName, java.math.BigDecimal price, Integer quantity, String barcode) {
        if (!isValidName(productName)) {
            return "Tên sản phẩm phải có từ 2-100 ký tự.";
        }
        
        if (!isValidPrice(price)) {
            return "Giá sản phẩm phải lớn hơn 0.";
        }
        
        if (!isValidQuantity(quantity)) {
            return "Số lượng sản phẩm phải >= 0.";
        }
        
        if (!isValidBarcode(barcode)) {
            return "Mã barcode không hợp lệ.";
        }
        
        return null; // Không có lỗi
    }
}
