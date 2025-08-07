package com.shopmanagement.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Shop Web Controller
 * Xử lý các request cho shop management
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
    
    /**
     * Shop home - redirect to dashboard
     */
    @GetMapping
    public String shopHome() {
        return "redirect:/shop/dashboard";
    }
    
    /**
     * Shop dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard - Shop Management System");
        return "shop/dashboard";
    }
    
    /**
     * Login page
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Đăng nhập - Shop Management System");
        return "shop/login";
    }
    
    /**
     * Products management
     */
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("title", "Quản lý sản phẩm - Shop Management System");
        return "shop/products";
    }
    
    /**
     * Customers management
     */
    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("title", "Quản lý khách hàng - Shop Management System");
        return "shop/customers";
    }
    
    /**
     * Employees management
     */
    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("title", "Quản lý nhân viên - Shop Management System");
        return "shop/employees";
    }
    
    /**
     * Statistics page
     */
    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("title", "Thống kê - Shop Management System");
        return "shop/statistics";
    }
    
    /**
     * Profile page
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Hồ sơ - Shop Management System");
        return "shop/profile";
    }
    
    /**
     * Search page
     */
    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("title", "Tìm kiếm - Shop Management System");
        return "shop/search";
    }
    
    /**
     * Add customer page
     */
    @GetMapping("/add-customer")
    public String addCustomer(Model model) {
        model.addAttribute("title", "Thêm khách hàng - Shop Management System");
        return "shop/add-customer";
    }
    
    /**
     * Add product page
     */
    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("title", "Thêm sản phẩm - Shop Management System");
        return "shop/add-product";
    }
    
    /**
     * Add employee page
     */
    @GetMapping("/add-employee")
    public String addEmployee(Model model) {
        model.addAttribute("title", "Thêm nhân viên - Shop Management System");
        return "shop/add-employee";
    }
}
