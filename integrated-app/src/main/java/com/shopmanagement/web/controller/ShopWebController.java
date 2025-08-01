package com.shopmanagement.web.controller;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Customer;
import com.shopmanagement.entity.Login;
import com.shopmanagement.service.EmployeeService;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CustomerService;
import com.shopmanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopWebController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            // Get basic statistics for dashboard
            List<Employee> employees = employeeService.findAll();
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            model.addAttribute("employeeCount", employees.size());
            model.addAttribute("productCount", products.size());
            model.addAttribute("customerCount", customers.size());
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
        }
        
        return "shop/dashboard";
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        try {
            List<Employee> employees = employeeService.findAll();
            model.addAttribute("employees", employees);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load employees: " + e.getMessage());
        }
        return "shop/employees";
    }

    @GetMapping("/products")
    public String products(Model model) {
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load products: " + e.getMessage());
        }
        return "shop/products";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        try {
            List<Customer> customers = customerService.findAll();
            model.addAttribute("customers", customers);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load customers: " + e.getMessage());
        }
        return "shop/customers";
    }

    @GetMapping("/login")
    public String login() {
        return "shop/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Simple authentication - in a real application, you'd use Spring Security
        try {
            Optional<Login> loginOpt = loginService.authenticate(username, password);
            if (loginOpt.isPresent()) {
                return "redirect:/shop/";
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "shop/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Login error: " + e.getMessage());
            return "shop/login";
        }
    }
}
