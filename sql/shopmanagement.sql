-- Shop Management Database Schema
USE shopmanagement;

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(10) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    user_type ENUM('employee', 'customer') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Employees table
CREATE TABLE IF NOT EXISTS employees (
    employee_id VARCHAR(10) PRIMARY KEY,
    user_id VARCHAR(10),
    position VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    department VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id VARCHAR(10) PRIMARY KEY,
    user_id VARCHAR(10),
    address TEXT,
    membership_level ENUM('Bronze', 'Silver', 'Gold', 'Platinum') DEFAULT 'Bronze',
    total_purchases DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Products table
CREATE TABLE IF NOT EXISTS products (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    quantity_in_stock INT DEFAULT 0,
    category VARCHAR(50),
    supplier VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample users
INSERT IGNORE INTO users VALUES
('u001', 'admin', '123456', 'System Administrator', 'admin@shop.com', '0123456789', 'employee', NOW()),
('u002', 'e001', '123456', 'Nguyen Van A', 'nvana@shop.com', '0987654321', 'employee', NOW()),
('u003', 'e002', '123456', 'Tran Thi B', 'ttb@shop.com', '0912345678', 'employee', NOW()),
('u004', 'c001', '123456', 'Le Van C', 'lvc@gmail.com', '0901234567', 'customer', NOW()),
('u005', 'c002', '123456', 'Pham Thi D', 'ptd@gmail.com', '0898765432', 'customer', NOW());

-- Insert sample employees
INSERT IGNORE INTO employees VALUES
('e001', 'u002', 'Sales Manager', 15000000, '2023-01-15', 'Sales'),
('e002', 'u003', 'Cashier', 8000000, '2023-03-20', 'Sales');

-- Insert sample customers
INSERT IGNORE INTO customers VALUES
('c001', 'u004', '123 Nguyen Trai, Q1, TPHCM', 'Silver', 5000000),
('c002', 'u005', '456 Le Loi, Q3, TPHCM', 'Gold', 12000000);

-- Insert sample products
INSERT IGNORE INTO products VALUES
('p001', 'iPhone 15 Pro', 'Latest iPhone with A17 Pro chip', 29990000, 50, 'Electronics', 'Apple Inc.', NOW()),
('p002', 'Samsung Galaxy S24', 'Premium Android smartphone', 22990000, 30, 'Electronics', 'Samsung', NOW()),
('p003', 'MacBook Air M3', 'Lightweight laptop with M3 chip', 34990000, 20, 'Computers', 'Apple Inc.', NOW()),
('p004', 'Dell XPS 13', 'Ultrabook with Intel Core i7', 25990000, 15, 'Computers', 'Dell', NOW()),
('p005', 'Sony WH-1000XM5', 'Noise canceling headphones', 7990000, 100, 'Audio', 'Sony', NOW());

-- Show tables and data
SHOW TABLES;
SELECT 'Users:' as Info;
SELECT * FROM users;
SELECT 'Employees:' as Info;
SELECT * FROM employees;
SELECT 'Customers:' as Info;
SELECT * FROM customers;
SELECT 'Products:' as Info;
SELECT * FROM products;
