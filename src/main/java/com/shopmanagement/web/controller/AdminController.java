package com.shopmanagement.web.controller;

import com.shopmanagement.entity.Employee;
import com.shopmanagement.entity.Login;
import com.shopmanagement.service.EmployeeService;
import com.shopmanagement.service.LoginService;
import com.shopmanagement.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Controller cho quản lý Admin và Employee
 */
@Controller
@RequestMapping({"/admin", "/shop/admin"})
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private LoginService loginService;

    /**
     * Trang chính Admin Dashboard
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        // Thống kê cơ bản
        model.addAttribute("totalEmployees", employeeService.count());
        model.addAttribute("totalCustomers", loginService.countByStatus(1));
        model.addAttribute("departments", employeeService.getAllDepartments());
        model.addAttribute("roles", employeeService.getAllRoles());

        return "admin/dashboard";
    }

    /**
     * Quản lý nhân viên
     */
    @GetMapping("/employees")
    public String manageEmployees(Model model, HttpSession session,
                                @RequestParam(required = false) String department,
                                @RequestParam(required = false) String role,
                                @RequestParam(required = false) String search) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        List<Employee> employees;
        
        if (search != null && !search.trim().isEmpty()) {
            employees = employeeService.searchByName(search.trim());
        } else if (department != null && role != null && !department.isEmpty() && !role.isEmpty()) {
            employees = employeeService.findByDepartmentAndRole(department, role);
        } else if (department != null && !department.isEmpty()) {
            employees = employeeService.findByDepartment(department);
        } else if (role != null && !role.isEmpty()) {
            employees = employeeService.findByRole(role);
        } else {
            employees = employeeService.findAll();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("departments", employeeService.getAllDepartments());
        model.addAttribute("roles", employeeService.getAllRoles());
        model.addAttribute("selectedDepartment", department);
        model.addAttribute("selectedRole", role);
        model.addAttribute("search", search);

        return "admin/employees";
    }

    /**
     * Form thêm nhân viên mới
     */
    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", employeeService.getAllDepartments());
        model.addAttribute("roles", employeeService.getAllRoles());

        return "admin/add-employee";
    }

    /**
     * Xử lý thêm nhân viên mới
     */
    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                            @RequestParam String password,
                            Model model,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        try {
            // Tạo login account cho employee
            Login login = new Login(employee.getUserId(), password, 0); // 0 = Employee
            loginService.createLogin(login);
            
            // Tạo employee
            employeeService.createEmployee(employee);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Nhân viên " + employee.getEmployeeName() + " đã được thêm thành công!");
            
            return "redirect:/admin/employees";
            
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            model.addAttribute("employee", employee);
            model.addAttribute("departments", employeeService.getAllDepartments());
            model.addAttribute("roles", employeeService.getAllRoles());
            
            return "admin/add-employee";
        }
    }

    /**
     * Form sửa thông tin nhân viên
     */
    @GetMapping("/employees/edit/{userId}")
    public String editEmployeeForm(@PathVariable String userId, Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        Optional<Employee> employeeOpt = employeeService.findById(userId);
        if (!employeeOpt.isPresent()) {
            return "redirect:/admin/employees?error=not_found";
        }

        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("departments", employeeService.getAllDepartments());
        model.addAttribute("roles", employeeService.getAllRoles());

        return "admin/edit-employee";
    }

    /**
     * Xử lý cập nhật thông tin nhân viên
     */
    @PostMapping("/employees/edit/{userId}")
    public String editEmployee(@PathVariable String userId,
                             @ModelAttribute Employee employee,
                             Model model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        try {
            employee.setUserId(userId);
            employeeService.updateEmployee(employee);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Thông tin nhân viên " + employee.getEmployeeName() + " đã được cập nhật!");
            
            return "redirect:/admin/employees";
            
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            model.addAttribute("employee", employee);
            model.addAttribute("departments", employeeService.getAllDepartments());
            model.addAttribute("roles", employeeService.getAllRoles());
            
            return "admin/edit-employee";
        }
    }

    /**
     * Xóa nhân viên
     */
    @PostMapping("/employees/delete/{userId}")
    public String deleteEmployee(@PathVariable String userId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        try {
            // Xóa nhân viên (cascade sẽ xóa login account)
            employeeService.deleteEmployee(userId);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Nhân viên đã được xóa thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa nhân viên: " + e.getMessage());
        }

        return "redirect:/admin/employees";
    }

    /**
     * Chi tiết nhân viên
     */
    @GetMapping("/employees/{userId}")
    public String employeeDetails(@PathVariable String userId, Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        Optional<Employee> employeeOpt = employeeService.findById(userId);
        if (!employeeOpt.isPresent()) {
            return "redirect:/admin/employees?error=not_found";
        }

        model.addAttribute("employee", employeeOpt.get());
        return "admin/employee-details";
    }

    /**
     * Thống kê theo department
     */
    @GetMapping("/statistics/departments")
    public String departmentStatistics(Model model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession == null || !userSession.isAdmin()) {
            return "redirect:/shop/login?error=access_denied";
        }

        List<String> departments = employeeService.getAllDepartments();
        model.addAttribute("departments", departments);
        
        // Tính số nhân viên cho mỗi department
        departments.forEach(dept -> {
            long count = employeeService.countByDepartment(dept);
            model.addAttribute("count_" + dept.replaceAll("\\s+", "_"), count);
        });

        return "admin/department-statistics";
    }
}
