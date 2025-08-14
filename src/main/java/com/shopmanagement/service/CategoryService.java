package com.shopmanagement.service;

import com.shopmanagement.entity.Category;
import com.shopmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class cho Category entity
 * Chứa business logic cho việc quản lý danh mục sản phẩm
 */
@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Tạo danh mục mới
     */
    public Category createCategory(Category category) {
        // Kiểm tra tên danh mục đã tồn tại chưa
        Optional<Category> existingCategory = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Danh mục với tên '" + category.getCategoryName() + "' đã tồn tại");
        }
        
        return categoryRepository.save(category);
    }
    
    /**
     * Cập nhật danh mục
     */
    public Category updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
        if (!existingCategory.isPresent()) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + category.getCategoryId());
        }
        
        // Kiểm tra tên danh mục trùng lặp (trừ chính nó)
        Optional<Category> categoryWithSameName = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (categoryWithSameName.isPresent() && !categoryWithSameName.get().getCategoryId().equals(category.getCategoryId())) {
            throw new RuntimeException("Danh mục với tên '" + category.getCategoryName() + "' đã tồn tại");
        }
        
        return categoryRepository.save(category);
    }
    
    /**
     * Xóa danh mục (soft delete)
     */
    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId);
        }
        
        // Kiểm tra danh mục có sản phẩm không
        Long productCount = categoryRepository.countProductsInCategory(categoryId);
        if (productCount > 0) {
            throw new RuntimeException("Không thể xóa danh mục vì còn có " + productCount + " sản phẩm");
        }
        
        Category categoryToDelete = category.get();
        categoryToDelete.setIsActive(false);
        categoryRepository.save(categoryToDelete);
    }
    
    /**
     * Xóa vĩnh viễn danh mục
     */
    public void permanentDeleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId);
        }
        
        // Kiểm tra danh mục có sản phẩm không
        Long productCount = categoryRepository.countProductsInCategory(categoryId);
        if (productCount > 0) {
            throw new RuntimeException("Không thể xóa danh mục vì còn có " + productCount + " sản phẩm");
        }
        
        categoryRepository.deleteById(categoryId);
    }
    
    /**
     * Lấy tất cả danh mục
     */
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    /**
     * Lấy danh mục đang hoạt động
     */
    @Transactional(readOnly = true)
    public List<Category> findActiveCategories() {
        return categoryRepository.findActiveCategoriesOrderByDisplayOrder();
    }
    
    /**
     * Lấy danh mục theo ID
     */
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
    
    /**
     * Lấy danh mục theo tên
     */
    @Transactional(readOnly = true)
    public Optional<Category> findByName(String categoryName) {
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName);
    }
    
    /**
     * Tìm kiếm danh mục
     */
    @Transactional(readOnly = true)
    public List<Category> searchCategories(String keyword) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword);
    }
    
    /**
     * Lấy danh mục có sản phẩm
     */
    @Transactional(readOnly = true)
    public List<Category> findCategoriesWithProducts() {
        return categoryRepository.findCategoriesWithProducts();
    }
    
    /**
     * Đếm số lượng danh mục
     */
    @Transactional(readOnly = true)
    public Long count() {
        return categoryRepository.count();
    }
    
    /**
     * Đếm số lượng danh mục đang hoạt động
     */
    @Transactional(readOnly = true)
    public Long countActiveCategories() {
        return categoryRepository.findByIsActiveTrue().stream().count();
    }
    
    /**
     * Kiểm tra danh mục tồn tại và đang hoạt động
     */
    @Transactional(readOnly = true)
    public boolean existsAndActive(Long categoryId) {
        return categoryRepository.existsByCategoryIdAndIsActiveTrue(categoryId);
    }
}
