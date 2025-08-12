-- Data initialization cho H2 Database
-- Dữ liệu demo cho Shop Management System

-- Insert LOGIN accounts với password = 123456 (theo demo accounts trong login.html)
INSERT INTO login (user_id, password, status, created_at, updated_at) VALUES 
('admin', '123456', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),        -- Admin account
('e001', '123456', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),         -- Employee account  
('c001', '123456', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);         -- Customer account

-- Insert CATEGORIES
INSERT INTO category (category_id, category_name, description, created_at, updated_at) VALUES 
('CAT001', 'Điện tử', 'Thiết bị điện tử và công nghệ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CAT002', 'Thời trang', 'Quần áo và phụ kiện thời trang', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CAT003', 'Sách', 'Sách và tài liệu giáo dục', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CAT004', 'Gia dụng', 'Đồ gia dụng và nội thất', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CAT005', 'Thể thao', 'Dụng cụ thể thao và outdoor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert PRODUCTS
INSERT INTO product (product_name, category, price, quantity, description, brand, supplier, barcode, created_at, updated_at) VALUES 
('iPhone 15', 'Điện tử', 25000000, 50, 'Điện thoại thông minh iPhone 15 128GB', 'Apple', 'FPT Shop', 'IP15128GB', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Samsung Galaxy S24', 'Điện tử', 22000000, 30, 'Điện thoại Samsung Galaxy S24 256GB', 'Samsung', 'CellphoneS', 'SGS24256GB', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('MacBook Air M2', 'Điện tử', 28000000, 20, 'Laptop MacBook Air với chip M2', 'Apple', 'FPT Shop', 'MBAM2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Áo thun polo', 'Thời trang', 350000, 100, 'Áo thun polo nam chất lượng cao', 'Uniqlo', 'Uniqlo VN', 'POLO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Quần jeans', 'Thời trang', 650000, 80, 'Quần jeans nam phong cách hiện đại', 'Levis', 'Levis Store', 'JEANS001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sách lập trình Java', 'Sách', 200000, 50, 'Sách học lập trình Java từ cơ bản đến nâng cao', 'NXB Trẻ', 'Fahasa', 'JAVA001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nồi cơm điện', 'Gia dụng', 1500000, 25, 'Nồi cơm điện Toshiba 1.8L', 'Toshiba', 'Điện máy XANH', 'RICE001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Giày chạy bộ Nike', 'Thể thao', 2500000, 40, 'Giày chạy bộ Nike Air Max', 'Nike', 'Nike Store', 'NIKE001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert CUSTOMER info
INSERT INTO customer (user_id, customer_name, email, phone_number, address, date_of_birth, gender, loyalty_points, created_at, updated_at) VALUES 
('c001', 'Nguyen Van A', 'customer1@example.com', '0901234567', '123 Nguyen Trai, Q1, TP.HCM', '1990-05-15', 'Nam', 500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert EMPLOYEE info
INSERT INTO employee (user_id, employee_name, role, department, salary, hire_date, phone_number, address, emergency_contact, created_at, updated_at) VALUES 
('e001', 'Tran Thi B', 'Nhân viên bán hàng', 'Bán hàng', 8000000, '2024-01-15', '0907654321', '456 Le Loi, Q3, TP.HCM', '0909999999', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample ORDER
INSERT INTO orders (order_id, customer_id, employee_id, total_amount, discount_amount, tax_amount, final_amount, status, payment_method, payment_status, notes, order_date, updated_at) VALUES 
('ORD001', 'c001', 'e001', 25000000, 500000, 2250000, 26750000, 'PENDING', 'CASH', 'PENDING', 'Đơn hàng đầu tiên', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample ORDER ITEMS
INSERT INTO order_item (order_id, product_id, quantity, unit_price, total_price, created_at) VALUES 
('ORD001', 1, 1, 25000000, 25000000, CURRENT_TIMESTAMP);
