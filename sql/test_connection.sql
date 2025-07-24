-- Test Connection cho Shop Management Database
-- File này dùng để test kết nối database

USE shopmanagement;

-- Kiểm tra các bảng có tồn tại không
SHOW TABLES;

-- Xem thông tin users
SELECT 'Users Count' as Info, COUNT(*) as Total FROM users;

-- Xem thông tin employees  
SELECT 'Employees Count' as Info, COUNT(*) as Total FROM employees;

-- Xem thông tin customers
SELECT 'Customers Count' as Info, COUNT(*) as Total FROM customers;

-- Xem thông tin products
SELECT 'Products Count' as Info, COUNT(*) as Total FROM products;

-- Test query phức tạp: Lấy thông tin user và role
SELECT 
    u.username,
    u.full_name,
    u.user_type,
    CASE 
        WHEN u.user_type = 'employee' THEN e.position
        WHEN u.user_type = 'customer' THEN c.membership_level
        ELSE 'Unknown'
    END as role_detail
FROM users u
LEFT JOIN employees e ON u.user_id = e.user_id
LEFT JOIN customers c ON u.user_id = c.user_id
ORDER BY u.user_type, u.username;
