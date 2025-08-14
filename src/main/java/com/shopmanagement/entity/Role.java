package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity cho bảng roles (vai trò người dùng)
 * Quản lý phân quyền trong hệ thống
 */
@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Long roleId;
    
    @Column(name = "roleName", nullable = false, unique = true)
    @NotBlank(message = "Tên vai trò không được để trống")
    @Size(max = 50, message = "Tên vai trò không được vượt quá 50 ký tự")
    private String roleName;
    
    @Column(name = "roleDescription", columnDefinition = "TEXT")
    private String roleDescription;
    
    @Column(name = "isActive", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;
    
    // Predefined role constants
    public static final String ADMIN = "ADMIN";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String STAFF = "STAFF";
    public static final String MANAGER = "MANAGER";
    
    // Constructors
    public Role() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Role(String roleName, String roleDescription) {
        this();
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }
    
    // Getters and Setters
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleDescription() {
        return roleDescription;
    }
    
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
    
    public List<UserRole> getUserRoles() {
        return userRoles;
    }
    
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
