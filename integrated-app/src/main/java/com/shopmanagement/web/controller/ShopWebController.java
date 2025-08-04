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
import java.util.Map;
import java.util.HashMap;
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

    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "shop/add-employee";
    }

        @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam String password, Model model) {
        try {
            employeeService.createEmployee(employee, password);
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm nhân viên: " + e.getMessage());
            return "shop/add-employee";
        }
    }

    @GetMapping("/employees/edit/{id}")
    public String editEmployeeForm(@PathVariable String id, Model model) {
        try {
            Optional<Employee> employee = employeeService.findById(id);
            if (employee.isPresent()) {
                model.addAttribute("employee", employee.get());
                return "shop/edit-employee";
            } else {
                return "redirect:/shop/employees?error=Employee not found";
            }
        } catch (Exception e) {
            return "redirect:/shop/employees?error=Unable to load employee: " + e.getMessage();
        }
    }

        @PostMapping("/employees/edit/{id}")
    public String updateEmployee(@PathVariable String id, @ModelAttribute Employee employee, Model model) {
        try {
            employee.setUserId(id);
            employeeService.updateEmployee(employee);
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật nhân viên: " + e.getMessage());
            return "redirect:/employees";
        }
    }

    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return "redirect:/shop/employees?success=Employee deleted successfully";
        } catch (Exception e) {
            return "redirect:/shop/employees?error=Unable to delete employee: " + e.getMessage();
        }
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

    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "shop/add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            productService.createProduct(product);
            return "redirect:/shop/products?success=Product added successfully";
        } catch (Exception e) {
            model.addAttribute("error", "Unable to add product: " + e.getMessage());
            model.addAttribute("product", product);
            return "shop/add-product";
        }
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
                return "shop/edit-product";
            } else {
                return "redirect:/shop/products?error=Product not found";
            }
        } catch (Exception e) {
            return "redirect:/shop/products?error=Unable to load product: " + e.getMessage();
        }
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, Model model) {
        try {
            product.setProductId(id);
            productService.updateProduct(product);
            return "redirect:/shop/products?success=Product updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", "Unable to update product: " + e.getMessage());
            model.addAttribute("product", product);
            return "shop/edit-product";
        }
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/shop/products?success=Product deleted successfully";
        } catch (Exception e) {
            return "redirect:/shop/products?error=Unable to delete product: " + e.getMessage();
        }
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

    @GetMapping("/customers/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "shop/add-customer";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@ModelAttribute Customer customer, @RequestParam String password, Model model) {
        try {
            customerService.createCustomer(customer, password);
            return "redirect:/shop/customers?success=Customer added successfully";
        } catch (Exception e) {
            model.addAttribute("error", "Unable to add customer: " + e.getMessage());
            model.addAttribute("customer", customer);
            return "shop/add-customer";
        }
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomerForm(@PathVariable String id, Model model) {
        try {
            Optional<Customer> customer = customerService.findById(id);
            if (customer.isPresent()) {
                model.addAttribute("customer", customer.get());
                return "shop/edit-customer";
            } else {
                return "redirect:/shop/customers?error=Customer not found";
            }
        } catch (Exception e) {
            return "redirect:/shop/customers?error=Unable to load customer: " + e.getMessage();
        }
    }

    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable String id, @ModelAttribute Customer customer, Model model) {
        try {
            customer.setUserId(id);
            customerService.updateCustomer(customer);
            return "redirect:/shop/customers?success=Customer updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", "Unable to update customer: " + e.getMessage());
            model.addAttribute("customer", customer);
            return "shop/edit-customer";
        }
    }

    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return "redirect:/shop/customers?success=Customer deleted successfully";
        } catch (Exception e) {
            return "redirect:/shop/customers?error=Unable to delete customer: " + e.getMessage();
        }
    }

    @GetMapping("/api/dashboard-stats")
    @ResponseBody
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        try {
            List<Employee> employees = employeeService.findAll();
            List<Product> products = productService.findAll();
            List<Customer> customers = customerService.findAll();
            
            stats.put("employeeCount", employees.size());
            stats.put("productCount", products.size());
            stats.put("customerCount", customers.size());
            stats.put("status", "success");
            stats.put("timestamp", new java.util.Date());
            
        } catch (Exception e) {
            stats.put("error", "Unable to load dashboard data: " + e.getMessage());
            stats.put("status", "error");
        }
        return stats;
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
