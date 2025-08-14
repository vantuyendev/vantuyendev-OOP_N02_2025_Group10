package com.shopmanagement.web.controller;

import com.shopmanagement.entity.Product;
import com.shopmanagement.entity.Customer;
import com.shopmanagement.entity.Login;
import com.shopmanagement.entity.Order;
import com.shopmanagement.model.UserSession;
import com.shopmanagement.service.ProductService;
import com.shopmanagement.service.CustomerService;
import com.shopmanagement.service.LoginService;
import com.shopmanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopWebController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private OrderService orderService;

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
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            model.addAttribute("productCount", products.size());
            model.addAttribute("customerCount", customers.size());
            model.addAttribute("userSession", userSession);
            
            // Redirect to appropriate dashboard based on user type
            if (userSession.isAdmin()) {
                return "shop/admin-dashboard";
            } else if (userSession.isCustomer()) {
                return "shop/customer-dashboard";
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
        }
        
        return "shop/dashboard";
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

    // ------------------------
    // Product - Web actions
    // ------------------------

    @GetMapping("/products/add")
    public String addProductForm(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin()) return "redirect:/shop/dashboard";

        model.addAttribute("product", new Product());
        model.addAttribute("userSession", userSession);
        return "shop/add-product";
    }

    @PostMapping("/products/add")
    public String addProductSubmit(@ModelAttribute Product product,
                                   Model model,
                                   HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin()) return "redirect:/shop/dashboard";

        try {
            productService.createProduct(product);
            return "redirect:/shop/products";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tạo sản phẩm: " + e.getMessage());
            model.addAttribute("product", product);
            return "shop/add-product";
        }
    }

    @GetMapping("/products/edit/{productId}")
    public String editProductForm(@PathVariable Long productId, Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin()) return "redirect:/shop/dashboard";

        try {
            Optional<Product> productOpt = productService.findById(productId);
            if (productOpt.isPresent()) {
                model.addAttribute("product", productOpt.get());
                model.addAttribute("userSession", userSession);
                return "shop/edit-product";
            } else {
                return "redirect:/shop/products";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải thông tin sản phẩm: " + e.getMessage());
            return "shop/products";
        }
    }

    @PostMapping("/products/edit/{productId}")
    public String editProductSubmit(@PathVariable Long productId,
                                    @ModelAttribute Product product,
                                    Model model,
                                    HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin()) return "redirect:/shop/dashboard";

        try {
            product.setProductId(productId);
            productService.updateProduct(product);
            return "redirect:/shop/products";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể cập nhật sản phẩm: " + e.getMessage());
            model.addAttribute("product", product);
            return "shop/edit-product";
        }
    }

    @PostMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin()) return "redirect:/shop/dashboard";

        try {
            productService.deleteProduct(productId);
        } catch (Exception ignored) {}
        return "redirect:/shop/products";
    }

    // ------------------------
    // Customer - Web actions
    // ------------------------

    // ------------------------
    // Lightweight stats API for dashboard.js
    // ------------------------
    @GetMapping("/api/dashboard-stats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> dashboardStats(HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        try {
            long productCount = productService.count();
            long customerCount = customerService.count();
            res.put("productCount", productCount);
            res.put("customerCount", customerCount);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
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
    String msg = URLEncoder.encode("Đăng xuất thành công", StandardCharsets.UTF_8);
    return "redirect:/shop/login?message=" + msg;
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
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            model.addAttribute("productCount", products.size());
            model.addAttribute("customerCount", customers.size());
            model.addAttribute("userSession", userSession);
            
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load statistics: " + e.getMessage());
        }
        return "shop/statistics";
    }

    @GetMapping("/audit")
    public String auditLog(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        if (!userSession.isAdmin()) {
            return "redirect:/shop/dashboard";
        }
        
        model.addAttribute("userSession", userSession);
        return "shop/audit";
    }

    @GetMapping("/shop-interface")
    public String shopInterface(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) {
            return "redirect:/shop/login";
        }
        
        if (!userSession.isCustomer()) {
            return "redirect:/shop/dashboard";
        }
        
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            model.addAttribute("userSession", userSession);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to load products: " + e.getMessage());
        }
        return "shop/shop";
    }
    
    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processPurchase(@RequestBody Map<String, Object> purchaseData, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        Map<String, Object> response = new HashMap<>();
        
        if (userSession == null || !userSession.isCustomer()) {
            response.put("success", false);
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(401).body(response);
        }
        
        try {
            String customerId = userSession.getUserId();
            String shippingAddress = (String) purchaseData.get("shippingAddress");
            String paymentMethod = (String) purchaseData.get("paymentMethod");
            String notes = (String) purchaseData.get("notes");
            
            // Validate required fields
            if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Shipping address is required");
                return ResponseEntity.status(400).body(response);
            }
            
            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Payment method is required");
                return ResponseEntity.status(400).body(response);
            }
            
            // Create order from cart
            Order order = orderService.createOrderFromCart(customerId, shippingAddress, paymentMethod, notes);
            
            if (order != null) {
                response.put("success", true);
                response.put("message", "Order placed successfully! Order ID: " + order.getOrderId());
                response.put("orderId", order.getOrderId());
            } else {
                response.put("success", false);
                response.put("message", "Unable to create order. Cart may be empty.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Purchase failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
