package com.shopmanagement.service;

import com.shopmanagement.entity.Customer;
import com.shopmanagement.entity.Login;
import com.shopmanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class cho Customer entity
 * Chứa business logic cho việc quản lý khách hàng
 */
@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private LoginService loginService;
    
    /**
     * Tạo khách hàng mới (bao gồm tài khoản đăng nhập)
     * @param customer thông tin khách hàng
     * @param password mật khẩu
     * @return Customer object đã được lưu
     */
    public Customer createCustomer(Customer customer, String password) {
        // Tạo tài khoản đăng nhập trước
        Login login = new Login(customer.getUserId(), password, 1); // 1 = Customer status
        loginService.createLogin(login);
        
        // Lưu thông tin khách hàng
        customer.setLogin(login);
        return customerRepository.save(customer);
    }
    
    /**
     * Cập nhật thông tin khách hàng
     * @param customer thông tin khách hàng cần cập nhật
     * @return Customer object đã được cập nhật
     */
    public Customer updateCustomer(Customer customer) {
        if (!customerRepository.existsById(customer.getUserId())) {
            throw new IllegalArgumentException("Customer không tồn tại với ID: " + customer.getUserId());
        }
        return customerRepository.save(customer);
    }
    
    /**
     * Xóa khách hàng (bao gồm tài khoản đăng nhập)
     * @param userId ID khách hàng
     */
    public void deleteCustomer(String userId) {
        if (customerRepository.existsById(userId)) {
            customerRepository.deleteById(userId);
            // Xóa tài khoản đăng nhập
            loginService.deleteLogin(userId);
        }
    }
    
    /**
     * Tìm khách hàng theo userId
     * @param userId ID khách hàng
     * @return Customer object nếu tìm thấy
     */
    public Optional<Customer> findById(String userId) {
        return customerRepository.findById(userId);
    }
    
    /**
     * Tìm khách hàng theo userId kèm thông tin đăng nhập
     * @param userId ID khách hàng
     * @return Customer object kèm thông tin đăng nhập
     */
    public Optional<Customer> findByIdWithLogin(String userId) {
        return customerRepository.findByUserIdWithLogin(userId);
    }
    
    /**
     * Lấy tất cả khách hàng
     * @return danh sách tất cả khách hàng
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    /**
     * Lấy tất cả khách hàng kèm thông tin đăng nhập
     * @return danh sách tất cả khách hàng kèm thông tin đăng nhập
     */
    public List<Customer> findAllWithLogin() {
        return customerRepository.findAllWithLogin();
    }
    
    /**
     * Tìm khách hàng theo tên
     * @param name tên khách hàng (tìm kiếm gần đúng)
     * @return danh sách khách hàng phù hợp
     */
    public List<Customer> findByName(String name) {
        return customerRepository.findByCustomerNameContainingIgnoreCase(name);
    }
    
    /**
     * Tìm khách hàng theo số điện thoại
     * @param phoneNumber số điện thoại
     * @return Customer object nếu tìm thấy
     */
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
    
    /**
     * Tìm khách hàng theo email
     * @param email địa chỉ email
     * @return Customer object nếu tìm thấy
     */
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    
    /**
     * Tìm khách hàng theo địa chỉ
     * @param address địa chỉ (tìm kiếm gần đúng)
     * @return danh sách khách hàng phù hợp
     */
    public List<Customer> findByAddress(String address) {
        return customerRepository.findByAddressContainingIgnoreCase(address);
    }
    
    /**
     * Đếm tổng số khách hàng
     * @return số lượng khách hàng
     */
    public long count() {
        return customerRepository.count();
    }
    
    /**
     * Kiểm tra khách hàng có tồn tại hay không
     * @param userId ID khách hàng
     * @return true nếu tồn tại, false nếu không
     */
    public boolean existsById(String userId) {
        return customerRepository.existsById(userId);
    }
}
