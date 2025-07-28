package com.shopmanagement.repository;

import com.shopmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Employee entity
 * Chứa các phương thức truy vấn dữ liệu cho bảng employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    /**
     * Tìm employee theo tên (tìm kiếm không phân biệt chữ hoa/thường)
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByEmployeeNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Tìm employee theo chức vụ
     */
    List<Employee> findByRole(String role);
    
    /**
     * Tìm employee theo phòng ban
     */
    List<Employee> findByDepartment(String department);
    
    /**
     * Tìm employee theo số điện thoại
     */
    Optional<Employee> findByPhoneNumber(String phoneNumber);
    
    /**
     * Tìm tất cả employee với thông tin login
     */
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.login")
    List<Employee> findAllWithLogin();
    
    /**
     * Tìm employee theo userId với thông tin login
     */
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.login WHERE e.userId = :userId")
    Optional<Employee> findByUserIdWithLogin(@Param("userId") String userId);
    
    /**
     * Tìm employee theo khoảng lương
     */
    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findBySalaryRange(@Param("minSalary") BigDecimal minSalary, @Param("maxSalary") BigDecimal maxSalary);
    
    /**
     * Tìm employee có lương cao nhất
     */
    @Query("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(emp.salary) FROM Employee emp)")
    List<Employee> findEmployeesWithHighestSalary();
    
    /**
     * Đếm số employee theo chức vụ
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.role = :role")
    long countByRole(@Param("role") String role);
    
    /**
     * Tính tổng lương tất cả employee
     */
    @Query("SELECT SUM(e.salary) FROM Employee e")
    BigDecimal getTotalSalary();
    
    /**
     * Tính lương trung bình
     */
    @Query("SELECT AVG(e.salary) FROM Employee e")
    BigDecimal getAverageSalary();
}
