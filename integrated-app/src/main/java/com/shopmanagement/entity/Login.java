package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity cho bảng login
 * Chứa thông tin đăng nhập của người dùng
 */
@Entity
@Table(name = "login")
public class Login {
    
    @Id
    @Column(name = "userId", length = 50)
    @NotBlank(message = "User ID không được để trống")
    @Size(max = 50, message = "User ID không được vượt quá 50 ký tự")
    private String userId;
    
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password không được để trống")
    private String password;
    
    @Column(name = "status", nullable = false)
    private Integer status; // 0: Employee, 1: Customer
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Login() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Login(String userId, String password, Integer status) {
        this();
        this.userId = userId;
        this.password = password;
        this.status = status;
    }
    
    // JPA Lifecycle callbacks
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Login{" +
                "userId='" + userId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
