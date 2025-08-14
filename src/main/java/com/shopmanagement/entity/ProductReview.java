package com.shopmanagement.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity cho bảng product_reviews (đánh giá sản phẩm)
 * Quản lý đánh giá và phản hồi của khách hàng về sản phẩm
 */
@Entity
@Table(name = "product_reviews")
public class ProductReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;
    
    @Column(name = "productId", nullable = false)
    @NotNull(message = "Product ID không được để trống")
    private Long productId;
    
    @Column(name = "customerId", nullable = false)
    @NotBlank(message = "Customer ID không được để trống")
    private String customerId;
    
    @Column(name = "rating", nullable = false)
    @NotNull(message = "Đánh giá không được để trống")
    @Min(value = 1, message = "Đánh giá tối thiểu là 1 sao")
    @Max(value = 5, message = "Đánh giá tối đa là 5 sao")
    private Integer rating;
    
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    
    @Column(name = "reviewDate", nullable = false)
    private LocalDateTime reviewDate;
    
    @Column(name = "isVerifiedPurchase", nullable = false)
    private Boolean isVerifiedPurchase = false;
    
    @Column(name = "isApproved", nullable = false)
    private Boolean isApproved = true;
    
    @Column(name = "helpfulCount")
    private Integer helpfulCount = 0;
    
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private Customer customer;
    
    // Constructors
    public ProductReview() {
        this.reviewDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public ProductReview(Long productId, String customerId, Integer rating, String comment) {
        this();
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }
    
    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public Boolean getIsVerifiedPurchase() {
        return isVerifiedPurchase;
    }
    
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
        this.isVerifiedPurchase = isVerifiedPurchase;
    }
    
    public Boolean getIsApproved() {
        return isApproved;
    }
    
    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
    
    public Integer getHelpfulCount() {
        return helpfulCount;
    }
    
    public void setHelpfulCount(Integer helpfulCount) {
        this.helpfulCount = helpfulCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "ProductReview{" +
                "reviewId=" + reviewId +
                ", productId=" + productId +
                ", customerId='" + customerId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
