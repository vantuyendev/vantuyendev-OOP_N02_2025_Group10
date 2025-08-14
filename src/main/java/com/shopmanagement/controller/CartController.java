package com.shopmanagement.controller;

import com.shopmanagement.entity.Cart;
import com.shopmanagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controller cho Cart (Giỏ hàng)
 * Xử lý các request liên quan đến giỏ hàng
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(@RequestParam Long productId, 
                                     @RequestParam Integer quantity,
                                     HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
            }
            
            Cart cart = cartService.addToCart(customerId, productId, quantity);
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã thêm sản phẩm vào giỏ hàng",
                "cartItemCount", cartService.getCartItemCount(customerId),
                "totalQuantity", cartService.getTotalQuantity(customerId)
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateCartItem(@RequestParam Long productId,
                                          @RequestParam Integer quantity,
                                          HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            Cart cart = cartService.updateCartItemQuantity(customerId, productId, quantity);
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã cập nhật số lượng",
                "subtotal", cart.getSubtotal(),
                "cartTotal", cartService.calculateCartTotal(customerId)
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<?> removeFromCart(@RequestParam Long productId,
                                          HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            cartService.removeFromCart(customerId, productId);
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã xóa sản phẩm khỏi giỏ hàng",
                "cartItemCount", cartService.getCartItemCount(customerId),
                "cartTotal", cartService.calculateCartTotal(customerId)
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Lấy danh sách sản phẩm trong giỏ hàng
     */
    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<?> getCartItems(HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            List<Cart> cartItems = cartService.getCartWithProductDetails(customerId);
            BigDecimal cartTotal = cartService.calculateCartTotal(customerId);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "cartItems", cartItems,
                "cartTotal", cartTotal,
                "itemCount", cartItems.size()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Lấy số lượng sản phẩm trong giỏ hàng (để hiển thị trên header)
     */
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<?> getCartCount(HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.ok().body(Map.of("count", 0));
            }
            
            Long itemCount = cartService.getCartItemCount(customerId);
            Integer totalQuantity = cartService.getTotalQuantity(customerId);
            
            return ResponseEntity.ok().body(Map.of(
                "itemCount", itemCount,
                "totalQuantity", totalQuantity
            ));
            
        } catch (Exception e) {
            return ResponseEntity.ok().body(Map.of("itemCount", 0, "totalQuantity", 0));
        }
    }
    
    /**
     * Xóa toàn bộ giỏ hàng
     */
    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<?> clearCart(HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            cartService.clearCart(customerId);
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "Đã xóa toàn bộ giỏ hàng"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Validate giỏ hàng trước khi checkout
     */
    @GetMapping("/validate")
    @ResponseBody
    public ResponseEntity<?> validateCart(HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
            }
            
            boolean isValid = cartService.validateCartForCheckout(customerId);
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "isValid", isValid
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "isValid", false,
                "message", e.getMessage()
            ));
        }
    }
}
