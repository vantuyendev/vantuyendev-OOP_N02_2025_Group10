package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity cho bảng user_roles (liên kết người dùng và vai trò)
 * Quản lý mối quan hệ nhiều-nhiều giữa User và Role
 */
@Entity
@Table(name = "user_roles", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "roleId"}))
public class UserRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userRoleId")
    private Long userRoleId;
    
    @Column(name = "userId", nullable = false)
    @NotBlank(message = "User ID không được để trống")
    private String userId;
    
    @Column(name = "roleId", nullable = false)
    @NotNull(message = "Role ID không được để trống")
    private Long roleId;
    
    @Column(name = "assignedAt", nullable = false)
    private LocalDateTime assignedAt;
    
    @Column(name = "assignedBy")
    private String assignedBy;
    
    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private Login login;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private Role role;
    
    // Constructors
    public UserRole() {
        this.assignedAt = LocalDateTime.now();
    }
    
    public UserRole(String userId, Long roleId) {
        this();
        this.userId = userId;
        this.roleId = roleId;
    }
    
    public UserRole(String userId, Long roleId, String assignedBy) {
        this(userId, roleId);
        this.assignedBy = assignedBy;
    }
    
    // Getters and Setters
    public Long getUserRoleId() {
        return userRoleId;
    }
    
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }
    
    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
    
    public String getAssignedBy() {
        return assignedBy;
    }
    
    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Login getLogin() {
        return login;
    }
    
    public void setLogin(Login login) {
        this.login = login;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                ", userId='" + userId + '\'' +
                ", roleId=" + roleId +
                ", assignedAt=" + assignedAt +
                ", isActive=" + isActive +
                '}';
    }
}
