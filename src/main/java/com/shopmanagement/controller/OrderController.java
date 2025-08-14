package com.shopmanagement.controller;

import com.shopmanagement.entity.Order;
import com.shopmanagement.entity.OrderItem;
import com.shopmanagement.service.OrderService;
import com.shopmanagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller cho Order (Đơn hàng)
 * Xử lý các request liên quan đến đơn hàng
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;
    
    /**
     * Hiển thị trang checkout
     */
    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        try {
            // Validate giỏ hàng trước khi checkout
            boolean isValid = cartService.validateCartForCheckout(customerId);
            if (!isValid) {
                model.addAttribute("error", "Giỏ hàng không hợp lệ hoặc trống");
                return "redirect:/shop/products";
            }
            
            // Lấy thông tin giỏ hàng
            model.addAttribute("cartItems", cartService.getCartWithProductDetails(customerId));
            model.addAttribute("cartTotal", cartService.calculateCartTotal(customerId));
            
            return "shop/checkout";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/shop/products";
        }
    }
    
    /**
     * Xử lý tạo đơn hàng
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createOrder(@RequestParam String shippingAddress,
                                       @RequestParam String paymentMethod,
                                       @RequestParam(required = false) String notes,
                                       HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Vui lòng đăng nhập"
                ));
            }
            
            Order order = orderService.createOrderFromCart(customerId, shippingAddress, paymentMethod, notes);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đặt hàng thành công",
                "orderId", order.getOrderId(),
                "orderTotal", order.getTotalAmount()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Hiển thị danh sách đơn hàng của khách hàng
     */
    @GetMapping("/my-orders")
    public String showMyOrders(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        try {
            List<Order> orders = orderService.getCustomerOrders(customerId);
            model.addAttribute("orders", orders);
            return "shop/my-orders";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "shop/my-orders";
        }
    }
    
    /**
     * Hiển thị chi tiết đơn hàng
     */
    @GetMapping("/details/{orderId}")
    public String showOrderDetails(@PathVariable Long orderId, HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        try {
            Optional<Order> orderOpt = orderService.getOrderById(orderId);
            if (!orderOpt.isPresent()) {
                model.addAttribute("error", "Đơn hàng không tồn tại");
                return "redirect:/order/my-orders";
            }
            
            Order order = orderOpt.get();
            
            // Kiểm tra quyền truy cập
            if (!order.getCustomerId().equals(customerId)) {
                model.addAttribute("error", "Bạn không có quyền xem đơn hàng này");
                return "redirect:/order/my-orders";
            }
            
            List<OrderItem> orderItems = orderService.getOrderItems(orderId);
            
            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            
            return "shop/order-details";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/order/my-orders";
        }
    }
    
    /**
     * Hủy đơn hàng
     */
    @PostMapping("/cancel/{orderId}")
    @ResponseBody
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Vui lòng đăng nhập"
                ));
            }
            
            Order order = orderService.cancelOrder(orderId, customerId);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã hủy đơn hàng thành công",
                "newStatus", order.getStatus().getDisplayName()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * API lấy thông tin đơn hàng
     */
    @GetMapping("/api/{orderId}")
    @ResponseBody
    public ResponseEntity<?> getOrderInfo(@PathVariable Long orderId, HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            Optional<Order> orderOpt = orderService.getOrderById(orderId);
            if (!orderOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Đơn hàng không tồn tại");
            }
            
            Order order = orderOpt.get();
            
            // Kiểm tra quyền truy cập
            if (!order.getCustomerId().equals(customerId)) {
                return ResponseEntity.badRequest().body("Bạn không có quyền xem đơn hàng này");
            }
            
            List<OrderItem> orderItems = orderService.getOrderItems(orderId);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "order", order,
                "orderItems", orderItems
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    // ============ ADMIN ENDPOINTS ============
    
    /**
     * Hiển thị quản lý đơn hàng cho admin
     */
    @GetMapping("/admin/manage")
    public String showOrderManagement(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/login";
        }
        
        try {
            List<Order> allOrders = orderService.getAllOrders();
            List<Order> pendingOrders = orderService.getPendingOrders();
            
            model.addAttribute("allOrders", allOrders);
            model.addAttribute("pendingOrders", pendingOrders);
            
            // Thống kê đơn hàng
            model.addAttribute("totalOrders", allOrders.size());
            model.addAttribute("pendingCount", orderService.countOrdersByStatus(Order.OrderStatus.PENDING));
            model.addAttribute("confirmedCount", orderService.countOrdersByStatus(Order.OrderStatus.CONFIRMED));
            model.addAttribute("deliveredCount", orderService.countOrdersByStatus(Order.OrderStatus.DELIVERED));
            
            return "shop/admin-order-management";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "shop/admin-order-management";
        }
    }
    
    /**
     * Cập nhật trạng thái đơn hàng (admin)
     */
    @PostMapping("/admin/update-status")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(@RequestParam Long orderId,
                                             @RequestParam String status,
                                             HttpSession session) {
        try {
            String role = (String) session.getAttribute("role");
            if (!"admin".equals(role)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Không có quyền truy cập"
                ));
            }
            
            Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status);
            Order order = orderService.updateOrderStatus(orderId, newStatus);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã cập nhật trạng thái đơn hàng",
                "newStatus", order.getStatus().getDisplayName()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Thống kê doanh thu (admin)
     */
    @GetMapping("/admin/revenue")
    @ResponseBody
    public ResponseEntity<?> getRevenue(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      HttpSession session) {
        try {
            String role = (String) session.getAttribute("role");
            if (!"admin".equals(role)) {
                return ResponseEntity.badRequest().body("Không có quyền truy cập");
            }
            
            LocalDateTime start = startDate != null ? 
                LocalDateTime.parse(startDate + "T00:00:00") : 
                LocalDateTime.now().minusMonths(1);
                
            LocalDateTime end = endDate != null ? 
                LocalDateTime.parse(endDate + "T23:59:59") : 
                LocalDateTime.now();
            
            Double revenue = orderService.calculateRevenue(start, end, Order.OrderStatus.DELIVERED);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "revenue", revenue,
                "startDate", start.format(DateTimeFormatter.ISO_LOCAL_DATE),
                "endDate", end.format(DateTimeFormatter.ISO_LOCAL_DATE)
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
}
