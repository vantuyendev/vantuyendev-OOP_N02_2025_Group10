package com.shopmanagement.controller.admin;

import com.shopmanagement.entity.Category;
import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Customer;
import com.shopmanagement.entity.Order;
import com.shopmanagement.service.CategoryService;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CustomerService;
import com.shopmanagement.service.OrderService;
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
 * REST Controller cho Admin Panel
 * Xử lý các request quản lý từ admin
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private OrderService orderService;
    
    // ==================== CATEGORY MANAGEMENT ====================
    
    /**
     * Lấy tất cả danh mục
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Category> categories = categoryService.findAll();
            response.put("success", true);
            response.put("data", categories);
            response.put("count", categories.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Tạo danh mục mới
     */
    @PostMapping("/categories")
    public ResponseEntity<Map<String, Object>> createCategory(@Valid @RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            Category createdCategory = categoryService.createCategory(category);
            response.put("success", true);
            response.put("message", "Tạo danh mục thành công");
            response.put("data", createdCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Không thể tạo danh mục: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Cập nhật danh mục
     */
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long categoryId, 
                                                            @Valid @RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            category.setCategoryId(categoryId);
            Category updatedCategory = categoryService.updateCategory(category);
            response.put("success", true);
            response.put("message", "Cập nhật danh mục thành công");
            response.put("data", updatedCategory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Không thể cập nhật danh mục: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Xóa danh mục
     */
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long categoryId) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoryService.deleteCategory(categoryId);
            response.put("success", true);
            response.put("message", "Xóa danh mục thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Không thể xóa danh mục: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // ==================== PRODUCT MANAGEMENT ====================
    
    /**
     * Lấy tất cả sản phẩm (Admin view)
     */
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Product> products = productService.findAll();
            response.put("success", true);
            response.put("data", products);
            response.put("count", products.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Thống kê tổng quan cho admin dashboard
     */
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Thống kê cơ bản
            stats.put("totalProducts", productService.count());
            stats.put("totalCustomers", customerService.count());
            stats.put("totalCategories", categoryService.count());
            stats.put("totalOrders", orderService.count());
            
            // Thống kê đơn hàng theo trạng thái
            stats.put("pendingOrders", orderService.countOrdersByStatus(Order.OrderStatus.PENDING));
            stats.put("confirmedOrders", orderService.countOrdersByStatus(Order.OrderStatus.CONFIRMED));
            stats.put("deliveredOrders", orderService.countOrdersByStatus(Order.OrderStatus.DELIVERED));
            
            // Sản phẩm sắp hết hàng (quantity < 10)
            stats.put("lowStockProducts", productService.findLowStockProducts(10).size());
            
            response.put("success", true);
            response.put("data", stats);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // ==================== CUSTOMER MANAGEMENT ====================
    
    /**
     * Lấy tất cả khách hàng (Admin view)
     */
    @GetMapping("/customers")
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Customer> customers = customerService.findAll();
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
    
    // ==================== ORDER MANAGEMENT ====================
    
    /**
     * Lấy tất cả đơn hàng (Admin view)
     */
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = orderService.getAllOrders();
            response.put("success", true);
            response.put("data", orders);
            response.put("count", orders.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Cập nhật trạng thái đơn hàng
     */
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long orderId, 
                                                               @RequestParam String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
            
            response.put("success", true);
            response.put("message", "Cập nhật trạng thái đơn hàng thành công");
            response.put("data", updatedOrder);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Trạng thái đơn hàng không hợp lệ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Không thể cập nhật trạng thái: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
