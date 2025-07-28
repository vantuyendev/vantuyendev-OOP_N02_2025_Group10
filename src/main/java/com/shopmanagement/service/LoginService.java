package com.shopmanagement.service;

import com.shopmanagement.entity.Login;
import com.shopmanagement.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class cho Login entity
 * Chứa business logic cho việc xử lý đăng nhập và quản lý tài khoản
 */
@Service
@Transactional
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepository;
    
    /**
     * Xác thực đăng nhập
     * @param userId ID người dùng
     * @param password Mật khẩu
     * @return Login object nếu thành công, null nếu thất bại
     */
    public Optional<Login> authenticate(String userId, String password) {
        if (userId == null || password == null || userId.trim().isEmpty() || password.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return loginRepository.findByUserIdAndPassword(userId.trim(), password.trim());
    }
    
    /**
     * Tạo tài khoản đăng nhập mới
     * @param login thông tin đăng nhập
     * @return Login object đã được lưu
     */
    public Login createLogin(Login login) {
        if (loginRepository.existsByUserId(login.getUserId())) {
            throw new IllegalArgumentException("User ID đã tồn tại: " + login.getUserId());
        }
        return loginRepository.save(login);
    }
    
    /**
     * Cập nhật thông tin đăng nhập
     * @param login thông tin đăng nhập cần cập nhật
     * @return Login object đã được cập nhật
     */
    public Login updateLogin(Login login) {
        if (!loginRepository.existsByUserId(login.getUserId())) {
            throw new IllegalArgumentException("User ID không tồn tại: " + login.getUserId());
        }
        return loginRepository.save(login);
    }
    
    /**
     * Đổi mật khẩu
     * @param userId ID người dùng
     * @param oldPassword mật khẩu cũ
     * @param newPassword mật khẩu mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        // Kiểm tra mật khẩu cũ
        Optional<Login> login = authenticate(userId, oldPassword);
        if (login.isPresent()) {
            Login loginEntity = login.get();
            loginEntity.setPassword(newPassword);
            loginRepository.save(loginEntity);
            return true;
        }
        return false;
    }
    
    /**
     * Xóa tài khoản đăng nhập
     * @param userId ID người dùng
     */
    public void deleteLogin(String userId) {
        loginRepository.deleteById(userId);
    }
    
    /**
     * Tìm tài khoản theo userId
     * @param userId ID người dùng
     * @return Login object nếu tìm thấy
     */
    public Optional<Login> findByUserId(String userId) {
        return loginRepository.findByUserId(userId);
    }
    
    /**
     * Lấy tất cả tài khoản
     * @return danh sách tất cả tài khoản
     */
    public List<Login> findAll() {
        return loginRepository.findAll();
    }
    
    /**
     * Lấy tài khoản theo status
     * @param status trạng thái (0: Employee, 1: Customer)
     * @return danh sách tài khoản theo status
     */
    public List<Login> findByStatus(Integer status) {
        return loginRepository.findByStatus(status);
    }
    
    /**
     * Đếm số lượng tài khoản theo status
     * @param status trạng thái
     * @return số lượng tài khoản
     */
    public long countByStatus(Integer status) {
        return loginRepository.countByStatus(status);
    }
    
    /**
     * Kiểm tra userId đã tồn tại hay chưa
     * @param userId ID người dùng
     * @return true nếu đã tồn tại, false nếu chưa
     */
    public boolean existsByUserId(String userId) {
        return loginRepository.existsByUserId(userId);
    }
}
