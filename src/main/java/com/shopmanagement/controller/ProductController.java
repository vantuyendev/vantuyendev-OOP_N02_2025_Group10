package com.shopmanagement.controller;

import com.shopmanagement.entity.Product;
import com.shopmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller cho Product API
 * Xử lý các request liên quan đến quản lý sản phẩm
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * API lấy tất cả sản phẩm
     * GET /api/products
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products = productService.findAll();
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
     * API lấy sản phẩm theo ID
     * GET /api/products/{productId}
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Product> productOpt = productService.findById(productId);
            
            if (productOpt.isPresent()) {
                response.put("success", true);
                response.put("data", productOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không tìm thấy sản phẩm với ID: " + productId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tạo sản phẩm mới
     * POST /api/products
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Product savedProduct = productService.createProduct(product);
            
            response.put("success", true);
            response.put("message", "Tạo sản phẩm thành công");
            response.put("data", savedProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API cập nhật thông tin sản phẩm
     * PUT /api/products/{productId}
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long productId, 
                                                           @Valid @RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            product.setProductId(productId); // Ensure productId matches path variable
            Product updatedProduct = productService.updateProduct(product);
            
            response.put("success", true);
            response.put("message", "Cập nhật sản phẩm thành công");
            response.put("data", updatedProduct);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API xóa sản phẩm
     * DELETE /api/products/{productId}
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!productService.existsById(productId)) {
                response.put("success", false);
                response.put("message", "Không tìm thấy sản phẩm với ID: " + productId);
                return ResponseEntity.notFound().build();
            }
            
            productService.deleteProduct(productId);
            
            response.put("success", true);
            response.put("message", "Xóa sản phẩm thành công");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API tìm kiếm sản phẩm
     * GET /api/products/search
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String category,
                                                            @RequestParam(required = false) String brand,
                                                            @RequestParam(required = false) String supplier,
                                                            @RequestParam(required = false) String barcode,
                                                            @RequestParam(required = false) BigDecimal minPrice,
                                                            @RequestParam(required = false) BigDecimal maxPrice) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products;
            
            if (barcode != null && !barcode.trim().isEmpty()) {
                Optional<Product> productOpt = productService.findByBarcode(barcode.trim());
                products = productOpt.map(List::of).orElse(List.of());
            } else if (name != null || category != null || brand != null || minPrice != null || maxPrice != null) {
                products = productService.findProductsByCriteria(
                    name != null ? name.trim() : null,
                    category != null ? category.trim() : null,
                    brand != null ? brand.trim() : null,
                    minPrice,
                    maxPrice
                );
            } else if (supplier != null && !supplier.trim().isEmpty()) {
                products = productService.findBySupplier(supplier.trim());
            } else {
                products = productService.findAll();
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
     * API lấy sản phẩm theo category
     * GET /api/products/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(@PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products = productService.findByCategory(category);
            
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
     * API lấy sản phẩm còn hàng
     * GET /api/products/in-stock
     */
    @GetMapping("/in-stock")
    public ResponseEntity<Map<String, Object>> getProductsInStock() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products = productService.findProductsInStock();
            
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
     * API lấy sản phẩm hết hàng
     * GET /api/products/out-of-stock
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<Map<String, Object>> getOutOfStockProducts() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products = productService.findOutOfStockProducts();
            
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
     * API lấy sản phẩm sắp hết hàng
     * GET /api/products/low-stock?threshold={threshold}
     */
    @GetMapping("/low-stock")
    public ResponseEntity<Map<String, Object>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Product> products = productService.findLowStockProducts(threshold);
            
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
     * API thêm số lượng vào kho
     * POST /api/products/{productId}/add-stock
     */
    @PostMapping("/{productId}/add-stock")
    public ResponseEntity<Map<String, Object>> addStock(@PathVariable Long productId, 
                                                       @RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity <= 0) {
                response.put("success", false);
                response.put("message", "Số lượng phải lớn hơn 0");
                return ResponseEntity.badRequest().body(response);
            }
            
            Product updatedProduct = productService.addStock(productId, quantity);
            
            response.put("success", true);
            response.put("message", "Thêm số lượng vào kho thành công");
            response.put("data", updatedProduct);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API xuất kho (trừ số lượng)
     * POST /api/products/{productId}/remove-stock
     */
    @PostMapping("/{productId}/remove-stock")
    public ResponseEntity<Map<String, Object>> removeStock(@PathVariable Long productId, 
                                                          @RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer quantity = request.get("quantity");
            if (quantity == null || quantity <= 0) {
                response.put("success", false);
                response.put("message", "Số lượng phải lớn hơn 0");
                return ResponseEntity.badRequest().body(response);
            }
            
            Product updatedProduct = productService.removeStock(productId, quantity);
            
            response.put("success", true);
            response.put("message", "Xuất kho thành công");
            response.put("data", updatedProduct);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API lấy danh sách categories
     * GET /api/products/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<String> categories = productService.findDistinctCategories();
            
            response.put("success", true);
            response.put("data", categories);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API lấy danh sách brands
     * GET /api/products/brands
     */
    @GetMapping("/brands")
    public ResponseEntity<Map<String, Object>> getBrands() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<String> brands = productService.findDistinctBrands();
            
            response.put("success", true);
            response.put("data", brands);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * API thống kê sản phẩm
     * GET /api/products/statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getProductStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long totalProducts = productService.count();
            BigDecimal totalValue = productService.getTotalInventoryValue();
            List<Product> inStockProducts = productService.findProductsInStock();
            List<Product> outOfStockProducts = productService.findOutOfStockProducts();
            List<Product> lowStockProducts = productService.findLowStockProducts(10);
            
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalProducts", totalProducts);
            statistics.put("totalInventoryValue", totalValue);
            statistics.put("inStockCount", inStockProducts.size());
            statistics.put("outOfStockCount", outOfStockProducts.size());
            statistics.put("lowStockCount", lowStockProducts.size());
            
            response.put("success", true);
            response.put("data", statistics);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi hệ thống: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
