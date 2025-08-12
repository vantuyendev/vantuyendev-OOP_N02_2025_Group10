package com.shopmanagement.repository;

import com.shopmanagement.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface cho Login entity
 * Chứa các phương thức truy vấn dữ liệu cho bảng login
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    
    /**
     * Tìm login theo userId và password
     */
    Optional<Login> findByUserIdAndPassword(String userId, String password);
    
    /**
     * Kiểm tra userId đã tồn tại hay chưa
     */
    boolean existsByUserId(String userId);
    
    /**
     * Tìm login theo userId
     */
    Optional<Login> findByUserId(String userId);
    
    /**
     * Tìm tất cả login theo status
     */
    @Query("SELECT l FROM Login l WHERE l.status = :status")
    java.util.List<Login> findByStatus(@Param("status") Integer status);
    
    /**
     * Đếm số lượng người dùng theo status
     */
    @Query("SELECT COUNT(l) FROM Login l WHERE l.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * Cập nhật password cho userId
     */
    @Query("UPDATE Login l SET l.password = :newPassword WHERE l.userId = :userId AND l.password = :oldPassword")
    int updatePassword(@Param("userId") String userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}
