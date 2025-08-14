package com.shopmanagement.repository;

import com.shopmanagement.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho ProductReview entity
 * Cung cấp các phương thức truy cập dữ liệu đánh giá sản phẩm
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    
    /**
     * Tìm đánh giá theo sản phẩm
     */
    List<ProductReview> findByProductIdAndIsApprovedTrueOrderByReviewDateDesc(Long productId);
    
    /**
     * Tìm đánh giá của khách hàng cho sản phẩm
     */
    Optional<ProductReview> findByProductIdAndCustomerId(Long productId, String customerId);
    
    /**
     * Tìm đánh giá của khách hàng
     */
    List<ProductReview> findByCustomerIdOrderByReviewDateDesc(String customerId);
    
    /**
     * Kiểm tra khách hàng đã đánh giá sản phẩm chưa
     */
    boolean existsByProductIdAndCustomerId(Long productId, String customerId);
    
    /**
     * Đếm số đánh giá của sản phẩm
     */
    Long countByProductIdAndIsApprovedTrue(Long productId);
    
    /**
     * Tính điểm đánh giá trung bình của sản phẩm
     */
    @Query("SELECT AVG(r.rating) FROM ProductReview r WHERE r.productId = :productId AND r.isApproved = true")
    Double findAverageRatingByProductId(@Param("productId") Long productId);
    
    /**
     * Tìm đánh giá theo rating
     */
    List<ProductReview> findByProductIdAndRatingAndIsApprovedTrueOrderByReviewDateDesc(Long productId, Integer rating);
    
    /**
     * Đếm đánh giá theo rating
     */
    @Query("SELECT r.rating, COUNT(r) FROM ProductReview r WHERE r.productId = :productId AND r.isApproved = true GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> countReviewsByRating(@Param("productId") Long productId);
    
    /**
     * Tìm đánh giá chờ phê duyệt
     */
    List<ProductReview> findByIsApprovedFalseOrderByReviewDateDesc();
    
    /**
     * Tìm đánh giá từ giao dịch đã xác thực
     */
    List<ProductReview> findByProductIdAndIsVerifiedPurchaseTrueAndIsApprovedTrueOrderByReviewDateDesc(Long productId);
    
    /**
     * Tìm đánh giá hữu ích nhất
     */
    List<ProductReview> findByProductIdAndIsApprovedTrueOrderByHelpfulCountDescReviewDateDesc(Long productId);
}
