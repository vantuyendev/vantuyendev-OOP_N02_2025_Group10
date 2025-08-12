package com.shopmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Controller for handling language switching
 */
@Controller
public class LanguageController {

    private final LocaleResolver localeResolver;

    public LanguageController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    /**
     * Change language and redirect back to the referring page
     */
    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String language,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        
        Locale locale;
        switch (language.toLowerCase()) {
            case "en":
                locale = Locale.ENGLISH;
                break;
            case "vi":
                locale = new Locale("vi", "VN");
                break;
            default:
                locale = new Locale("vi", "VN"); // Default to Vietnamese
        }
        
        localeResolver.setLocale(request, response, locale);
        
        // Redirect back to the referring page or dashboard if no referer
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            // Make sure we stay within our application
            if (referer.contains("/shop/") || referer.contains("/changeLanguage")) {
                return "redirect:" + referer;
            }
        }
        
        return "redirect:/shop/dashboard";
    }
}
