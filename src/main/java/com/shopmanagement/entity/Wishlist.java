package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity cho bảng wishlist (danh sách yêu thích)
 * Quản lý sản phẩm mà khách hàng quan tâm
 */
@Entity
@Table(name = "wishlist", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"customerId", "productId"}))
public class Wishlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlistId")
    private Long wishlistId;
    
    @Column(name = "customerId", nullable = false)
    @NotNull(message = "Customer ID không được để trống")
    private String customerId;
    
    @Column(name = "productId", nullable = false)
    @NotNull(message = "Product ID không được để trống")
    private Long productId;
    
    @Column(name = "addedAt", nullable = false)
    private LocalDateTime addedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;
    
    // Constructors
    public Wishlist() {
        this.addedAt = LocalDateTime.now();
    }
    
    public Wishlist(String customerId, Long productId) {
        this();
        this.customerId = customerId;
        this.productId = productId;
    }
    
    // Getters and Setters
    public Long getWishlistId() {
        return wishlistId;
    }
    
    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }
    
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", customerId='" + customerId + '\'' +
                ", productId=" + productId +
                ", addedAt=" + addedAt +
                '}';
    }
}
