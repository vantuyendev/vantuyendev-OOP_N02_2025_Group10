package com.shopmanagement.repository;

import com.shopmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Product entity
 * Chứa các phương thức truy vấn dữ liệu cho bảng product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Tìm sản phẩm theo tên (tìm kiếm không phân biệt chữ hoa/thường)
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByProductNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Tìm sản phẩm theo category
     */
    List<Product> findByCategory(String category);
    
    /**
     * Tìm sản phẩm theo brand
     */
    List<Product> findByBrand(String brand);
    
    /**
     * Tìm sản phẩm theo supplier
     */
    List<Product> findBySupplier(String supplier);
    
    /**
     * Tìm sản phẩm theo barcode
     */
    Optional<Product> findByBarcode(String barcode);
    
    /**
     * Tìm sản phẩm theo khoảng giá
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    /**
     * Tìm sản phẩm có quantity > 0 (còn hàng)
     */
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findProductsInStock();
    
    /**
     * Tìm sản phẩm hết hàng (quantity = 0)
     */
    @Query("SELECT p FROM Product p WHERE p.quantity = 0")
    List<Product> findOutOfStockProducts();
    
    /**
     * Tìm sản phẩm sắp hết hàng (quantity <= threshold)
     */
    @Query("SELECT p FROM Product p WHERE p.quantity <= :threshold AND p.quantity > 0")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);
    
    /**
     * Đếm số sản phẩm theo category
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    long countByCategory(@Param("category") String category);
    
    /**
     * Tính tổng giá trị kho hàng
     */
    @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
    BigDecimal getTotalInventoryValue();
    
    /**
     * Tìm sản phẩm theo nhiều tiêu chí
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:brand IS NULL OR p.brand = :brand) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> findProductsByCriteria(@Param("name") String name,
                                       @Param("category") String category,
                                       @Param("brand") String brand,
                                       @Param("minPrice") BigDecimal minPrice,
                                       @Param("maxPrice") BigDecimal maxPrice);
    
    /**
     * Tìm tất cả category distinct
     */
    @Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
    List<String> findDistinctCategories();
    
    /**
     * Tìm tất cả brand distinct
     */
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL ORDER BY p.brand")
    List<String> findDistinctBrands();
}
