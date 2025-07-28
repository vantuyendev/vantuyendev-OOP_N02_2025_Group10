# 🏪 Hệ Thống Quản Lý Cửa Hàng - Ứng Dụng Desktop

Ứng dụng Spring Boot desktop hiện đại để quản lý hoạt động cửa hàng với cơ sở dữ liệu MySQL, giao diện Swing và các tính năng quản lý kinh doanh toàn diện.

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)
![Swing](https://img.shields.io/badge/GUI-Swing-blue.svg)
![FlatLaf](https://img.shields.io/badge/Theme-FlatLaf-purple.svg)
![Status](https://img.shields.io/badge/Status-Working-green.svg)

## 🚀 Bắt Đầu Nhanh

### Yêu Cầu Hệ Thống

- Java 11 trở lên
- MySQL 8.0+
- Maven 3.6+

### Chạy Ứng Dụng Desktop

#### Phương Pháp 1: Maven (Khuyến Nghị)

```bash
# Khởi động dịch vụ MySQL
sudo service mysql start

# Tạo cơ sở dữ liệu và import dữ liệu mẫu
mysql -u root -e "CREATE DATABASE IF NOT EXISTS shopmanagement;"
mysql -u root shopmanagement < sql/shopmanagement.sql

# Dọn dẹp và biên dịch
mvn clean compile

# Chạy ứng dụng desktop
mvn spring-boot:run
```

#### Phương Pháp 2: VS Code Tasks

1. Nhấn `Ctrl+Shift+P`
2. Gõ "Tasks: Run Task"  
3. Chọn "Build Spring Boot App" hoặc "Run Spring Boot App"

#### Phương Pháp 3: File JAR

```bash
# Build file JAR
mvn clean package

# Chạy file JAR
java -jar target/shop-management-system-1.0.0.jar
```

### Tính Năng Ứng Dụng

- **Giao Diện Desktop**: Swing hiện đại với chủ đề FlatLaf
- **Quản Lý Người Dùng**: Xác thực nhân viên và khách hàng
- **Quản Lý Sản Phẩm**: Các thao tác CRUD cho kho hàng
- **Hỗ Trợ Chủ Đề**: Nhiều màu sắc và giao diện hiện đại
- **Tích Hợp Cơ Sở Dữ Liệu**: Đồng bộ MySQL thời gian thực

## 🔐 Thông Tin Đăng Nhập

### Tài Khoản Mặc Định

| Vai Trò | Tên Đăng Nhập | Mật Khẩu | Mô Tả |
|---------|---------------|----------|-------|
| **Quản Trị** | `admin` | `123456` | Quản trị hệ thống với quyền truy cập đầy đủ |
| **Quản Lý** | `e001` | `123456` | Quản lý bán hàng với quyền quản lý nhân viên |
| **Thu Ngân** | `e002` | `123456` | Nhân viên bán hàng với các thao tác cơ bản |
| **Khách Hàng** | `c001` | `123456` | Khách hàng thành viên bạc |
| **Khách Hàng** | `c002` | `123456` | Khách hàng thành viên vàng |

## 📊 Quản Lý Cơ Sở Dữ Liệu

### Cấu Hình Cơ Sở Dữ Liệu

- **Host**: localhost:3306
- **Cơ sở dữ liệu**: `shopmanagement`
- **Người dùng**: `root` (không có mật khẩu)
- **Bảng**: `users`, `employees`, `customers`, `products`

### Thiết Lập Cơ Sở Dữ Liệu

```bash
# Khởi động dịch vụ MySQL
sudo service mysql start

# Tạo cơ sở dữ liệu
mysql -u root -e "CREATE DATABASE IF NOT EXISTS shopmanagement;"

# Import dữ liệu mẫu
mysql -u root shopmanagement < sql/shopmanagement.sql
```

## 📂 Cấu Trúc Dự Án

```text
shop-management-system/
├── src/main/java/com/shopmanagement/     # Mã nguồn Java
│   ├── Start.java                        # Điểm khởi động ứng dụng Spring Boot
│   ├── activity/                         # Các Activity giao diện Swing
│   │   ├── LoginActivity.java           # Màn hình xác thực người dùng
│   │   ├── DashboardActivity.java       # Giao diện dashboard chính
│   │   ├── ManageEmployee.java          # Giao diện quản lý nhân viên
│   │   ├── ManageProduct.java           # Giao diện quản lý sản phẩm
│   │   ├── ManageCustomer.java          # Giao diện quản lý khách hàng
│   │   └── ThemeSettingsActivity.java   # Giao diện tùy chỉnh chủ đề
│   ├── entity/                          # Các lớp Entity JPA
│   │   ├── Employee.java                # Entity cơ sở dữ liệu nhân viên
│   │   ├── Customer.java                # Entity cơ sở dữ liệu khách hàng
│   │   ├── Product.java                 # Entity cơ sở dữ liệu sản phẩm
│   │   └── Login.java                   # Entity xác thực người dùng
│   ├── model/                           # Các mô hình dữ liệu và DTO
│   │   ├── User.java                    # Lớp mô hình người dùng
│   │   ├── Employee.java                # Mô hình nhân viên
│   │   ├── Customer.java                # Mô hình khách hàng
│   │   └── Product.java                 # Mô hình sản phẩm
│   ├── repository/                      # Các repository Spring Data JPA
│   │   ├── EmployeeRepository.java      # Truy cập dữ liệu nhân viên
│   │   ├── CustomerRepository.java      # Truy cập dữ liệu khách hàng
│   │   ├── ProductRepository.java       # Truy cập dữ liệu sản phẩm
│   │   └── LoginRepository.java         # Truy cập dữ liệu xác thực
│   ├── service/                         # Các dịch vụ logic nghiệp vụ
│   │   ├── EmployeeService.java         # Logic nghiệp vụ nhân viên
│   │   ├── CustomerService.java         # Logic nghiệp vụ khách hàng
│   │   ├── ProductService.java          # Logic nghiệp vụ sản phẩm
│   │   └── LoginService.java            # Dịch vụ xác thực
│   ├── config/                          # Các lớp cấu hình
│   │   └── DesktopConfiguration.java    # Cấu hình ứng dụng desktop
│   └── util/                            # Các lớp tiện ích
│       ├── Database.java                # Tiện ích kết nối cơ sở dữ liệu
│       ├── DesktopUtils.java            # Tiện ích giao diện desktop
│       └── ModernCard.java              # Các thành phần UI tùy chỉnh
├── src/main/resources/                   # Tài nguyên ứng dụng
│   └── application.properties           # Cấu hình Spring Boot
├── sql/                                 # Các script cơ sở dữ liệu
│   ├── shopmanagement.sql              # Schema cơ sở dữ liệu và dữ liệu mẫu
│   └── test_connection.sql             # Test kết nối cơ sở dữ liệu
├── target/classes/                      # Các lớp Java đã biên dịch
├── pom.xml                              # Cấu hình Maven
└── README.md                            # Tài liệu này
```

## 🛠️ Các Lệnh Có Sẵn

### Lệnh Maven

```bash
# Build ứng dụng
mvn clean compile

# Chạy ứng dụng desktop
mvn spring-boot:run

# Đóng gói thành file JAR
mvn clean package

# Chạy test
mvn test
```

### VS Code Tasks

- **Build Spring Boot App**: Biên dịch mã nguồn
- **Run Spring Boot App**: Khởi chạy ứng dụng desktop
- **Test Spring Boot App**: Chạy unit test
- **Package Spring Boot App**: Tạo file JAR
- **Clean Spring Boot App**: Dọn dẹp file build

## 🎯 Tính Năng

### Khả Năng Ứng Dụng

**Dành Cho Quản Lý:**

- ✅ Quản lý nhân viên đầy đủ (thêm, sửa, xóa, xem)
- ✅ Quản lý khách hàng và theo dõi thành viên
- ✅ Quản lý catalog sản phẩm hoàn chỉnh
- ✅ Báo cáo bán hàng và phân tích
- ✅ Quản trị hệ thống

**Dành Cho Nhân Viên (Thu Ngân):**

- ✅ Bán hàng và thanh toán sản phẩm
- ✅ Tra cứu và xác minh khách hàng
- ✅ Xem kho hàng cơ bản
- ✅ Xử lý giao dịch

**Dành Cho Khách Hàng:**

- ✅ Duyệt và tìm kiếm sản phẩm
- ✅ Xem lịch sử mua hàng
- ✅ Quản lý hồ sơ
- ✅ Theo dõi lợi ích thành viên

**Tính Năng Hệ Thống:**

- ✅ Hệ thống xác thực đa người dùng
- ✅ Giao diện Swing hiện đại với chủ đề FlatLaf
- ✅ Đồng bộ cơ sở dữ liệu thời gian thực
- ✅ Dependency injection Spring Boot
- ✅ Xử lý lỗi toàn diện

## 🧪 Kiểm Thử

### Kiểm Thử Kết Nối Cơ Sở Dữ Liệu

```bash
# Test kết nối MySQL
mysql -u root -e "SELECT 'Kết nối cơ sở dữ liệu thành công' as Status;"

# Test cơ sở dữ liệu ứng dụng
mysql -u root shopmanagement -e "SHOW TABLES;"
```

### Kiểm Thử Ứng Dụng

1. **Kiểm Thử Đăng Nhập**: Test tất cả vai trò người dùng với thông tin đăng nhập được cung cấp
2. **Thao Tác CRUD**: Test tạo, đọc, cập nhật, xóa cho tất cả các entity
3. **Tính Toàn Vẹn Cơ Sở Dữ Liệu**: Xác minh các ràng buộc khóa ngoại và tính nhất quán dữ liệu
4. **Chức Năng GUI**: Test tất cả các thành phần UI và tương tác người dùng

## 🔧 Môi Trường Phát Triển

### Yêu Cầu Môi Trường Phát Triển

- **Java**: 11+ (Khuyến nghị OpenJDK)
- **MySQL**: 8.0+
- **Hệ điều hành**: Linux (Ubuntu 20.04+), Windows, macOS
- **Bộ nhớ**: Khuyến nghị 2GB+ RAM

### Cấu Hình IDE

- **VS Code** với Java Extension Pack
- **Spring Boot** extension để phát triển nâng cao
- **MySQL** tools để quản lý cơ sở dữ liệu

## 🚨 Khắc Phục Sự Cố

### Các Vấn Đề Thường Gặp

#### Kết Nối Cơ Sở Dữ Liệu Thất Bại

- ✅ Khởi động MySQL: `sudo service mysql start`
- ✅ Kiểm tra cơ sở dữ liệu tồn tại: `mysql -u root -e "SHOW DATABASES;"`
- ✅ Tạo lại cơ sở dữ liệu: `mysql -u root shopmanagement < sql/shopmanagement.sql`

#### Java ClassNotFoundException

- ✅ Build lại ứng dụng: `mvn clean compile`
- ✅ Kiểm tra dependencies Maven: `mvn dependency:tree`
- ✅ Xác minh phiên bản Java: `java -version`

#### GUI Không Hiển Thị

- ✅ Kiểm tra log Spring Boot để tìm lỗi
- ✅ Xác minh dependency FlatLaf trong pom.xml
- ✅ Đảm bảo Java được cấu hình cho ứng dụng desktop

### Lệnh Debug

```bash
# Kiểm tra trạng thái MySQL
sudo service mysql status

# Xem log ứng dụng
mvn spring-boot:run

# Test kết nối cơ sở dữ liệu
mysql -u root shopmanagement -e "SELECT COUNT(*) FROM users;"
```

## 📚 Tài Liệu

### Các File Tài Liệu Cốt Lõi

- `README.md` - Hướng dẫn toàn diện (tiếng Anh)
- `README_VI.md` - Hướng dẫn này (tiếng Việt)
- `pom.xml` - Cấu hình dự án Maven
- `sql/shopmanagement.sql` - Schema cơ sở dữ liệu và dữ liệu mẫu
- `sql/test_connection.sql` - Các truy vấn test kết nối cơ sở dữ liệu

### Công Nghệ Sử Dụng

- **Backend**: Spring Boot 2.7.18 với Spring Data JPA
- **Cơ sở dữ liệu**: MySQL 8.0+ với schema tự động tạo
- **GUI**: Java Swing với chủ đề FlatLaf hiện đại
- **Công cụ Build**: Maven 3.6+ để quản lý dependency
- **Kiến trúc**: Mô hình MVC với thiết kế phân lớp
