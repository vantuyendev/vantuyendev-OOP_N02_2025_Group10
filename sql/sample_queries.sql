-- Sample SQL Queries for ShopManagement Database

-- 1. View all users
SELECT * FROM users;

-- 2. View employees with their details
SELECT 
    e.employee_id,
    u.full_name,
    e.position,
    e.salary,
    e.department
FROM employees e
JOIN users u ON e.user_id = u.user_id;

-- 3. View customers with membership info
SELECT 
    c.customer_id,
    u.full_name,
    c.membership_level,
    c.total_purchases,
    c.address
FROM customers c
JOIN users u ON c.user_id = u.user_id;

-- 4. View products by category
SELECT 
    category,
    COUNT(*) as product_count,
    AVG(price) as avg_price
FROM products
GROUP BY category;

-- 5. Find expensive products (>20M VND)
SELECT 
    product_name,
    price,
    quantity_in_stock,
    supplier
FROM products
WHERE price > 20000000
ORDER BY price DESC;

-- 6. Login validation query (for application)
SELECT 
    u.user_id,
    u.username,
    u.full_name,
    u.user_type
FROM users u
WHERE u.username = 'e001' AND u.password = '123456';

-- 7. Update product quantity
UPDATE products 
SET quantity_in_stock = quantity_in_stock - 1 
WHERE product_id = 'p001';

-- 8. Insert new customer
INSERT INTO users (user_id, username, password, full_name, email, phone, user_type)
VALUES ('u006', 'c003', '123456', 'Hoang Van E', 'hve@gmail.com', '0123456789', 'customer');

INSERT INTO customers (customer_id, user_id, address, membership_level)
VALUES ('c003', 'u006', '789 Vo Van Tan, Q3, TPHCM', 'Bronze');
