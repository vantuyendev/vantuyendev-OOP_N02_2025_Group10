SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";



-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer` (khách hàng)
--

CREATE TABLE `customer` (
  `userId` varchar(12) NOT NULL,
  `customerName` varchar(30) NOT NULL,
  `phoneNumber` varchar(14) NOT NULL,
  `address` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dữ liệu mẫu cho bảng `customer`
--

INSERT INTO `customer` (`userId`, `customerName`, `phoneNumber`, `address`) VALUES
('c001', 'Tuong Van Tuyen', '0123456789', 'Ha Noi'),
('c002', 'Tran Thi Lan', '0912345678', 'Da Nang'),
('c003', 'Le Hoang Nam', '0974856123', 'Can Tho'),
('c004', 'Pham Thi Hoa', '0938572146', 'Hai Phong');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee` (nhân viên)
--

CREATE TABLE `employee` (
  `userId` varchar(12) NOT NULL,
  `employeeName` varchar(50) NOT NULL,
  `phoneNumber` varchar(14) NOT NULL,
  `role` varchar(8) NOT NULL,
  `salary` double(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dữ liệu mẫu cho bảng `employee`
--

INSERT INTO `employee` (`userId`, `employeeName`, `phoneNumber`, `role`, `salary`) VALUES
('e001', 'Hoang Minh Duc', '0901234567', 'Manager', 75000.00),
('e002', 'Nguyen Thu Hien', '0912345678', 'General', 45000.00),
('e003', 'Tran Van Khai', '0923456789', 'General', 42000.00),
('e004', 'Le Thi Mai', '0934567890', 'Manager', 78000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `login` (thông tin đăng nhập)
--

CREATE TABLE `login` (
  `userId` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dữ liệu mẫu cho bảng `login`
--

INSERT INTO `login` (`userId`, `password`, `status`) VALUES
('e001', 'e001', 0),
('e002', 'e002', 0),
('e003', 'e003', 0),
('e004', 'e004', 0),
('c001', 'c001', 1),
('c002', 'c002', 1),
('c003', 'c003', 1),
('c004', 'c004', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product` (sản phẩm)
--

CREATE TABLE `product` (
  `productId` int(5) UNSIGNED ZEROFILL NOT NULL,
  `productName` varchar(50) NOT NULL,
  `price` double(8,2) NOT NULL,
  `quantity` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dữ liệu mẫu cho bảng `product`
--

INSERT INTO `product` (`productId`, `productName`, `price`, `quantity`) VALUES
(00001, 'Laptop Dell XPS 13', 1850.00, 12),
(00002, 'Smartphone iPhone 15', 1200.00, 8),
(00003, 'Wireless Headphones', 150.00, 25),
(00004, 'Gaming Mouse RGB', 85.00, 35),
(00005, 'Mechanical Keyboard', 120.00, 18),
(00006, 'Monitor 4K 27 inch', 450.00, 10),
(00007, 'USB-C Hub', 65.00, 40),
(00008, 'Webcam HD 1080p', 95.00, 22);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `purchaseinfo` (thông tin mua hàng)
--

CREATE TABLE `purchaseinfo` (
  `purchaseId` int(5) UNSIGNED ZEROFILL NOT NULL,
  `userId` varchar(12) NOT NULL,
  `productId` int(5) UNSIGNED ZEROFILL NOT NULL,
  `cost` double(12,2) UNSIGNED NOT NULL,
  `amount` int(8) UNSIGNED NOT NULL,
  `date` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dữ liệu mẫu cho bảng `purchaseinfo`
--

INSERT INTO `purchaseinfo` (`purchaseId`, `userId`, `productId`, `cost`, `amount`, `date`) VALUES
(00001, 'c001', 00003, 150.00, 1, '2025-07-15'),
(00002, 'c002', 00005, 120.00, 1, '2025-07-16'),
(00003, 'c003', 00001, 1850.00, 1, '2025-07-17'),
(00004, 'c001', 00004, 170.00, 2, '2025-07-18'),
(00005, 'c004', 00002, 1200.00, 1, '2025-07-18');

--
-- Chỉ mục cho các bảng đã tạo
--

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`userId`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`userId`);

--
-- Chỉ mục cho bảng `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`userId`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productId`);

--
-- Chỉ mục cho bảng `purchaseinfo`
--
ALTER TABLE `purchaseinfo`
  ADD PRIMARY KEY (`purchaseId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `productId` (`productId`);

--
-- Tự động tăng cho các bảng đã tạo
--

--
-- Tự động tăng cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `productId` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Tự động tăng cho bảng `purchaseinfo`
--
ALTER TABLE `purchaseinfo`
  MODIFY `purchaseId` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Ràng buộc khóa ngoại cho các bảng đã tạo
--

--
-- Ràng buộc khóa ngoại cho bảng `purchaseinfo`
--
ALTER TABLE `purchaseinfo`
  ADD CONSTRAINT `FK_PUR_CUS` FOREIGN KEY (`userId`) REFERENCES `customer` (`userId`),
  ADD CONSTRAINT `FK_PUR_PRO` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`);

--
-- Ràng buộc khóa ngoại cho bảng `login`
--
-- Lưu ý: Bảng login tham chiếu đến cả bảng customer và employee thông qua userId
-- Ràng buộc này không thể thực thi với khóa ngoại đơn giản do có nhiều bảng cha

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
