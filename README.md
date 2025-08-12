# 🏪 Hệ Thống Quản Lý Cửa Hàng - Ứng Dụng Tích Hợp Web & Desktop

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0%20Aiven%20Cloud-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)
![Gradle](https://img.shields.io/badge/Gradle-7.6+-green.svg)

Đây là hệ thống quản lý cửa hàng toàn diện được xây dựng bằng Spring Boot với tích hợp cloud database Aiven MySQL, hỗ trợ cả giao diện web hiện đại và ứng dụng desktop GUI truyền thống trong cùng một ứng dụng.

## 📋 Giới Thiệu Dự Án

Hệ thống quản lý cửa hàng được thiết kế để giúp các chủ cửa hàng quản lý hoạt động kinh doanh một cách hiệu quả. Dự án cung cấp hai giao diện sử dụng:

- **🌐 Giao diện Web**: Truy cập từ bất kỳ thiết bị nào có trình duyệt với tính năng responsive
- **🖥️ Ứng dụng Desktop**: Giao diện Swing với tính năng đầy đủ
- **☁️ Cloud Database**: Tích hợp Aiven MySQL cho hiệu suất cao và bảo mật
- **🔒 Role-based Access**: Hệ thống phân quyền Admin/Employee/Customer
- **📊 Real-time Analytics**: Dashboard thống kê và báo cáo trực quan
- **🌍 Multi-language**: Hỗ trợ tiếng Việt và tiếng Anh

### 🎯 Mục Tiêu Dự Án
- Quản lý toàn diện sản phẩm, khách hàng, nhân viên và đơn hàng
- Đồng bộ dữ liệu real-time với cloud database
- Cung cấp dashboard analytics và reporting
- Đảm bảo bảo mật và scalability cho production
- Giao diện thân thiện và dễ sử dụng

## ✨ Tính Năng Chính

### 🌐 Giao Diện Web
- **📊 Dashboard Tổng Quan**: Thống kê số lượng nhân viên, sản phẩm, khách hàng
- **👥 Quản Lý Nhân Viên**: Xem danh sách nhân viên với thông tin chi tiết
- **📦 Quản Lý Sản Phẩm**: Hiển thị catalog sản phẩm, giá cả và tồn kho
- **🏪 Quản Lý Khách Hàng**: Thông tin khách hàng và lịch sử giao dịch
- **📱 Thiết Kế Responsive**: Giao diện Bootstrap 5 hiện đại, tương thích mọi thiết bị

### 🖥️ Ứng Dụng Desktop
- **🎨 Giao Diện Swing Modern**: Sử dụng FlatLaf Look and Feel
- **✏️ Thao Tác CRUD Đầy Đủ**: Thêm, sửa, xóa nhân viên, sản phẩm, khách hàng
- **🔐 Hệ Thống Đăng Nhập**: Xác thực an toàn và phân quyền người dùng
- **🗄️ Tích Hợp Database**: Kết nối trực tiếp với MySQL
- **⚙️ Cài Đặt Giao Diện**: Tùy chỉnh theme và cá nhân hóa

## 🛠️ Công Nghệ Sử Dụng

| Thành phần | Công nghệ | Phiên bản |
|------------|-----------|-----------|
| **Backend Framework** | Spring Boot | 2.7.18 |
| **Web Frontend** | Thymeleaf + Bootstrap | 5.3.0 |
| **Desktop GUI** | Java Swing + FlatLaf | 3.2.5 |
| **Database** | MySQL + JPA/Hibernate | 8.0+ |
| **Build Tool** | Maven | 3.6+ |
| **Java Version** | OpenJDK | 11+ |

## 📁 Cấu Trúc Dự Án (Đã Tái Cấu Trúc)

```
📁 Shop Management System/
├── 📄 README.md                          # Tài liệu hướng dẫn
├── 📄 pom.xml                            # Cấu hình Maven chính
├── 📄 start-web.sh                       # 🚀 Khởi chạy nhanh web
├── 📄 start-desktop.sh                   # 🚀 Khởi chạy nhanh desktop
├── 📁 integrated-app/                    # 📱 Ứng dụng tích hợp chính
│   ├── 📄 pom.xml                        # Maven configuration
│   ├── 📁 src/main/java/com/shopmanagement/
│   │   ├── 📄 ShopManagementApplication.java    # Entry point chính
│   │   ├── � desktop/                   # 🖥️ Desktop Components
│   │   │   ├── 📁 config/               # Desktop configuration
│   │   │   │   └── � DesktopConfiguration.java
│   │   │   └── 📁 ui/                   # Modern Swing UI
│   │   │       ├── 📄 LoginFrame.java   # Giao diện đăng nhập
│   │   │       ├── 📄 DashboardFrame.java # Dashboard chính
│   │   │       ├── 📄 EmployeeFrame.java # Quản lý nhân viên
│   │   │       ├── 📄 ProductFrame.java # Quản lý sản phẩm
│   │   │       └── 📄 CustomerFrame.java # Quản lý khách hàng
│   │   ├── 📁 web/                      # 🌐 Web Components
│   │   │   ├── 📁 config/               # Web configuration
│   │   │   │   └── 📄 WebConfiguration.java
│   │   │   └── 📁 controller/           # Web Controllers
│   │   │       └── 📄 ShopWebController.java
│   │   ├── 📁 entity/                   # 🗄️ Database Entities
│   │   │   ├── 📄 Employee.java         # Entity nhân viên
│   │   │   ├── 📄 Product.java          # Entity sản phẩm
│   │   │   ├── 📄 Customer.java         # Entity khách hàng
│   │   │   └── 📄 Login.java            # Entity đăng nhập
│   │   ├── 📁 repository/               # 📊 Data Access Layer
│   │   ├── 📁 service/                  # 🔧 Business Logic
│   │   ├── 📁 model/                    # 📋 Data Models
│   │   ├── 📁 util/                     # ⚙️ Utilities
│   │   └── 📁 config/                   # 🔧 Shared Configuration
│   ├── 📁 src/main/resources/
│   │   ├── 📄 application.properties    # Cấu hình ứng dụng
│   │   ├── 📁 static/                   # Static web resources
│   │   └── 📁 templates/shop/           # 🎨 Web Templates
│   │       ├── 📄 dashboard.html        # Trang dashboard
│   │       ├── 📄 employees.html        # Trang nhân viên
│   │       ├── 📄 products.html         # Trang sản phẩm
│   │       └── 📄 customers.html        # Trang khách hàng
│   └── 📁 sql/                          # 🗃️ Database Scripts
│       ├── 📄 shopmanagement.sql        # Script tạo database
│       └── 📄 test_connection.sql       # Test kết nối
```

### 🔄 Cải Tiến Cấu Trúc Dự Án

Dự án đã được tái cấu trúc theo các nguyên tắc Spring Boot tốt nhất:

#### ✅ Những Cải Tiến Chính:
- **🏗️ Separation of Concerns**: Tách biệt rõ ràng giữa web và desktop components
- **📦 Package Organization**: Cấu trúc package theo chức năng thay vì theo layer
- **🔧 Configuration Management**: Cấu hình riêng biệt cho web và desktop mode
- **🎨 Modern UI**: Sử dụng modern Swing với FlatLaf Look and Feel
- **🔒 Single Entry Point**: Một main class cho cả web và desktop mode
- **⚡ Better Error Handling**: Xử lý lỗi và fallback tốt hơn

#### 📈 Lợi Ích Của Cấu Trúc Mới:
- **Maintainability**: Dễ bảo trì và mở rộng
- **Testability**: Cấu trúc thuận lợi cho việc testing
- **Scalability**: Dễ dàng thêm tính năng mới
- **Best Practices**: Tuân thủ Spring Boot conventions
- **Code Reusability**: Tái sử dụng code giữa web và desktop

## 🚀 Hướng Dẫn Cài Đặt
```
```

## 🚀 Hướng Dẫn Cài Đặt

### 📋 Yêu Cầu Hệ Thống
- **Java**: JDK 11 hoặc cao hơn
- **Maven**: 3.6+ 
- **MySQL**: 8.0+ (tùy chọn cho chế độ web)
- **RAM**: Tối thiểu 4GB
- **Ổ cứng**: 1GB dung lượng trống

### 🗄️ Thiết Lập Cơ Sở Dữ liệu

#### ☁️ Aiven MySQL Cloud (Production - Khuyến nghị)

1. **Thiết lập biến môi trường**:
   ```bash
   # Copy file example
   cp .env.example .env
   
   # Chỉnh sửa .env với thông tin Aiven thực tế
   nano .env
   ```

2. **Chạy với Aiven MySQL**:
   ```bash
   # Sử dụng script tự động
   export MYSQL_PASSWORD='your_actual_aiven_password'
   ./start-aiven.sh
   
   # Hoặc thiết lập thủ công
   export MYSQL_HOST=mysql-288ed145-tuongvantuyen2006.h.aivencloud.com
   export MYSQL_PORT=16302
   export MYSQL_DB=defaultdb
   export MYSQL_USER=avnadmin
   export MYSQL_PASSWORD='your_password'
   export SPRING_PROFILES_ACTIVE=mysql
   ```

#### 🏠 MySQL Local (Development)

1. **Cài đặt MySQL Server**:
   ```bash
   # Ubuntu/Debian
   sudo apt install mysql-server
   
   # macOS
   brew install mysql
   
   # Windows: Tải về từ mysql.com
   ```

2. **Tạo database**:
   ```sql
   mysql -u root -p
   CREATE DATABASE shopmanagement CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE shopmanagement;
   SOURCE sql/shopmanagement.sql;
   ```

3. **Tạo user cho ứng dụng**:
   ```sql
   CREATE USER 'shopmanager'@'localhost' IDENTIFIED BY 'password123';
   GRANT ALL PRIVILEGES ON shopmanagement.* TO 'shopmanager'@'localhost';
   FLUSH PRIVILEGES;
   ```

### ⚙️ Cấu Hình Ứng Dụng

Chỉnh sửa file `src/main/resources/application.properties`:

```properties
# Database Configuration (Local MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/shopmanagement?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=shopmanager
spring.datasource.password=password123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true

# Web Configuration
server.port=8080
spring.thymeleaf.cache=false
```

## 🎮 Hướng Dẫn Sử Dụng

### ☁️ Chạy với Aiven MySQL Cloud (Production)

#### Cách 1: Sử dụng script tự động
```bash
# Đặt password Aiven trước
export MYSQL_PASSWORD='your_actual_aiven_password'

# Chạy ứng dụng
./start-aiven.sh
```

#### Cách 2: Chạy thủ công
```bash
# Đặt các biến môi trường
export MYSQL_HOST=mysql-288ed145-tuongvantuyen2006.h.aivencloud.com
export MYSQL_PORT=16302
export MYSQL_DB=defaultdb
export MYSQL_USER=avnadmin
export MYSQL_PASSWORD='your_actual_aiven_password'
export SPRING_PROFILES_ACTIVE=mysql

# Chạy ứng dụng web
mvn -f integrated-app/pom.xml spring-boot:run

# Hoặc chạy desktop mode
mvn -f integrated-app/pom.xml spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"
```

### 🌐 Chạy Ứng Dụng Web (Local Development)

#### Cách 1: Sử dụng script nhanh (Khuyến nghị)
```bash
# Từ thư mục gốc dự án
./run-web.sh
```

#### Cách 2: Sử dụng Maven trực tiếp
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=web"
```

#### Cách 3: Sử dụng VS Code Tasks
- Mở **Command Palette** (`Ctrl+Shift+P`)
- Chọn "Tasks: Run Task" 
- Chọn "Run Spring Boot App"

**🔗 Truy cập ứng dụng**: http://localhost:8080

### 🖥️ Chạy Ứng Dụng Desktop

#### Cách 1: Sử dụng script nhanh
```bash
./run-desktop.sh
```

#### Cách 2: Sử dụng Maven
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"
```

#### Cách 3: Chạy file Start.java trực tiếp
```bash
mvn compile exec:java -Dexec.mainClass="com.shopmanagement.Start"
```

## 🌐 Giao Diện Web - Hướng Dẫn Sử Dụng

### 📍 Các Trang Chính

| URL | Mô tả | Chức năng |
|-----|-------|-----------|
| `/` | Trang chủ | Chuyển hướng đến dashboard |
| `/shop/` | Dashboard | Thống kê tổng quan hệ thống |
| `/shop/employees` | Quản lý nhân viên | Xem danh sách nhân viên |
| `/shop/products` | Quản lý sản phẩm | Xem catalog sản phẩm |
| `/shop/customers` | Quản lý khách hàng | Xem thông tin khách hàng |

### 📊 Tính Năng Dashboard
- **👥 Thống kê nhân viên**: Tổng số nhân viên trong hệ thống
- **📦 Thống kê sản phẩm**: Tổng số sản phẩm và tồn kho
- **🏪 Thống kê khách hàng**: Tổng số khách hàng đã đăng ký
- **🧭 Menu điều hướng**: Truy cập nhanh các chức năng chính

## 🖥️ Ứng Dụng Desktop - Hướng Dẫn Sử Dụng

### 🔐 Đăng Nhập Hệ Thống
1. Mở ứng dụng desktop
2. Nhập **Username** và **Password**
3. Click **Đăng nhập** để vào hệ thống

### 📱 Các Chức Năng Chính

#### 👥 Quản Lý Nhân Viên
- **➕ Thêm nhân viên mới**: Form nhập thông tin cơ bản
- **✏️ Sửa thông tin**: Cập nhật thông tin nhân viên
- **🗑️ Xóa nhân viên**: Xóa khỏi hệ thống (có xác nhận)
- **🔍 Tìm kiếm**: Theo tên, chức vụ, phòng ban

#### 📦 Quản Lý Sản Phẩm  
- **➕ Thêm sản phẩm**: Thông tin sản phẩm, giá, tồn kho
- **✏️ Cập nhật giá**: Thay đổi giá bán và giá nhập
- **📊 Quản lý tồn kho**: Cập nhật số lượng sản phẩm
- **🔍 Tìm kiếm**: Theo tên, danh mục, thương hiệu

#### 🏪 Quản Lý Khách Hàng
- **➕ Thêm khách hàng**: Thông tin liên hệ và địa chỉ
- **✏️ Sửa thông tin**: Cập nhật thông tin khách hàng
- **📞 Quản lý liên hệ**: Số điện thoại, email
- **🔍 Tìm kiếm**: Theo tên, số điện thoại

## 🔧 Phát Triển và Build

### 🏗️ Build Ứng Dụng

```bash
# Build toàn bộ project
mvn clean compile

# Chạy tests
mvn test

# Tạo JAR file
mvn clean package
```

### 🚀 Chạy từ JAR File

```bash
# Web mode
java -jar target/shop-management-web-system-1.0.0.jar --mode=web

# Desktop mode
java -jar target/shop-management-web-system-1.0.0.jar --mode=desktop
```

### 🔧 VS Code Tasks

Mở **Command Palette** (`Ctrl+Shift+P`) và chọn "Tasks: Run Task":

- **Build Spring Boot App** - Build ứng dụng
- **Run Spring Boot App** - Chạy ứng dụng
- **Test Spring Boot App** - Chạy unit tests
- **Package Spring Boot App** - Tạo JAR file
- **Clean Spring Boot App** - Dọn dẹp build files

## 🗄️ Cấu Trúc Database

### 📊 Các Bảng Chính

| Bảng | Mô tả | Trường chính |
|------|-------|--------------|
| `employee` | Thông tin nhân viên | userId, employeeName, role, salary |
| `product` | Catalog sản phẩm | productId, productName, price, quantity |
| `customer` | Thông tin khách hàng | userId, customerName, email, phone |
| `login` | Xác thực người dùng | userId, password, status |

### 🔗 Mối Quan Hệ
- **Employee ↔ Login**: One-to-One (userId)
- **Customer ↔ Login**: One-to-One (userId)  
- **Product**: Standalone table

## 🚨 Xử Lý Sự Cố

### ❌ Lỗi Thường Gặp

#### 🗄️ Lỗi Kết Nối Database
```
Error: Communications link failure
```
**Giải pháp**:
- Kiểm tra MySQL server đã chạy chưa: `sudo systemctl status mysql`
- Xác nhận thông tin đăng nhập trong `application.properties`
- Test kết nối bằng: `mysql -u username -p`

#### 🌐 Lỗi Port Conflict
```
Error: Port 8080 was already in use
```
**Giải pháp**:
- Thay đổi port trong `application.properties`: `server.port=8081`
- Hoặc kill process đang sử dụng port: `sudo lsof -ti:8080 | xargs kill -9`

#### ☕ Lỗi Java Version
```
Error: Java 11 or higher required
```
**Giải pháp**:
- Cài đặt JDK 11+: `sudo apt install openjdk-11-jdk`
- Kiểm tra version: `java -version`
- Cập nhật JAVA_HOME nếu cần

#### 🖥️ Lỗi Display Desktop Mode
```
Error: Cannot connect to X11 display
```
**Giải pháp**:
- Đảm bảo chạy trên môi trường có GUI
- Không chạy desktop mode trên server headless
- Sử dụng VNC hoặc X11 forwarding nếu cần

#### 📁 Lỗi Maven Build
```
Error: Could not find or load main class
```
**Giải pháp**:
- Chạy `mvn clean compile` trước
- Kiểm tra JAVA_HOME đã set đúng chưa
- Xóa folder `target/` và build lại

