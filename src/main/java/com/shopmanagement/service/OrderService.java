package com.shopmanagement.service;

import com.shopmanagement.entity.*;
import com.shopmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service cho Order business logic
 * Quản lý logic nghiệp vụ đơn hàng
 */
@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    /**
     * Tạo đơn hàng từ giỏ hàng
     */
    public Order createOrderFromCart(String customerId, String shippingAddress, String paymentMethod, String notes) {
        // Kiểm tra khách hàng tồn tại
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Khách hàng không tồn tại");
        }
        
        // Lấy giỏ hàng
        List<Cart> cartItems = cartRepository.findByCustomerIdOrderByAddedAtDesc(customerId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }
        
        // Tính tổng tiền và validate tồn kho
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cartItem : cartItems) {
            Optional<Product> productOpt = productRepository.findById(cartItem.getProductId());
            if (!productOpt.isPresent()) {
                throw new RuntimeException("Sản phẩm không tồn tại: " + cartItem.getProductId());
            }
            
            Product product = productOpt.get();
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getProductName() + " không đủ số lượng trong kho");
            }
            
            totalAmount = totalAmount.add(cartItem.getSubtotal());
        }
        
        // Tạo đơn hàng
        Order order = new Order(customerId, totalAmount, shippingAddress, paymentMethod);
        order.setNotes(notes);
        order = orderRepository.save(order);
        
        // Tạo order items và cập nhật tồn kho
        for (Cart cartItem : cartItems) {
            // Tạo order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItemRepository.save(orderItem);
            
            // Cập nhật tồn kho
            Product product = productRepository.findById(cartItem.getProductId()).get();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }
        
        // Xóa giỏ hàng
        cartRepository.deleteByCustomerId(customerId);
        
        return order;
    }
    
    /**
     * Lấy tất cả đơn hàng của khách hàng
     */
    public List<Order> getCustomerOrders(String customerId) {
        return orderRepository.findByCustomerIdOrderByOrderDateDesc(customerId);
    }
    
    /**
     * Lấy chi tiết đơn hàng
     */
    public Order getOrderDetails(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }
        
        Order order = orderOpt.get();
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        order.setOrderItems(orderItems);
        
        return order;
    }
    
    /**
     * Lấy đơn hàng theo ID
     */
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    
    /**
     * Lấy đơn hàng theo ID (alias cho compatibility)
     */
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    
    /**
     * Lấy danh sách OrderItem của đơn hàng
     */
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
    
    /**
     * Cập nhật trạng thái đơn hàng
     */
    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }
        
        Order order = orderOpt.get();
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
    
    /**
     * Hủy đơn hàng
     */
    public Order cancelOrder(Long orderId, String customerId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }
        
        Order order = orderOpt.get();
        
        // Kiểm tra quyền hủy đơn
        if (!order.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Bạn không có quyền hủy đơn hàng này");
        }
        
        if (!order.canBeCancelled()) {
            throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái hiện tại");
        }
        
        // Hoàn trả số lượng sản phẩm về kho
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        for (OrderItem item : orderItems) {
            Optional<Product> productOpt = productRepository.findById(item.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        
        // Cập nhật trạng thái
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
    
    /**
     * Lấy tất cả đơn hàng (cho admin)
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    /**
     * Lấy đơn hàng theo trạng thái
     */
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatusOrderByOrderDateDesc(status);
    }
    
    /**
     * Lấy đơn hàng cần xử lý
     */
    public List<Order> getPendingOrders() {
        return orderRepository.findPendingOrders();
    }
    
    /**
     * Tính doanh thu theo khoảng thời gian
     */
    public Double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        Double revenue = orderRepository.calculateRevenueByPeriodAndStatus(
            startDate, endDate, Order.OrderStatus.DELIVERED);
        return revenue != null ? revenue : 0.0;
    }
    
    /**
     * Tính doanh thu theo khoảng thời gian và trạng thái
     */
    public Double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate, Order.OrderStatus status) {
        Double revenue = orderRepository.calculateRevenueByPeriodAndStatus(startDate, endDate, status);
        return revenue != null ? revenue : 0.0;
    }
    
    /**
     * Thống kê đơn hàng theo trạng thái
     */
    public Long countOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.countByStatus(status);
    }
    
    /**
     * Lấy đơn hàng trong ngày
     */
    public List<Order> getTodayOrders() {
        return orderRepository.findTodayOrders();
    }
    
    /**
     * Xác nhận đơn hàng
     */
    public Order confirmOrder(Long orderId) {
        return updateOrderStatus(orderId, Order.OrderStatus.CONFIRMED);
    }
    
    /**
     * Đánh dấu đơn hàng đang xử lý
     */
    public Order processOrder(Long orderId) {
        return updateOrderStatus(orderId, Order.OrderStatus.PROCESSING);
    }
    
    /**
     * Đánh dấu đơn hàng đã giao vận
     */
    public Order shipOrder(Long orderId) {
        return updateOrderStatus(orderId, Order.OrderStatus.SHIPPED);
    }
    
    /**
     * Đánh dấu đơn hàng đã giao thành công
     */
    public Order deliverOrder(Long orderId) {
        return updateOrderStatus(orderId, Order.OrderStatus.DELIVERED);
    }
    
    /**
     * Đếm tổng số đơn hàng
     */
    public long count() {
        return orderRepository.count();
    }
}
