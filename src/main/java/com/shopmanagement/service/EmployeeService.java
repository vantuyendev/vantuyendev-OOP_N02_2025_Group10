package com.shopmanagement.service;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class cho Employee entity
 * Chứa business logic cho việc quản lý nhân viên
 */
@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    /**
     * Tạo nhân viên mới
     */
    public Employee createEmployee(Employee employee) {
        // Kiểm tra email đã tồn tại chưa
        if (employee.getEmail() != null && employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email '" + employee.getEmail() + "' đã được sử dụng");
        }
        
        return employeeRepository.save(employee);
    }
    
    /**
     * Cập nhật thông tin nhân viên
     */
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getUserId());
        if (!existingEmployee.isPresent()) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID: " + employee.getUserId());
        }
        
        // Kiểm tra email trùng lặp (trừ chính nó)
        if (employee.getEmail() != null) {
            Optional<Employee> employeeWithSameEmail = employeeRepository.findByEmail(employee.getEmail());
            if (employeeWithSameEmail.isPresent() && 
                !employeeWithSameEmail.get().getUserId().equals(employee.getUserId())) {
                throw new RuntimeException("Email '" + employee.getEmail() + "' đã được sử dụng");
            }
        }
        
        return employeeRepository.save(employee);
    }
    
    /**
     * Xóa nhân viên
     */
    public void deleteEmployee(String userId) {
        Optional<Employee> employee = employeeRepository.findById(userId);
        if (!employee.isPresent()) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID: " + userId);
        }
        
        employeeRepository.deleteById(userId);
    }
    
    /**
     * Lấy tất cả nhân viên
     */
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    /**
     * Lấy nhân viên theo ID
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findById(String userId) {
        return employeeRepository.findById(userId);
    }
    
    /**
     * Tìm kiếm nhân viên theo tên
     */
    @Transactional(readOnly = true)
    public List<Employee> searchByName(String name) {
        return employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
    }
    
    /**
     * Lấy nhân viên theo department
     */
    @Transactional(readOnly = true)
    public List<Employee> findByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    /**
     * Lấy nhân viên theo role
     */
    @Transactional(readOnly = true)
    public List<Employee> findByRole(String role) {
        return employeeRepository.findByRole(role);
    }
    
    /**
     * Lấy nhân viên theo department và role
     */
    @Transactional(readOnly = true)
    public List<Employee> findByDepartmentAndRole(String department, String role) {
        return employeeRepository.findByDepartmentAndRole(department, role);
    }
    
    /**
     * Lấy danh sách department
     */
    @Transactional(readOnly = true)
    public List<String> getAllDepartments() {
        return employeeRepository.findDistinctDepartments();
    }
    
    /**
     * Lấy danh sách role
     */
    @Transactional(readOnly = true)
    public List<String> getAllRoles() {
        return employeeRepository.findDistinctRoles();
    }
    
    /**
     * Đếm số nhân viên theo department
     */
    @Transactional(readOnly = true)
    public long countByDepartment(String department) {
        return employeeRepository.countByDepartment(department);
    }
    
    /**
     * Đếm số nhân viên theo role
     */
    @Transactional(readOnly = true)
    public long countByRole(String role) {
        return employeeRepository.countByRole(role);
    }
    
    /**
     * Tìm nhân viên có lương trong khoảng
     */
    @Transactional(readOnly = true)
    public List<Employee> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }
    
    /**
     * Đếm tổng số nhân viên
     */
    @Transactional(readOnly = true)
    public long count() {
        return employeeRepository.count();
    }
    
    /**
     * Kiểm tra nhân viên tồn tại
     */
    @Transactional(readOnly = true)
    public boolean existsById(String userId) {
        return employeeRepository.existsById(userId);
    }
}
