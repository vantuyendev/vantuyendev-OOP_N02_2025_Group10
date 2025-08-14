package com.shopmanagement.repository;

import com.shopmanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho Order entity
 * Quản lý truy vấn dữ liệu đơn hàng
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Tìm tất cả đơn hàng của một khách hàng
     */
    List<Order> findByCustomerIdOrderByOrderDateDesc(String customerId);
    
    /**
     * Tìm đơn hàng theo trạng thái
     */
    List<Order> findByStatusOrderByOrderDateDesc(Order.OrderStatus status);
    
    /**
     * Tìm đơn hàng theo khoảng thời gian
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    /**
     * Tìm đơn hàng của khách hàng theo trạng thái
     */
    List<Order> findByCustomerIdAndStatusOrderByOrderDateDesc(String customerId, Order.OrderStatus status);
    
    /**
     * Đếm số đơn hàng của khách hàng
     */
    Long countByCustomerId(String customerId);
    
    /**
     * Đếm số đơn hàng theo trạng thái
     */
    Long countByStatus(Order.OrderStatus status);
    
    /**
     * Tìm đơn hàng gần nhất của khách hàng
     */
    Optional<Order> findTopByCustomerIdOrderByOrderDateDesc(String customerId);
    
    /**
     * Tìm tất cả đơn hàng trong ngày hôm nay
     */
    @Query("SELECT o FROM Order o WHERE DATE(o.orderDate) = CURRENT_DATE ORDER BY o.orderDate DESC")
    List<Order> findTodayOrders();
    
    /**
     * Tính tổng doanh thu theo khoảng thời gian
     */
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.status = :status")
    Double calculateRevenueByPeriodAndStatus(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate, 
                                           @Param("status") Order.OrderStatus status);
    
    /**
     * Tìm đơn hàng cần xử lý (PENDING, CONFIRMED)
     */
    @Query("SELECT o FROM Order o WHERE o.status IN ('PENDING', 'CONFIRMED') ORDER BY o.orderDate ASC")
    List<Order> findPendingOrders();
    
    /**
     * Tìm đơn hàng theo phương thức thanh toán
     */
    List<Order> findByPaymentMethodOrderByOrderDateDesc(String paymentMethod);
}
