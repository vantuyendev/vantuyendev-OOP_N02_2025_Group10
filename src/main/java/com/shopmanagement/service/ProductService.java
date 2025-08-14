package com.shopmanagement.service;

import com.shopmanagement.entity.Product;
import com.shopmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class cho Product entity
 * Chứa business logic cho việc quản lý sản phẩm
 */
@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Tạo sản phẩm mới
     * @param product thông tin sản phẩm
     * @return Product object đã được lưu
     */
    public Product createProduct(Product product) {
        // Kiểm tra barcode trùng lặp nếu có
        if (product.getBarcode() != null && !product.getBarcode().isEmpty()) {
            Optional<Product> existingProduct = productRepository.findByBarcode(product.getBarcode());
            if (existingProduct.isPresent()) {
                throw new IllegalArgumentException("Barcode đã tồn tại: " + product.getBarcode());
            }
        }
        return productRepository.save(product);
    }
    
    /**
     * Cập nhật thông tin sản phẩm
     * @param product thông tin sản phẩm cần cập nhật
     * @return Product object đã được cập nhật
     */
    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getProductId())) {
            throw new IllegalArgumentException("Product không tồn tại với ID: " + product.getProductId());
        }
        
        // Kiểm tra barcode trùng lặp với sản phẩm khác
        if (product.getBarcode() != null && !product.getBarcode().isEmpty()) {
            Optional<Product> existingProduct = productRepository.findByBarcode(product.getBarcode());
            if (existingProduct.isPresent() && !existingProduct.get().getProductId().equals(product.getProductId())) {
                throw new IllegalArgumentException("Barcode đã được sử dụng bởi sản phẩm khác: " + product.getBarcode());
            }
        }
        
        return productRepository.save(product);
    }
    
    /**
     * Xóa sản phẩm
     * @param productId ID sản phẩm
     */
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product không tồn tại với ID: " + productId);
        }
        productRepository.deleteById(productId);
    }
    
    /**
     * Tìm sản phẩm theo ID
     * @param productId ID sản phẩm
     * @return Product object nếu tìm thấy
     */
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }
    
    /**
     * Lấy tất cả sản phẩm
     * @return danh sách tất cả sản phẩm
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
        /**
     * Tìm sản phẩm theo danh mục
     */
    @Transactional(readOnly = true)
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryIdAndQuantityGreaterThan(categoryId, 0);
    }
    
    /**
     * Tìm sản phẩm còn hàng
     */
    @Transactional(readOnly = true)
    public List<Product> findAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }
    
    /**
     * Tìm sản phẩm sắp hết hàng
     */
    @Transactional(readOnly = true)
    public List<Product> findLowStockProducts(Integer threshold) {
        return productRepository.findByQuantityLessThanEqual(threshold);
    }
    
    /**
     * Tìm kiếm sản phẩm theo tên hoặc mô tả
     */
    @Transactional(readOnly = true)
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
    
    /**
     * Tìm sản phẩm theo khoảng giá
     */
    @Transactional(readOnly = true)
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    public List<Product> findByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }
    
    /**
     * Tìm sản phẩm theo category
     * @param category loại sản phẩm
     * @return danh sách sản phẩm thuộc category đó
     */
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    /**
     * Tìm sản phẩm theo brand
     * @param brand thương hiệu
     * @return danh sách sản phẩm của thương hiệu đó
     */
    public List<Product> findByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
    
    /**
     * Tìm sản phẩm theo supplier
     * @param supplier nhà cung cấp
     * @return danh sách sản phẩm của nhà cung cấp đó
     */
    public List<Product> findBySupplier(String supplier) {
        return productRepository.findBySupplier(supplier);
    }
    
    /**
     * Tìm sản phẩm theo barcode
     * @param barcode mã vạch
     * @return Product object nếu tìm thấy
     */
    public Optional<Product> findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }
    
    /**
     * Lấy danh sách sản phẩm còn hàng
     * @return danh sách sản phẩm có quantity > 0
     */
    public List<Product> findProductsInStock() {
        return productRepository.findProductsInStock();
    }
    
    /**
     * Lấy danh sách sản phẩm hết hàng
     * @return danh sách sản phẩm có quantity = 0
     */
    public List<Product> findOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }
    
    /**
     * Tìm sản phẩm theo nhiều tiêu chí
     * @param name tên sản phẩm
     * @param category loại sản phẩm
     * @param brand thương hiệu
     * @param minPrice giá tối thiểu
     * @param maxPrice giá tối đa
     * @return danh sách sản phẩm phù hợp
     */
    public List<Product> findProductsByCriteria(String name, String category, String brand, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findProductsByCriteria(name, category, brand, minPrice, maxPrice);
    }
    
    /**
     * Lấy danh sách tất cả category
     * @return danh sách category distinct
     */
    public List<String> findDistinctCategories() {
        return productRepository.findDistinctCategories();
    }
    
    /**
     * Lấy danh sách tất cả brand
     * @return danh sách brand distinct
     */
    public List<String> findDistinctBrands() {
        return productRepository.findDistinctBrands();
    }
    
    /**
     * Đếm số sản phẩm theo category
     * @param category loại sản phẩm
     * @return số lượng sản phẩm
     */
    public long countByCategory(String category) {
        return productRepository.countByCategory(category);
    }
    
    /**
     * Tính tổng giá trị kho hàng
     * @return tổng giá trị (price * quantity)
     */
    public BigDecimal getTotalInventoryValue() {
        BigDecimal total = productRepository.getTotalInventoryValue();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Thêm số lượng vào kho
     * @param productId ID sản phẩm
     * @param quantity số lượng cần thêm
     * @return Product object đã được cập nhật
     */
    public Product addStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.addStock(quantity);
            return productRepository.save(product);
        }
        throw new IllegalArgumentException("Product không tồn tại với ID: " + productId);
    }
    
    /**
     * Xuất kho (trừ số lượng)
     * @param productId ID sản phẩm
     * @param quantity số lượng cần xuất
     * @return Product object đã được cập nhật
     */
    public Product removeStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.removeStock(quantity)) {
                return productRepository.save(product);
            } else {
                throw new IllegalArgumentException("Không đủ hàng trong kho. Số lượng hiện tại: " + product.getQuantity());
            }
        }
        throw new IllegalArgumentException("Product không tồn tại với ID: " + productId);
    }
    
    /**
     * Đếm tổng số sản phẩm
     * @return số lượng sản phẩm
     */
    public long count() {
        return productRepository.count();
    }
    
    /**
     * Kiểm tra sản phẩm có tồn tại hay không
     * @param productId ID sản phẩm
     * @return true nếu tồn tại, false nếu không
     */
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }
}
