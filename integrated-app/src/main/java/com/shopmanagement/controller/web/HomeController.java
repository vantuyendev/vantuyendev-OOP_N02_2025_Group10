package com.shopmanagement.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web Controller cho trang chủ và dashboard
 * Xử lý các request để render HTML views
 */
@Controller
@RequestMapping("/")
public class HomeController {
    
    /**
     * Trang chủ - redirect đến dashboard
     */
    @GetMapping
    public String home() {
        return "redirect:/shop/dashboard";
    }
    
    /**
     * Dashboard chính
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "redirect:/shop/dashboard";
    }
    
    /**
     * Trang đăng nhập
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Đăng nhập - Shop Management System");
        return "login";
    }
    
    /**
     * Trang quản lý sản phẩm
     */
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("title", "Quản lý sản phẩm - Shop Management System");
        return "shop/products";
    }
    
    /**
     * Trang quản lý khách hàng
     */
    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("title", "Quản lý khách hàng - Shop Management System");
        return "shop/customers";
    }
    
    /**
     * Trang quản lý nhân viên
     */
    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("title", "Quản lý nhân viên - Shop Management System");
        return "shop/employees";
    }
    
    /**
     * Trang thống kê
     */
    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("title", "Statistics - Shop Management System");
        return "shop/statistics";
    }
    
    /**
     * Trang cài đặt
     */
    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("title", "Cài đặt - Shop Management System");
        return "settings";
    }
    
    /**
     * Trang tìm kiếm nâng cao
     */
    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("title", "Advanced Search - Shop Management System");
        return "shop/search";
    }
}
