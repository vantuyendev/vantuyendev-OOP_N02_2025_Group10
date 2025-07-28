package com.shopmanagement.controller;

import com.shopmanagement.entity.Login;
import com.shopmanagement.service.LoginService;
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
 * REST Controller cho Login API
 * Xử lý các request liên quan đến đăng nhập và xác thực
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    /**
     * API đăng nhập
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String userId = loginRequest.get("userId");
            String password = loginRequest.get("password");
            
            if (userId == null || password == null || userId.trim().isEmpty() || password.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "User ID và Password không được để trống");
                return ResponseEntity.badRequest().body(response);
            }
            
            Optional<Login> loginOpt = loginService.authenticate(userId.trim(), password.trim());
            
            if (loginOpt.isPresent()) {
                Login login = loginOpt.get();
                response.put("success", true);
                response.put("message", "Đăng nhập thành công");
                response.put("userId", login.getUserId());
                response.put("status", login.getStatus());
                response.put("userType", login.getStatus() == 0 ? "Employee" : "Customer");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User ID hoặc Password không đúng");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API đổi mật khẩu
     * PUT /api/auth/change-password
     */
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String userId = request.get("userId");
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");
            
            if (userId == null || oldPassword == null || newPassword == null) {
                response.put("success", false);
                response.put("message", "Thiếu thông tin bắt buộc");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = loginService.changePassword(userId, oldPassword, newPassword);
            
            if (success) {
                response.put("success", true);
                response.put("message", "Đổi mật khẩu thành công");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Mật khẩu cũ không đúng");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tạo tài khoản đăng nhập mới
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody Login login) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Login savedLogin = loginService.createLogin(login);
            response.put("success", true);
            response.put("message", "Tạo tài khoản thành công");
            response.put("data", savedLogin);
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
     * API lấy thông tin tài khoản theo userId
     * GET /api/auth/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Login> loginOpt = loginService.findByUserId(userId);
            
            if (loginOpt.isPresent()) {
                response.put("success", true);
                response.put("data", loginOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy tài khoản với ID: " + userId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API lấy danh sách tài khoản theo status
     * GET /api/auth/users?status={status}
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsersByStatus(@RequestParam(required = false) Integer status) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Login> users;
            if (status != null) {
                users = loginService.findByStatus(status);
            } else {
                users = loginService.findAll();
            }
            
            response.put("success", true);
            response.put("data", users);
            response.put("count", users.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API kiểm tra userId có tồn tại hay không
     * GET /api/auth/check-userid/{userId}
     */
    @GetMapping("/check-userid/{userId}")
    public ResponseEntity<Map<String, Object>> checkUserIdExists(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean exists = loginService.existsByUserId(userId);
            response.put("success", true);
            response.put("exists", exists);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
