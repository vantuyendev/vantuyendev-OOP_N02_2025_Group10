package com.shopmanagement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root controller to handle base URL mappings
 */
@Controller
public class RootController {
    
    /**
     * Redirect root URL to shop login
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/shop/login";
    }
    
    /**
     * Handle error page
     */
    @GetMapping("/error")
    public String error() {
        return "redirect:/shop/login";
    }
}
