package com.shopmanagement.repository;

import com.shopmanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Category entity
 * Cung cấp các phương thức truy cập dữ liệu danh mục sản phẩm
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Tìm danh mục theo tên
     */
    Optional<Category> findByCategoryName(String categoryName);
    
    /**
     * Tìm danh mục theo tên (không phân biệt hoa thường)
     */
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
    
    /**
     * Tìm danh mục đang hoạt động
     */
    List<Category> findByIsActiveTrue();
    
    /**
     * Tìm danh mục theo tên có chứa keyword
     */
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
    
    /**
     * Tìm danh mục đang hoạt động theo thứ tự hiển thị
     */
    @Query("SELECT c FROM Category c WHERE c.isActive = true ORDER BY c.displayOrder ASC, c.categoryName ASC")
    List<Category> findActiveCategoriesOrderByDisplayOrder();
    
    /**
     * Đếm số lượng sản phẩm trong danh mục
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.categoryId = :categoryId")
    Long countProductsInCategory(@Param("categoryId") Long categoryId);
    
    /**
     * Kiểm tra danh mục có tồn tại và đang hoạt động
     */
    boolean existsByCategoryIdAndIsActiveTrue(Long categoryId);
    
    /**
     * Tìm danh mục có sản phẩm
     */
    @Query("SELECT DISTINCT c FROM Category c INNER JOIN Product p ON c.categoryId = p.categoryId WHERE c.isActive = true")
    List<Category> findCategoriesWithProducts();
}
