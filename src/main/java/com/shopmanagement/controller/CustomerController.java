package com.shopmanagement.controller;

import com.shopmanagement.entity.Customer;
import com.shopmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller cho Customer API
 * Xử lý các request liên quan đến quản lý khách hàng
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    /**
     * API lấy tất cả khách hàng
     * GET /api/customers
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Customer> customers = customerService.findAllWithLogin();
            response.put("success", true);
            response.put("data", customers);
            response.put("count", customers.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API lấy khách hàng theo ID
     * GET /api/customers/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Customer> customerOpt = customerService.findByIdWithLogin(userId);
            
            if (customerOpt.isPresent()) {
                response.put("success", true);
                response.put("data", customerOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy khách hàng với ID: " + userId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tạo khách hàng mới
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCustomer(@Valid @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Parse request data
            String userId = (String) request.get("userId");
            String customerName = (String) request.get("customerName");
            String phoneNumber = (String) request.get("phoneNumber");
            String address = (String) request.get("address");
            String email = (String) request.get("email");
            String password = (String) request.get("password");
            
            // Validate required fields
            if (userId == null || customerName == null || password == null) {
                response.put("success", false);
                response.put("message", "User ID, tên khách hàng và mật khẩu là bắt buộc");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Create customer object
            Customer customer = new Customer();
            customer.setUserId(userId);
            customer.setCustomerName(customerName);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setEmail(email);
            
            Customer savedCustomer = customerService.createCustomer(customer, password);
            
            response.put("success", true);
            response.put("message", "Tạo khách hàng thành công");
            response.put("data", savedCustomer);
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
     * API cập nhật thông tin khách hàng
     * PUT /api/customers/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateCustomer(@PathVariable String userId, 
                                                            @Valid @RequestBody Customer customer) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            customer.setUserId(userId); // Ensure userId matches path variable
            Customer updatedCustomer = customerService.updateCustomer(customer);
            
            response.put("success", true);
            response.put("message", "Cập nhật khách hàng thành công");
            response.put("data", updatedCustomer);
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
     * API xóa khách hàng
     * DELETE /api/customers/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!customerService.existsById(userId)) {
                response.put("success", false);
                response.put("message", "Không tìm thấy khách hàng với ID: " + userId);
                return ResponseEntity.notFound().build();
            }
            
            customerService.deleteCustomer(userId);
            
            response.put("success", true);
            response.put("message", "Xóa khách hàng thành công");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tìm kiếm khách hàng theo tên
     * GET /api/customers/search?name={name}
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchCustomers(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String phone,
                                                             @RequestParam(required = false) String email,
                                                             @RequestParam(required = false) String address) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Customer> customers;
            
            if (name != null && !name.trim().isEmpty()) {
                customers = customerService.findByName(name.trim());
            } else if (phone != null && !phone.trim().isEmpty()) {
                Optional<Customer> customerOpt = customerService.findByPhoneNumber(phone.trim());
                customers = customerOpt.map(List::of).orElse(List.of());
            } else if (email != null && !email.trim().isEmpty()) {
                Optional<Customer> customerOpt = customerService.findByEmail(email.trim());
                customers = customerOpt.map(List::of).orElse(List.of());
            } else if (address != null && !address.trim().isEmpty()) {
                customers = customerService.findByAddress(address.trim());
            } else {
                customers = customerService.findAll();
            }
            
            response.put("success", true);
            response.put("data", customers);
            response.put("count", customers.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API thống kê khách hàng
     * GET /api/customers/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getCustomerStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long totalCustomers = customerService.count();
            
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalCustomers", totalCustomers);
            
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
