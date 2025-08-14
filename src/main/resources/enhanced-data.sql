-- Dữ liệu mẫu cho hệ thống Shop Management

-- Dữ liệu cho bảng login
INSERT INTO login (user_id, password, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTsJCNjVa3xpGPjfp9Hcv8Rv/LCTqI0m', 2), -- password: admin123
('customer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTsJCNjVa3xpGPjfp9Hcv8Rv/LCTqI0m', 1), -- password: admin123
('customer2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTsJCNjVa3xpGPjfp9Hcv8Rv/LCTqI0m', 1), -- password: admin123
('staff1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTsJCNjVa3xpGPjfp9Hcv8Rv/LCTqI0m', 0); -- password: admin123

-- Dữ liệu cho bảng roles
INSERT INTO roles (role_name, role_description) VALUES 
('ADMIN', 'Quản trị viên hệ thống - toàn quyền'),
('CUSTOMER', 'Khách hàng - mua hàng online'),
('STAFF', 'Nhân viên bán hàng'),
('MANAGER', 'Quản lý cửa hàng');

-- Dữ liệu cho bảng user_roles
INSERT INTO user_roles (user_id, role_id, assigned_by) VALUES 
('admin', 1, 'system'),
('customer1', 2, 'admin'),
('customer2', 2, 'admin'),
('staff1', 3, 'admin');

-- Dữ liệu cho bảng categories
INSERT INTO categories (category_name, description, is_active, display_order) VALUES 
('Thực phẩm tươi sống', 'Rau củ quả, thịt cá, trứng sữa tươi ngon', true, 1),
('Đồ uống', 'Nước ngọt, bia, rượu, trà, cà phê', true, 2),
('Bánh kẹo', 'Bánh ngọt, kẹo các loại, snack', true, 3),
('Gia vị', 'Muối, đường, nước mắm, các loại gia vị', true, 4),
('Đồ dùng sinh hoạt', 'Xà phòng, kem đánh răng, giấy vệ sinh', true, 5),
('Thiết bị gia dụng', 'Nồi niêu, chảo, bát đĩa', true, 6);

-- Dữ liệu cho bảng product
INSERT INTO product (product_name, category_id, price, quantity, description, brand, supplier, barcode) VALUES 
-- Thực phẩm tươi sống
('Thịt heo ba chỉ', 1, 180000, 25, 'Thịt heo ba chỉ tươi ngon, không hóa chất', 'CP', 'CP Việt Nam', '1234567890123'),
('Cá thu tươi', 1, 220000, 15, 'Cá thu tươi ngon, đánh bắt trong ngày', 'Hải Sản Việt', 'Ngư dân Vũng Tàu', '1234567890124'),
('Rau cải ngọt', 1, 15000, 50, 'Rau cải ngọt organic, không thuốc trừ sâu', 'Organik', 'Nông trại Đà Lạt', '1234567890125'),
('Trứng gà ta', 1, 35000, 40, 'Trứng gà ta thả vườn, giàu dinh dưỡng', 'Gà Đồng Tâm', 'Trang trại Đồng Tâm', '1234567890126'),

-- Đồ uống
('Coca Cola', 2, 12000, 100, 'Nước ngọt Coca Cola 330ml', 'Coca Cola', 'Công ty Coca Cola VN', '1234567890127'),
('Bia Saigon', 2, 18000, 80, 'Bia Saigon lon 330ml', 'Sabeco', 'Sabeco', '1234567890128'),
('Trà xanh C2', 2, 8000, 120, 'Trà xanh C2 chai 455ml', 'URC', 'URC Việt Nam', '1234567890129'),
('Nước suối Lavie', 2, 5000, 200, 'Nước suối tinh khiết Lavie 500ml', 'Lavie', 'La Vie', '1234567890130'),

-- Bánh kẹo
('Bánh quy Cosy', 3, 25000, 60, 'Bánh quy bơ sữa Cosy hộp 408g', 'Cosy', 'Mondelez', '1234567890131'),
('Kẹo dẻo Haribo', 3, 45000, 30, 'Kẹo dẻo trái cây Haribo 100g', 'Haribo', 'Haribo Đức', '1234567890132'),
('Snack Oishi', 3, 15000, 80, 'Snack khoai tây vị tôm chua ngọt', 'Oishi', 'Liwayway', '1234567890133'),

-- Gia vị
('Nước mắm Nam Ngư', 4, 28000, 45, 'Nước mắm truyền thống Nam Ngư 500ml', 'Nam Ngư', 'Nam Ngư', '1234567890134'),
('Đường trắng Biên Hòa', 4, 22000, 35, 'Đường trắng tinh luyện Biên Hòa 1kg', 'Biên Hòa', 'Biên Hòa Sugar', '1234567890135'),
('Muối Tây Ninh', 4, 8000, 60, 'Muối tinh Tây Ninh gói 500g', 'Tây Ninh', 'Visalco', '1234567890136'),

-- Đồ dùng sinh hoạt
('Xà phòng Lifebuoy', 5, 15000, 70, 'Xà phòng diệt khuẩn Lifebuoy 90g', 'Lifebuoy', 'Unilever', '1234567890137'),
('Kem đánh răng PS', 5, 25000, 50, 'Kem đánh răng P/S bảo vệ nướu 200g', 'P/S', 'Unilever', '1234567890138'),
('Giấy vệ sinh Saigon', 5, 35000, 40, 'Giấy vệ sinh Saigon 12 cuộn', 'Saigon', 'SCA Hygiene', '1234567890139'),

-- Thiết bị gia dụng
('Nồi cơm điện Sharp', 6, 1200000, 10, 'Nồi cơm điện Sharp 1.8L KS-COM180V', 'Sharp', 'Sharp Việt Nam', '1234567890140'),
('Chảo chống dính', 6, 350000, 20, 'Chảo chống dính cao cấp 24cm', 'Lock&Lock', 'Lock&Lock', '1234567890141'),
('Bộ đũa inox', 6, 45000, 35, 'Bộ 5 đôi đũa inox cao cấp', 'Inox Việt', 'Inox Việt Nam', '1234567890142');

-- Dữ liệu cho bảng customer
INSERT INTO customer (user_id, customer_name, email, phone_number, address) VALUES 
('customer1', 'Nguyễn Văn An', 'nguyenvanan@email.com', '0901234567', 'Số 123, Đường ABC, Quận 1, TP.HCM'),
('customer2', 'Trần Thị Bình', 'tranthibinh@email.com', '0907654321', 'Số 456, Đường XYZ, Quận 7, TP.HCM');

-- Dữ liệu cho bảng cart (giỏ hàng mẫu)
INSERT INTO cart (customer_id, product_id, quantity, price) VALUES 
('customer1', 1, 2, 180000),
('customer1', 5, 1, 12000),
('customer2', 3, 3, 15000),
('customer2', 8, 2, 5000);

-- Dữ liệu cho bảng wishlist (danh sách yêu thích mẫu)
INSERT INTO wishlist (customer_id, product_id) VALUES 
('customer1', 2),
('customer1', 6),
('customer1', 10),
('customer2', 1),
('customer2', 7),
('customer2', 15);

-- Dữ liệu cho bảng orders (đơn hàng mẫu)
INSERT INTO orders (customer_id, total_amount, status, shipping_address, payment_method) VALUES 
('customer1', 392000, 'DELIVERED', 'Số 123, Đường ABC, Quận 1, TP.HCM', 'CASH'),
('customer2', 55000, 'PENDING', 'Số 456, Đường XYZ, Quận 7, TP.HCM', 'BANK_TRANSFER');

-- Dữ liệu cho bảng product_reviews (đánh giá sản phẩm)
INSERT INTO product_reviews (product_id, customer_id, rating, comment, is_verified_purchase) VALUES 
(1, 'customer1', 5, 'Thịt tươi ngon, chất lượng rất tốt!', true),
(5, 'customer1', 4, 'Coca Cola vẫn ngon như mọi khi', true),
(3, 'customer2', 5, 'Rau cải rất tươi xanh, organic thật', true),
(7, 'customer2', 4, 'Trà xanh C2 giải khát tốt', false);
