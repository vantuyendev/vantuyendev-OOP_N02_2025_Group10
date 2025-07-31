package com.shopmanagement.service;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.entity.Login;
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
    
    @Autowired
    private LoginService loginService;
    
    /**
     * Tạo nhân viên mới (bao gồm tài khoản đăng nhập)
     * @param employee thông tin nhân viên
     * @param password mật khẩu
     * @return Employee object đã được lưu
     */
    public Employee createEmployee(Employee employee, String password) {
        // Tạo tài khoản đăng nhập trước
        Login login = new Login(employee.getUserId(), password, 0); // 0 = Employee status
        loginService.createLogin(login);
        
        // Lưu thông tin nhân viên
        employee.setLogin(login);
        return employeeRepository.save(employee);
    }
    
    /**
     * Cập nhật thông tin nhân viên
     * @param employee thông tin nhân viên cần cập nhật
     * @return Employee object đã được cập nhật
     */
    public Employee updateEmployee(Employee employee) {
        if (!employeeRepository.existsById(employee.getUserId())) {
            throw new IllegalArgumentException("Employee không tồn tại với ID: " + employee.getUserId());
        }
        return employeeRepository.save(employee);
    }
    
    /**
     * Xóa nhân viên (bao gồm tài khoản đăng nhập)
     * @param userId ID nhân viên
     */
    public void deleteEmployee(String userId) {
        if (employeeRepository.existsById(userId)) {
            employeeRepository.deleteById(userId);
            // Xóa tài khoản đăng nhập
            loginService.deleteLogin(userId);
        }
    }
    
    /**
     * Tìm nhân viên theo userId
     * @param userId ID nhân viên
     * @return Employee object nếu tìm thấy
     */
    public Optional<Employee> findById(String userId) {
        return employeeRepository.findById(userId);
    }
    
    /**
     * Tìm nhân viên theo userId kèm thông tin đăng nhập
     * @param userId ID nhân viên
     * @return Employee object kèm thông tin đăng nhập
     */
    public Optional<Employee> findByIdWithLogin(String userId) {
        return employeeRepository.findByUserIdWithLogin(userId);
    }
    
    /**
     * Lấy tất cả nhân viên
     * @return danh sách tất cả nhân viên
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    /**
     * Lấy tất cả nhân viên kèm thông tin đăng nhập
     * @return danh sách tất cả nhân viên kèm thông tin đăng nhập
     */
    public List<Employee> findAllWithLogin() {
        return employeeRepository.findAllWithLogin();
    }
    
    /**
     * Tìm nhân viên theo tên
     * @param name tên nhân viên (tìm kiếm gần đúng)
     * @return danh sách nhân viên phù hợp
     */
    public List<Employee> findByName(String name) {
        return employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
    }
    
    /**
     * Tìm nhân viên theo chức vụ
     * @param role chức vụ
     * @return danh sách nhân viên có chức vụ đó
     */
    public List<Employee> findByRole(String role) {
        return employeeRepository.findByRole(role);
    }
    
    /**
     * Tìm nhân viên theo phòng ban
     * @param department phòng ban
     * @return danh sách nhân viên thuộc phòng ban đó
     */
    public List<Employee> findByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    /**
     * Tìm nhân viên theo số điện thoại
     * @param phoneNumber số điện thoại
     * @return Employee object nếu tìm thấy
     */
    public Optional<Employee> findByPhoneNumber(String phoneNumber) {
        return employeeRepository.findByPhoneNumber(phoneNumber);
    }
    
    /**
     * Tìm nhân viên theo khoảng lương
     * @param minSalary lương tối thiểu
     * @param maxSalary lương tối đa
     * @return danh sách nhân viên trong khoảng lương
     */
    public List<Employee> findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return employeeRepository.findBySalaryRange(minSalary, maxSalary);
    }
    
    /**
     * Tìm nhân viên có lương cao nhất
     * @return danh sách nhân viên có lương cao nhất
     */
    public List<Employee> findEmployeesWithHighestSalary() {
        return employeeRepository.findEmployeesWithHighestSalary();
    }
    
    /**
     * Đếm số nhân viên theo chức vụ
     * @param role chức vụ
     * @return số lượng nhân viên
     */
    public long countByRole(String role) {
        return employeeRepository.countByRole(role);
    }
    
    /**
     * Tính tổng lương tất cả nhân viên
     * @return tổng lương
     */
    public BigDecimal getTotalSalary() {
        BigDecimal total = employeeRepository.getTotalSalary();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Tính lương trung bình
     * @return lương trung bình
     */
    public BigDecimal getAverageSalary() {
        BigDecimal average = employeeRepository.getAverageSalary();
        return average != null ? average : BigDecimal.ZERO;
    }
    
    /**
     * Đếm tổng số nhân viên
     * @return số lượng nhân viên
     */
    public long count() {
        return employeeRepository.count();
    }
    
    /**
     * Kiểm tra nhân viên có tồn tại hay không
     * @param userId ID nhân viên
     * @return true nếu tồn tại, false nếu không
     */
    public boolean existsById(String userId) {
        return employeeRepository.existsById(userId);
    }
}
