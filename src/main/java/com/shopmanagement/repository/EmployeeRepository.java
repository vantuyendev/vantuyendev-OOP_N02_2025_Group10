package com.shopmanagement.repository;

import com.shopmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Employee entity
 * Chứa các phương thức truy vấn dữ liệu cho bảng employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    /**
     * Tìm nhân viên theo tên (tìm kiếm không phân biệt chữ hoa/thường)
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByEmployeeNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Tìm nhân viên theo department
     */
    List<Employee> findByDepartment(String department);
    
    /**
     * Tìm nhân viên theo role
     */
    List<Employee> findByRole(String role);
    
    /**
     * Tìm nhân viên theo department và role
     */
    List<Employee> findByDepartmentAndRole(String department, String role);
    
    /**
     * Tìm nhân viên theo email
     */
    Optional<Employee> findByEmail(String email);
    
    /**
     * Kiểm tra email đã tồn tại hay chưa
     */
    boolean existsByEmail(String email);
    
    /**
     * Tìm tất cả department distinct
     */
    @Query("SELECT DISTINCT e.department FROM Employee e WHERE e.department IS NOT NULL ORDER BY e.department")
    List<String> findDistinctDepartments();
    
    /**
     * Tìm tất cả role distinct
     */
    @Query("SELECT DISTINCT e.role FROM Employee e WHERE e.role IS NOT NULL ORDER BY e.role")
    List<String> findDistinctRoles();
    
    /**
     * Đếm số nhân viên theo department
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    long countByDepartment(@Param("department") String department);
    
    /**
     * Đếm số nhân viên theo role
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.role = :role")
    long countByRole(@Param("role") String role);
    
    /**
     * Tìm nhân viên có lương trong khoảng
     */
    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findBySalaryBetween(@Param("minSalary") java.math.BigDecimal minSalary, 
                                     @Param("maxSalary") java.math.BigDecimal maxSalary);
}
