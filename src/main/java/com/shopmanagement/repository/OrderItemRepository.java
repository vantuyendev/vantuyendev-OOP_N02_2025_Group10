package com.shopmanagement.repository;

import com.shopmanagement.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho OrderItem entity
 * Quản lý truy vấn dữ liệu chi tiết đơn hàng
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Tìm tất cả item trong một đơn hàng
     */
    List<OrderItem> findByOrderId(Long orderId);
    
    /**
     * Tìm tất cả đơn hàng chứa sản phẩm cụ thể
     */
    List<OrderItem> findByProductId(Long productId);
    
    /**
     * Đếm số lượng đơn hàng chứa sản phẩm
     */
    Long countByProductId(Long productId);
    
    /**
     * Tính tổng số lượng sản phẩm đã bán
     */
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.productId = :productId")
    Integer sumQuantityByProductId(@Param("productId") Long productId);
    
    /**
     * Tính tổng doanh thu từ sản phẩm
     */
    @Query("SELECT SUM(oi.subtotal) FROM OrderItem oi WHERE oi.productId = :productId")
    Double sumRevenueByProductId(@Param("productId") Long productId);
    
    /**
     * Tìm sản phẩm bán chạy nhất
     */
    @Query("SELECT oi.productId, SUM(oi.quantity) as totalSold FROM OrderItem oi GROUP BY oi.productId ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
    
    /**
     * Tìm sản phẩm theo đơn hàng và product ID
     */
    OrderItem findByOrderIdAndProductId(Long orderId, Long productId);
    
    /**
     * Xóa tất cả items của một đơn hàng
     */
    void deleteByOrderId(Long orderId);
    
    /**
     * Tính tổng giá trị của một đơn hàng
     */
    @Query("SELECT SUM(oi.subtotal) FROM OrderItem oi WHERE oi.orderId = :orderId")
    Double calculateOrderTotal(@Param("orderId") Long orderId);
    
    /**
     * Tìm top sản phẩm bán chạy với limit
     */
    @Query(value = "SELECT oi.product_id, SUM(oi.quantity) as total_sold, p.product_name " +
           "FROM order_item oi " +
           "JOIN product p ON oi.product_id = p.product_id " +
           "GROUP BY oi.product_id, p.product_name " +
           "ORDER BY total_sold DESC " +
           "LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopSellingProducts(@Param("limit") int limit);
}
