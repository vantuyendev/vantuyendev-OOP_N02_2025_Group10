package com.shopmanagement.controller;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller cho Employee API
 * Xử lý các request liên quan đến quản lý nhân viên
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * API lấy tất cả nhân viên
     * GET /api/employees
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Employee> employees = employeeService.findAllWithLogin();
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API lấy nhân viên theo ID
     * GET /api/employees/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Employee> employeeOpt = employeeService.findByIdWithLogin(userId);
            
            if (employeeOpt.isPresent()) {
                response.put("success", true);
                response.put("data", employeeOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy nhân viên với ID: " + userId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tạo nhân viên mới
     * POST /api/employees
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@Valid @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Parse request data
            String userId = (String) request.get("userId");
            String employeeName = (String) request.get("employeeName");
            String phoneNumber = (String) request.get("phoneNumber");
            String role = (String) request.get("role");
            String department = (String) request.get("department");
            String password = (String) request.get("password");
            
            // Handle salary parsing
            BigDecimal salary = null;
            Object salaryObj = request.get("salary");
            if (salaryObj != null) {
                if (salaryObj instanceof Number) {
                    salary = BigDecimal.valueOf(((Number) salaryObj).doubleValue());
                } else if (salaryObj instanceof String) {
                    salary = new BigDecimal((String) salaryObj);
                }
            }
            
            // Validate required fields
            if (userId == null || employeeName == null || role == null || password == null || salary == null) {
                response.put("success", false);
                response.put("message", "User ID, tên nhân viên, chức vụ, lương và mật khẩu là bắt buộc");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Create employee object
            Employee employee = new Employee();
            employee.setUserId(userId);
            employee.setEmployeeName(employeeName);
            employee.setPhoneNumber(phoneNumber);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSalary(salary);
            
            Employee savedEmployee = employeeService.createEmployee(employee, password);
            
            response.put("success", true);
            response.put("message", "Tạo nhân viên thành công");
            response.put("data", savedEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API cập nhật thông tin nhân viên
     * PUT /api/employees/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable String userId, 
                                                            @Valid @RequestBody Employee employee) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            employee.setUserId(userId); // Ensure userId matches path variable
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            
            response.put("success", true);
            response.put("message", "Cập nhật nhân viên thành công");
            response.put("data", updatedEmployee);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API xóa nhân viên
     * DELETE /api/employees/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!employeeService.existsById(userId)) {
                response.put("success", false);
                response.put("message", "Không tìm thấy nhân viên với ID: " + userId);
                return ResponseEntity.notFound().build();
            }
            
            employeeService.deleteEmployee(userId);
            
            response.put("success", true);
            response.put("message", "Xóa nhân viên thành công");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tìm kiếm nhân viên
     * GET /api/employees/search
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchEmployees(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String role,
                                                             @RequestParam(required = false) String department,
                                                             @RequestParam(required = false) String phone) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Employee> employees;
            
            if (name != null && !name.trim().isEmpty()) {
                employees = employeeService.findByName(name.trim());
            } else if (role != null && !role.trim().isEmpty()) {
                employees = employeeService.findByRole(role.trim());
            } else if (department != null && !department.trim().isEmpty()) {
                employees = employeeService.findByDepartment(department.trim());
            } else if (phone != null && !phone.trim().isEmpty()) {
                Optional<Employee> employeeOpt = employeeService.findByPhoneNumber(phone.trim());
                employees = employeeOpt.map(List::of).orElse(List.of());
            } else {
                employees = employeeService.findAll();
            }
            
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tìm nhân viên theo khoảng lương
     * GET /api/employees/salary-range?min={min}&max={max}
     */
    @GetMapping("/salary-range")
    public ResponseEntity<Map<String, Object>> getEmployeesBySalaryRange(@RequestParam BigDecimal min,
                                                                        @RequestParam BigDecimal max) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Employee> employees = employeeService.findBySalaryRange(min, max);
            
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API thống kê nhân viên
     * GET /api/employees/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getEmployeeStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long totalEmployees = employeeService.count();
            BigDecimal totalSalary = employeeService.getTotalSalary();
            BigDecimal averageSalary = employeeService.getAverageSalary();
            List<Employee> highestPaidEmployees = employeeService.findEmployeesWithHighestSalary();
            
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalEmployees", totalEmployees);
            statistics.put("totalSalary", totalSalary);
            statistics.put("averageSalary", averageSalary);
            statistics.put("highestPaidEmployees", highestPaidEmployees);
            
            response.put("success", true);
            response.put("data", statistics);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
