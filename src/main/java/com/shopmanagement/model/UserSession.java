package com.shopmanagement.model;

/**
 * Model để lưu thông tin user session
 */
public class UserSession {
    private String userId;
    private String userType;  // ADMIN, CUSTOMER
    private Integer status;   // 1: Customer
    private String name;
    private String role;
    private String department;
    private String email;
    private String phone;
    
    // Constructors
    public UserSession() {}
    
    public UserSession(String userId, String userType, Integer status) {
        this.userId = userId;
        this.userType = userType;
        this.status = status;
    }
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    // Helper methods
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(userId) || "ADMIN".equals(userType);
    }
    
    public boolean isEmployee() {
        return false; // No employees in system
    }
    
    public boolean isCustomer() {
        return status != null && status == 1;
    }
    
    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
