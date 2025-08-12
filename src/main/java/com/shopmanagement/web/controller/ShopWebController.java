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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    // ------------------------
    // Employee - Web actions
    // ------------------------

    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        // Form uses raw fields; optional: provide defaults
        model.addAttribute("userSession", userSession);
        return "shop/add-employee";
    }

    @PostMapping("/employees/add")
    public String addEmployeeSubmit(@RequestParam String employeeName,
                                    @RequestParam String phoneNumber,
                                    @RequestParam String role,
                                    @RequestParam String department,
                                    @RequestParam BigDecimal salary,
                                    @RequestParam(required = false) String email,
                                    @RequestParam String password,
                                    @RequestParam(required = false) String address,
                                    Model model,
                                    HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            // Generate a userId for employee if not provided in form
            String base = (employeeName != null ? employeeName.replaceAll("\\s+", "").toLowerCase() : "emp");
            String suffix = phoneNumber != null && phoneNumber.length() >= 4 ? phoneNumber.substring(phoneNumber.length() - 4) : UUID.randomUUID().toString().substring(0, 4);
            String userId = base + suffix;

            // Build employee entity
            Employee employee = new Employee();
            employee.setUserId(userId);
            employee.setEmployeeName(employeeName);
            employee.setPhoneNumber(phoneNumber);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSalary(salary);
            // Optional fields if present on entity can be set here (email/address not available in current model)

            employeeService.createEmployee(employee, password);
            return "redirect:/shop/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tạo nhân viên: " + e.getMessage());
            return "shop/add-employee";
        }
    }

    @GetMapping("/employees/edit/{userId}")
    public String editEmployeeForm(@PathVariable String userId, Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            Optional<Employee> employeeOpt = employeeService.findById(userId);
            if (employeeOpt.isPresent()) {
                model.addAttribute("employee", employeeOpt.get());
                model.addAttribute("userSession", userSession);
                return "shop/edit-employee";
            } else {
                return "redirect:/shop/employees";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải thông tin nhân viên: " + e.getMessage());
            return "shop/employees";
        }
    }

    @PostMapping("/employees/edit/{userId}")
    public String editEmployeeSubmit(@PathVariable String userId,
                                     @ModelAttribute Employee employee,
                                     Model model,
                                     HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            employee.setUserId(userId);
            employeeService.updateEmployee(employee);
            return "redirect:/shop/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể cập nhật nhân viên: " + e.getMessage());
            model.addAttribute("employee", employee);
            return "shop/edit-employee";
        }
    }

    @PostMapping("/employees/delete/{userId}")
    public String deleteEmployee(@PathVariable String userId, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            employeeService.deleteEmployee(userId);
        } catch (Exception ignored) {}
        return "redirect:/shop/employees";
    }

    // ------------------------
    // Product - Web actions
    // ------------------------

    @GetMapping("/products/add")
    public String addProductForm(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

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
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

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
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

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
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

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
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            productService.deleteProduct(productId);
        } catch (Exception ignored) {}
        return "redirect:/shop/products";
    }

    // ------------------------
    // Customer - Web actions
    // ------------------------

    @GetMapping("/customers/add")
    public String addCustomerForm(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        model.addAttribute("customer", new Customer());
        model.addAttribute("userSession", userSession);
        return "shop/add-customer";
    }

    @PostMapping("/customers/add")
    public String addCustomerSubmit(@ModelAttribute Customer customer,
                                    @RequestParam String password,
                                    Model model,
                                    HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            customerService.createCustomer(customer, password);
            return "redirect:/shop/customers";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tạo khách hàng: " + e.getMessage());
            model.addAttribute("customer", customer);
            return "shop/add-customer";
        }
    }

    @GetMapping("/customers/edit/{userId}")
    public String editCustomerForm(@PathVariable String userId, Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            Optional<Customer> customerOpt = customerService.findById(userId);
            if (customerOpt.isPresent()) {
                model.addAttribute("customer", customerOpt.get());
                model.addAttribute("userSession", userSession);
                return "shop/edit-customer";
            } else {
                return "redirect:/shop/customers";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải thông tin khách hàng: " + e.getMessage());
            return "shop/customers";
        }
    }

    @PostMapping("/customers/edit/{userId}")
    public String editCustomerSubmit(@PathVariable String userId,
                                     @ModelAttribute Customer customer,
                                     Model model,
                                     HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            customer.setUserId(userId);
            customerService.updateCustomer(customer);
            return "redirect:/shop/customers";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể cập nhật khách hàng: " + e.getMessage());
            model.addAttribute("customer", customer);
            return "shop/edit-customer";
        }
    }

    @PostMapping("/customers/delete/{userId}")
    public String deleteCustomer(@PathVariable String userId, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null) return "redirect:/shop/login";
        if (!userSession.isAdmin() && !userSession.isEmployee()) return "redirect:/shop/dashboard";

        try {
            customerService.deleteCustomer(userId);
        } catch (Exception ignored) {}
        return "redirect:/shop/customers";
    }

    // ------------------------
    // Lightweight stats API for dashboard.js
    // ------------------------
    @GetMapping("/api/dashboard-stats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> dashboardStats(HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        try {
            long employeeCount = employeeService.count();
            long productCount = productService.count();
            long customerCount = customerService.count();
            res.put("employeeCount", employeeCount);
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
