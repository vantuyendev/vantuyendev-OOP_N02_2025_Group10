package com.shopmanagement.service;

import com.shopmanagement.entity.Cart;
import com.shopmanagement.entity.Product;
import com.shopmanagement.repository.CartRepository;
import com.shopmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service cho Cart business logic
 * Quản lý logic nghiệp vụ giỏ hàng
 */
@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    public Cart addToCart(String customerId, Long productId, Integer quantity) {
        // Kiểm tra sản phẩm có tồn tại không
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        
        Product product = productOpt.get();
        
        // Kiểm tra số lượng tồn kho
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Số lượng sản phẩm không đủ trong kho");
        }
        
        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        Optional<Cart> existingCartItem = cartRepository.findByCustomerIdAndProductId(customerId, productId);
        
        if (existingCartItem.isPresent()) {
            // Cập nhật số lượng
            Cart cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            
            if (product.getQuantity() < newQuantity) {
                throw new RuntimeException("Tổng số lượng vượt quá số lượng tồn kho");
            }
            
            cartItem.setQuantity(newQuantity);
            return cartRepository.save(cartItem);
        } else {
            // Tạo item mới
            Cart cartItem = new Cart(customerId, productId, quantity, product.getPrice());
            return cartRepository.save(cartItem);
        }
    }
    
    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    public Cart updateCartItemQuantity(String customerId, Long productId, Integer quantity) {
        Optional<Cart> cartItemOpt = cartRepository.findByCustomerIdAndProductId(customerId, productId);
        
        if (!cartItemOpt.isPresent()) {
            throw new RuntimeException("Sản phẩm không có trong giỏ hàng");
        }
        
        Cart cartItem = cartItemOpt.get();
        
        // Kiểm tra tồn kho
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent() && productOpt.get().getQuantity() < quantity) {
            throw new RuntimeException("Số lượng sản phẩm không đủ trong kho");
        }
        
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }
    
    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    public void removeFromCart(String customerId, Long productId) {
        cartRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }
    
    /**
     * Lấy tất cả sản phẩm trong giỏ hàng của khách hàng
     */
    public List<Cart> getCartItems(String customerId) {
        return cartRepository.findByCustomerIdOrderByAddedAtDesc(customerId);
    }
    
    /**
     * Tính tổng giá trị giỏ hàng
     */
    public BigDecimal calculateCartTotal(String customerId) {
        List<Cart> cartItems = getCartItems(customerId);
        return cartItems.stream()
                .map(Cart::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Đếm số sản phẩm trong giỏ hàng
     */
    public Long getCartItemCount(String customerId) {
        return cartRepository.countByCustomerId(customerId);
    }
    
    /**
     * Tính tổng số lượng sản phẩm trong giỏ hàng
     */
    public Integer getTotalQuantity(String customerId) {
        Integer total = cartRepository.sumQuantityByCustomerId(customerId);
        return total != null ? total : 0;
    }
    
    /**
     * Xóa toàn bộ giỏ hàng
     */
    public void clearCart(String customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }
    
    /**
     * Kiểm tra sản phẩm có trong giỏ hàng không
     */
    public boolean isProductInCart(String customerId, Long productId) {
        return cartRepository.existsByCustomerIdAndProductId(customerId, productId);
    }
    
    /**
     * Lấy thông tin chi tiết giỏ hàng với thông tin sản phẩm
     */
    public List<Cart> getCartWithProductDetails(String customerId) {
        List<Cart> cartItems = getCartItems(customerId);
        
        // Load product details for each cart item
        cartItems.forEach(cartItem -> {
            if (cartItem.getProduct() == null) {
                Optional<Product> product = productRepository.findById(cartItem.getProductId());
                product.ifPresent(cartItem::setProduct);
            }
        });
        
        return cartItems;
    }
    
    /**
     * Validate giỏ hàng trước khi checkout
     */
    public boolean validateCartForCheckout(String customerId) {
        List<Cart> cartItems = getCartItems(customerId);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }
        
        // Kiểm tra tồn kho cho từng sản phẩm
        for (Cart cartItem : cartItems) {
            Optional<Product> productOpt = productRepository.findById(cartItem.getProductId());
            if (!productOpt.isPresent()) {
                throw new RuntimeException("Sản phẩm " + cartItem.getProductId() + " không tồn tại");
            }
            
            Product product = productOpt.get();
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getProductName() + " không đủ số lượng trong kho");
            }
        }
        
        return true;
    }
}
