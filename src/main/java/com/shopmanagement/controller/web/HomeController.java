package com.shopmanagement.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web Controller cho trang chủ
 * Xử lý các request để render HTML views
 */
@Controller
public class HomeController {
    
    /**
     * Trang chủ - redirect đến shop login
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/shop/login";
    }
}
