package com.shopmanagement.repository;

import com.shopmanagement.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Wishlist entity
 * Cung cấp các phương thức truy cập dữ liệu danh sách yêu thích
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    /**
     * Tìm danh sách yêu thích của khách hàng
     */
    List<Wishlist> findByCustomerIdOrderByAddedAtDesc(String customerId);
    
    /**
     * Tìm sản phẩm trong danh sách yêu thích của khách hàng
     */
    Optional<Wishlist> findByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Kiểm tra sản phẩm có trong danh sách yêu thích không
     */
    boolean existsByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Đếm số sản phẩm trong danh sách yêu thích của khách hàng
     */
    Long countByCustomerId(String customerId);
    
    /**
     * Xóa sản phẩm khỏi danh sách yêu thích
     */
    void deleteByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Tìm danh sách yêu thích với thông tin sản phẩm
     */
    @Query("SELECT w FROM Wishlist w " +
           "JOIN FETCH w.product p " +
           "LEFT JOIN FETCH p.category c " +
           "WHERE w.customerId = :customerId " +
           "ORDER BY w.addedAt DESC")
    List<Wishlist> findWishlistWithProductDetails(@Param("customerId") String customerId);
    
    /**
     * Tìm sản phẩm được yêu thích nhiều nhất
     */
    @Query("SELECT w.productId, COUNT(w) as wishCount FROM Wishlist w " +
           "GROUP BY w.productId " +
           "ORDER BY wishCount DESC")
    List<Object[]> findMostWishedProducts();
    
    /**
     * Đếm số lượng người dùng đã thêm sản phẩm vào wishlist
     */
    Long countByProductId(Long productId);
}
