-- Setup script cho Aiven MySQL Database
-- Database: defaultdb (đã có sẵn trên Aiven)
-- User: avnadmin (đã có sẵn trên Aiven)

-- Tạo các bảng cho Shop Management System theo Entity structure
USE defaultdb;

-- Xóa các bảng nếu đã tồn tại (để tránh lỗi foreign key constraint)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS inventory_transactions;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- Bảng LOGIN (chính - theo Entity Login)
CREATE TABLE login (
    userId VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    status INT NOT NULL DEFAULT 1, -- 0: Employee, 1: Customer, 2: Admin
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng CATEGORY (theo Entity Category)
CREATE TABLE category (
    categoryId VARCHAR(20) PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng PRODUCT (theo Entity Product)
CREATE TABLE product (
    productId VARCHAR(20) PRIMARY KEY,
    productName VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    categoryId VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (categoryId) REFERENCES category(categoryId) ON DELETE SET NULL
);

-- Bảng CUSTOMER (theo Entity Customer)
CREATE TABLE customer (
    userId VARCHAR(50) PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phoneNumber VARCHAR(20),
    address TEXT,
    dateOfBirth DATE,
    gender VARCHAR(10),
    loyaltyPoints INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES login(userId) ON DELETE CASCADE
);

-- Bảng EMPLOYEE (theo Entity Employee)
CREATE TABLE employee (
    userId VARCHAR(50) PRIMARY KEY,
    employeeName VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    department VARCHAR(100),
    salary DECIMAL(10,2),
    hireDate DATE,
    phoneNumber VARCHAR(20),
    address TEXT,
    emergencyContact VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES login(userId) ON DELETE CASCADE
);

-- Bảng ORDER (theo Entity)
CREATE TABLE orders (
    orderId VARCHAR(20) PRIMARY KEY,
    customerId VARCHAR(50),
    employeeId VARCHAR(50),
    totalAmount DECIMAL(10,2) NOT NULL,
    discountAmount DECIMAL(10,2) DEFAULT 0,
    taxAmount DECIMAL(10,2) DEFAULT 0,
    finalAmount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    paymentMethod VARCHAR(20) DEFAULT 'CASH',
    paymentStatus VARCHAR(20) DEFAULT 'PENDING',
    notes TEXT,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES customer(userId) ON DELETE SET NULL,
    FOREIGN KEY (employeeId) REFERENCES employee(userId) ON DELETE SET NULL
);

-- Bảng ORDER_ITEM (theo Entity)
CREATE TABLE order_item (
    orderItemId BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderId VARCHAR(20) NOT NULL,
    productId VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(10,2) NOT NULL,
    totalPrice DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES orders(orderId) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(productId) ON DELETE RESTRICT
);

-- Tạo indexes để tối ưu hiệu suất
CREATE INDEX idx_login_userId ON login(userId);
CREATE INDEX idx_product_category ON product(categoryId);
CREATE INDEX idx_customer_userId ON customer(userId);
CREATE INDEX idx_employee_userId ON employee(userId);
CREATE INDEX idx_orders_customerId ON orders(customerId);
CREATE INDEX idx_orders_employeeId ON orders(employeeId);
CREATE INDEX idx_order_item_orderId ON order_item(orderId);
CREATE INDEX idx_order_item_productId ON order_item(productId);

-- Dữ liệu mẫu (theo demo accounts trong login.html)

-- Insert LOGIN accounts với password = 123456 (plain text như demo)
INSERT INTO login (userId, password, status) VALUES 
('admin', '123456', 2),        -- Admin account
('e001', '123456', 0),         -- Employee account  
('c001', '123456', 1);         -- Customer account

-- Insert CATEGORIES
INSERT INTO category (categoryId, categoryName, description) VALUES 
('CAT001', 'Electronics', 'Thiết bị điện tử và công nghệ'),
('CAT002', 'Clothing', 'Quần áo và thời trang'),
('CAT003', 'Books', 'Sách và tài liệu'),
('CAT004', 'Home & Garden', 'Đồ gia dụng và vườn tược'),
('CAT005', 'Sports', 'Thể thao và outdoor');

-- Insert PRODUCTS
INSERT INTO product (productId, productName, description, price, stockQuantity, categoryId) VALUES 
('P001', 'iPhone 15', 'Điện thoại thông minh iPhone 15 128GB', 25000000, 50, 'CAT001'),
('P002', 'Samsung Galaxy S24', 'Điện thoại Samsung Galaxy S24 256GB', 22000000, 30, 'CAT001'),
('P003', 'MacBook Air M2', 'Laptop MacBook Air với chip M2', 28000000, 20, 'CAT001'),
('P004', 'Áo thun polo', 'Áo thun polo nam chất lượng cao', 350000, 100, 'CAT002'),
('P005', 'Quần jeans', 'Quần jeans nam phong cách hiện đại', 650000, 80, 'CAT002'),
('P006', 'Sách lập trình', 'Sách học lập trình Java cơ bản', 200000, 50, 'CAT003'),
('P007', 'Nồi cơm điện', 'Nồi cơm điện Toshiba 1.8L', 1500000, 25, 'CAT004'),
('P008', 'Giày chạy bộ', 'Giày chạy bộ Nike Air Max', 2500000, 40, 'CAT005');

-- Insert CUSTOMER info
INSERT INTO customer (userId, customerName, email, phoneNumber, address, dateOfBirth, gender, loyaltyPoints) VALUES 
('c001', 'Nguyen Van A', 'customer1@example.com', '0901234567', '123 Nguyen Trai, Q1, TP.HCM', '1990-05-15', 'Nam', 500);

-- Insert EMPLOYEE info
INSERT INTO employee (userId, employeeName, role, department, salary, hireDate, phoneNumber, address, emergencyContact) VALUES 
('e001', 'Tran Thi B', 'Sales Staff', 'Sales', 8000000, '2024-01-15', '0907654321', '456 Le Loi, Q3, TP.HCM', '0909999999');

-- Insert sample ORDER
INSERT INTO orders (orderId, customerId, employeeId, totalAmount, discountAmount, taxAmount, finalAmount, status, paymentMethod, paymentStatus, notes) VALUES 
('ORD001', 'c001', 'e001', 25000000, 500000, 2250000, 26750000, 'PENDING', 'CASH', 'PENDING', 'Đơn hàng đầu tiên');

-- Insert sample ORDER ITEMS
INSERT INTO order_item (orderId, productId, quantity, unitPrice, totalPrice) VALUES 
('ORD001', 'P001', 1, 25000000, 25000000);

COMMIT;