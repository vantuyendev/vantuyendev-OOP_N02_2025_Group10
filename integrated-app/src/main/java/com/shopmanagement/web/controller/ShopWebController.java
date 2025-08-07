package com.shopmanagement.web.controller;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Customer;
import com.shopmanagement.entity.Login;
import com.shopmanagement.model.UserSession;
import com.shopmanagement.service.EmployeeService;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CustomerService;
import com.shopmanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String home(Model model, HttpSession session) {
        return dashboard(model, session);
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        
        // Redirect to login if not authenticated
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        try {
            // Get basic statistics for dashboard
            List<Employee> employees = employeeService.findAll();
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            model.addAttribute("employeeCount", employees.size());
            model.addAttribute("productCount", products.size());
            model.addAttribute("customerCount", customers.size());
            model.addAttribute("userSession", userSession);
            
            // Redirect to appropriate dashboard based on user type
            if (userSession.isAdmin()) {
                return "shop/admin-dashboard";
            } else if (userSession.isEmployee()) {
                return "shop/employee-dashboard";
            } else if (userSession.isCustomer()) {
                return "shop/customer-dashboard";
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
        }
        
        return "shop/dashboard";
    }

    @GetMapping("/employees")
    public String employees(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        // Only admin and employees can access employee management
        if (!userSession.isAdmin() && !userSession.isEmployee()) {
            model.addAttribute("error", "Bạn không có quyền truy cập trang này");
            return "redirect:/shop/dashboard";
        }
        
        try {
            List<Employee> employees = employeeService.findAll();
            model.addAttribute("employees", employees);
            model.addAttribute("userSession", userSession);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load employees: " + e.getMessage());
        }
        return "shop/employees";
    }

    @GetMapping("/products")
    public String products(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            model.addAttribute("userSession", userSession);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load products: " + e.getMessage());
        }
        return "shop/products";
    }

    @GetMapping("/customers")
    public String customers(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        // Customers can't view other customers
        if (userSession.isCustomer()) {
            model.addAttribute("error", "Bạn không có quyền xem thông tin khách hàng khác");
            return "redirect:/shop/dashboard";
        }
        
        try {
            List<Customer> customers = customerService.findAll();
            model.addAttribute("customers", customers);
            model.addAttribute("userSession", userSession);
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
    public String processLogin(@RequestParam String username, @RequestParam String password, 
                             Model model, HttpSession session) {
        try {
            // Validate input
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                model.addAttribute("error", "Vui lòng nhập tên đăng nhập và mật khẩu");
                return "shop/login";
            }
            
            // Authenticate user
            Optional<Login> loginOpt = loginService.authenticate(username.trim(), password.trim());
            if (loginOpt.isPresent()) {
                Login login = loginOpt.get();
                
                // Create user session
                UserSession userSession = createUserSession(login);
                session.setAttribute("userSession", userSession);
                
                model.addAttribute("success", "Đăng nhập thành công");
                return "redirect:/shop/dashboard";
            } else {
                model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
                return "shop/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "shop/login";
        }
    }
    
    /**
     * Create user session with detailed user information
     */
    private UserSession createUserSession(Login login) {
        UserSession userSession = new UserSession();
        userSession.setUserId(login.getUserId());
        userSession.setStatus(login.getStatus());
        
        // Determine user type and get additional info
        if ("admin".equalsIgnoreCase(login.getUserId())) {
            userSession.setUserType("ADMIN");
            userSession.setName("Administrator");
            userSession.setRole("System Administrator");
            userSession.setDepartment("IT");
        } else if (login.getStatus() == 0) { // Employee
            userSession.setUserType("EMPLOYEE");
            try {
                Optional<Employee> empOpt = employeeService.findById(login.getUserId());
                if (empOpt.isPresent()) {
                    Employee emp = empOpt.get();
                    userSession.setName(emp.getEmployeeName());
                    userSession.setRole(emp.getRole());
                    userSession.setDepartment(emp.getDepartment());
                    userSession.setPhone(emp.getPhoneNumber());
                } else {
                    userSession.setName("Employee");
                    userSession.setRole("Staff");
                }
            } catch (Exception e) {
                userSession.setName("Employee");
                userSession.setRole("Staff");
            }
        } else { // Customer
            userSession.setUserType("CUSTOMER");
            try {
                Optional<Customer> custOpt = customerService.findById(login.getUserId());
                if (custOpt.isPresent()) {
                    Customer cust = custOpt.get();
                    userSession.setName(cust.getCustomerName());
                    userSession.setEmail(cust.getEmail());
                    userSession.setPhone(cust.getPhoneNumber());
                } else {
                    userSession.setName("Customer");
                }
            } catch (Exception e) {
                userSession.setName("Customer");
            }
        }
        
        return userSession;
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/shop/login?message=Đăng xuất thành công";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        model.addAttribute("userSession", userSession);
        return "shop/profile";
    }

    @GetMapping("/search")
    public String search(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        model.addAttribute("userSession", userSession);
        return "shop/search";
    }

    @GetMapping("/statistics")
    public String statistics(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        try {
            // Get statistics data for the charts
            List<Employee> employees = employeeService.findAll();
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            model.addAttribute("employeeCount", employees.size());
            model.addAttribute("productCount", products.size());
            model.addAttribute("customerCount", customers.size());
            model.addAttribute("userSession", userSession);
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load statistics: " + e.getMessage());
        }
        return "shop/statistics";
    }
}
