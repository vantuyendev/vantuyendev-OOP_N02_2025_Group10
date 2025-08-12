package com.shopmanagement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root controller to handle base URL mappings
 */
@Controller
public class RootController {
    
    /**
     * Redirect alternative start URL to shop login
     * Note: Root path "/" is handled by HomeController to avoid ambiguity.
     */
    @GetMapping("/start")
    public String root() {
        return "redirect:/shop/login";
    }
    
    /**
     * Custom app error landing (avoid conflicting with Spring Boot default /error)
     */
    @GetMapping("/app-error")
    public String error() {
        return "redirect:/shop/login";
    }
}
