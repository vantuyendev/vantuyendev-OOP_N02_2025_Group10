package com.shopmanagement.repository;

import com.shopmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface cho Customer entity
 * Chứa các phương thức truy vấn dữ liệu cho bảng customer
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    /**
     * Tìm customer theo tên (tìm kiếm không phân biệt chữ hoa/thường)
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByCustomerNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Tìm customer theo số điện thoại
     */
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    
    /**
     * Tìm customer theo email
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Tìm tất cả customer với thông tin login
     */
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.login")
    List<Customer> findAllWithLogin();
    
    /**
     * Tìm customer theo userId với thông tin login
     */
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.login WHERE c.userId = :userId")
    Optional<Customer> findByUserIdWithLogin(@Param("userId") String userId);
    
    /**
     * Đếm tổng số customer
     */
    @Query("SELECT COUNT(c) FROM Customer c")
    long countAllCustomers();
    
    /**
     * Tìm customer theo địa chỉ
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Customer> findByAddressContainingIgnoreCase(@Param("address") String address);
}
