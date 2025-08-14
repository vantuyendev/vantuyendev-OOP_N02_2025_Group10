package com.shopmanagement.service;

import com.shopmanagement.entity.Wishlist;
import com.shopmanagement.entity.Product;
import com.shopmanagement.repository.WishlistRepository;
import com.shopmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class cho Wishlist entity
 * Chứa business logic cho việc quản lý danh sách yêu thích
 */
@Service
@Transactional
public class WishlistService {
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Thêm sản phẩm vào danh sách yêu thích
     */
    public Wishlist addToWishlist(String customerId, Long productId) {
        // Kiểm tra sản phẩm tồn tại
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId);
        }
        
        // Kiểm tra sản phẩm đã có trong wishlist chưa
        if (wishlistRepository.existsByCustomerIdAndProductId(customerId, productId)) {
            throw new RuntimeException("Sản phẩm đã có trong danh sách yêu thích");
        }
        
        Wishlist wishlist = new Wishlist(customerId, productId);
        return wishlistRepository.save(wishlist);
    }
    
    /**
     * Xóa sản phẩm khỏi danh sách yêu thích
     */
    public void removeFromWishlist(String customerId, Long productId) {
        Optional<Wishlist> wishlist = wishlistRepository.findByCustomerIdAndProductId(customerId, productId);
        if (!wishlist.isPresent()) {
            throw new RuntimeException("Sản phẩm không có trong danh sách yêu thích");
        }
        
        wishlistRepository.delete(wishlist.get());
    }
    
    /**
     * Toggle sản phẩm trong wishlist (thêm nếu chưa có, xóa nếu đã có)
     */
    public boolean toggleWishlist(String customerId, Long productId) {
        Optional<Wishlist> existingWishlist = wishlistRepository.findByCustomerIdAndProductId(customerId, productId);
        
        if (existingWishlist.isPresent()) {
            // Nếu đã có, xóa khỏi wishlist
            wishlistRepository.delete(existingWishlist.get());
            return false; // Đã xóa
        } else {
            // Nếu chưa có, thêm vào wishlist
            addToWishlist(customerId, productId);
            return true; // Đã thêm
        }
    }
    
    /**
     * Lấy danh sách yêu thích của khách hàng
     */
    @Transactional(readOnly = true)
    public List<Wishlist> getCustomerWishlist(String customerId) {
        return wishlistRepository.findByCustomerIdOrderByAddedAtDesc(customerId);
    }
    
    /**
     * Lấy danh sách yêu thích với thông tin sản phẩm đầy đủ
     */
    @Transactional(readOnly = true)
    public List<Wishlist> getWishlistWithProductDetails(String customerId) {
        return wishlistRepository.findWishlistWithProductDetails(customerId);
    }
    
    /**
     * Kiểm tra sản phẩm có trong danh sách yêu thích không
     */
    @Transactional(readOnly = true)
    public boolean isInWishlist(String customerId, Long productId) {
        return wishlistRepository.existsByCustomerIdAndProductId(customerId, productId);
    }
    
    /**
     * Đếm số sản phẩm trong danh sách yêu thích
     */
    @Transactional(readOnly = true)
    public Long getWishlistCount(String customerId) {
        return wishlistRepository.countByCustomerId(customerId);
    }
    
    /**
     * Lấy sản phẩm được yêu thích nhiều nhất
     */
    @Transactional(readOnly = true)
    public List<Object[]> getMostWishedProducts() {
        return wishlistRepository.findMostWishedProducts();
    }
    
    /**
     * Đếm số người dùng đã thêm sản phẩm vào wishlist
     */
    @Transactional(readOnly = true)
    public Long getProductWishlistCount(Long productId) {
        return wishlistRepository.countByProductId(productId);
    }
    
    /**
     * Xóa tất cả wishlist của khách hàng
     */
    public void clearCustomerWishlist(String customerId) {
        List<Wishlist> customerWishlist = wishlistRepository.findByCustomerIdOrderByAddedAtDesc(customerId);
        wishlistRepository.deleteAll(customerWishlist);
    }
    
    /**
     * Chuyển sản phẩm từ wishlist sang giỏ hàng
     */
    public void moveToCart(String customerId, Long productId, Integer quantity) {
        // Logic này sẽ tích hợp với CartService
        // Xóa khỏi wishlist sau khi thêm vào cart thành công
        removeFromWishlist(customerId, productId);
    }
}
