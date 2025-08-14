package com.shopmanagement.controller.customer;

import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Category;
import com.shopmanagement.entity.Wishlist;
import com.shopmanagement.entity.Cart;
import com.shopmanagement.entity.Order;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CategoryService;
import com.shopmanagement.service.WishlistService;
import com.shopmanagement.service.CartService;
import com.shopmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller cho Customer Shop Interface
 * Xử lý các request mua hàng từ khách hàng
 */
@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "*")
public class ShopController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private WishlistService wishlistService;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    // ==================== PRODUCT BROWSING ====================
    
    /**
     * Lấy tất cả sản phẩm (Customer view - chỉ sản phẩm còn hàng)
     */
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAvailableProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "999999") BigDecimal maxPrice,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            List<Product> products;
            
            if (categoryId != null) {
                products = productService.findByCategoryId(categoryId);
            } else if (search != null && !search.trim().isEmpty()) {
                products = productService.searchProducts(search.trim());
            } else {
                products = productService.findAvailableProducts(); // Chỉ lấy sản phẩm còn hàng
            }
            
            // Filter by price range
            products = products.stream()
                .filter(p -> p.getPrice().compareTo(minPrice) >= 0 && p.getPrice().compareTo(maxPrice) <= 0)
                .collect(java.util.stream.Collectors.toList());
            
            // Sort products
            if ("price".equals(sortBy)) {
                if ("desc".equals(sortDirection)) {
                    products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
                } else {
                    products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                }
            } else if ("name".equals(sortBy)) {
                if ("desc".equals(sortDirection)) {
                    products.sort((p1, p2) -> p2.getProductName().compareToIgnoreCase(p1.getProductName()));
                } else {
                    products.sort((p1, p2) -> p1.getProductName().compareToIgnoreCase(p2.getProductName()));
                }
            }
            
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
     * Lấy chi tiết sản phẩm
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<Map<String, Object>> getProductDetail(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var productOpt = productService.findById(productId);
            if (!productOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy sản phẩm");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Product product = productOpt.get();
            
            // Kiểm tra sản phẩm còn hàng không
            if (product.getQuantity() <= 0) {
                response.put("success", false);
                response.put("message", "Sản phẩm đã hết hàng");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            response.put("success", true);
            response.put("data", product);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Lấy danh mục sản phẩm
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getActiveCategories() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Category> categories = categoryService.findCategoriesWithProducts();
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
    
    // ==================== WISHLIST MANAGEMENT ====================
    
    /**
     * Thêm/xóa sản phẩm khỏi wishlist
     */
    @PostMapping("/wishlist/toggle")
    public ResponseEntity<Map<String, Object>> toggleWishlist(@RequestParam Long productId, 
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để sử dụng tính năng này");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean added = wishlistService.toggleWishlist(customerId, productId);
            
            response.put("success", true);
            response.put("added", added);
            response.put("message", added ? "Đã thêm vào danh sách yêu thích" : "Đã xóa khỏi danh sách yêu thích");
            response.put("wishlistCount", wishlistService.getWishlistCount(customerId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Lấy danh sách yêu thích của khách hàng
     */
    @GetMapping("/wishlist")
    public ResponseEntity<Map<String, Object>> getWishlist(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để xem danh sách yêu thích");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            List<Wishlist> wishlist = wishlistService.getWishlistWithProductDetails(customerId);
            
            response.put("success", true);
            response.put("data", wishlist);
            response.put("count", wishlist.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // ==================== CART MANAGEMENT ====================
    
    /**
     * Lấy giỏ hàng của khách hàng
     */
    @GetMapping("/cart")
    public ResponseEntity<Map<String, Object>> getCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để xem giỏ hàng");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            List<Cart> cartItems = cartService.getCartWithProductDetails(customerId);
            BigDecimal total = cartService.calculateCartTotal(customerId);
            
            response.put("success", true);
            response.put("data", cartItems);
            response.put("total", total);
            response.put("count", cartItems.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // ==================== ORDER HISTORY ====================
    
    /**
     * Lấy lịch sử đơn hàng của khách hàng
     */
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getCustomerOrders(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để xem đơn hàng");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            List<Order> orders = orderService.getCustomerOrders(customerId);
            
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
     * Lấy chi tiết đơn hàng
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetail(@PathVariable Long orderId, 
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            var orderOpt = orderService.findById(orderId);
            if (!orderOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy đơn hàng");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Order order = orderOpt.get();
            
            // Kiểm tra đơn hàng có thuộc về khách hàng này không
            if (!order.getCustomerId().equals(customerId)) {
                response.put("success", false);
                response.put("message", "Bạn không có quyền xem đơn hàng này");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            response.put("success", true);
            response.put("data", order);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
