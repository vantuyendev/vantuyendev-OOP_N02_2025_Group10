package com.shopmanagement.repository;

import com.shopmanagement.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho Cart entity
 * Quản lý truy vấn dữ liệu giỏ hàng
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    /**
     * Tìm tất cả sản phẩm trong giỏ hàng của khách hàng
     */
    List<Cart> findByCustomerIdOrderByAddedAtDesc(String customerId);
    
    /**
     * Tìm sản phẩm cụ thể trong giỏ hàng của khách hàng
     */
    Optional<Cart> findByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Đếm số sản phẩm trong giỏ hàng
     */
    Long countByCustomerId(String customerId);
    
    /**
     * Tính tổng số lượng sản phẩm trong giỏ hàng
     */
    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.customerId = :customerId")
    Integer sumQuantityByCustomerId(@Param("customerId") String customerId);
    
    /**
     * Tính tổng giá trị giỏ hàng
     */
    @Query("SELECT SUM(c.price * c.quantity) FROM Cart c WHERE c.customerId = :customerId")
    Double calculateTotalValueByCustomerId(@Param("customerId") String customerId);
    
    /**
     * Xóa tất cả sản phẩm trong giỏ hàng của khách hàng
     */
    @Modifying
    @Transactional
    void deleteByCustomerId(String customerId);
    
    /**
     * Xóa sản phẩm cụ thể khỏi giỏ hàng
     */
    @Modifying
    @Transactional
    void deleteByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Kiểm tra sản phẩm có trong giỏ hàng không
     */
    boolean existsByCustomerIdAndProductId(String customerId, Long productId);
    
    /**
     * Tìm giỏ hàng theo danh sách product IDs
     */
    @Query("SELECT c FROM Cart c WHERE c.customerId = :customerId AND c.productId IN :productIds")
    List<Cart> findByCustomerIdAndProductIdIn(@Param("customerId") String customerId, @Param("productIds") List<Long> productIds);
    
    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = :quantity, c.updatedAt = CURRENT_TIMESTAMP WHERE c.customerId = :customerId AND c.productId = :productId")
    int updateQuantity(@Param("customerId") String customerId, @Param("productId") Long productId, @Param("quantity") Integer quantity);
}
