package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity cho bảng employee
 * Chứa thông tin nhân viên
 */
@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @Column(name = "userId", length = 50)
    private String userId;
    
    @Column(name = "employeeName", nullable = false)
    @NotBlank(message = "Tên nhân viên không được để trống")
    @Size(max = 100, message = "Tên nhân viên không được vượt quá 100 ký tự")
    private String employeeName;
    
    @Column(name = "phoneNumber", length = 15)
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    
    @Column(name = "role", nullable = false)
    @NotBlank(message = "Chức vụ không được để trống")
    @Size(max = 50, message = "Chức vụ không được vượt quá 50 ký tự")
    private String role;
    
    @Column(name = "salary", precision = 10, scale = 2)
    @NotNull(message = "Lương không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Lương phải lớn hơn 0")
    private BigDecimal salary;
    
    @Column(name = "department", length = 50)
    private String department;
    
    @Column(name = "hire_date")
    private LocalDateTime hireDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationship với Login entity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Login login;
    
    // Constructors
    public Employee() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.hireDate = LocalDateTime.now();
    }
    
    public Employee(String userId, String employeeName, String role) {
        this();
        this.userId = userId;
        this.employeeName = employeeName;
        this.role = role;
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
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public BigDecimal getSalary() {
        return salary;
    }
    
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public LocalDateTime getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
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
    
    public Login getLogin() {
        return login;
    }
    
    public void setLogin(Login login) {
        this.login = login;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "userId='" + userId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
