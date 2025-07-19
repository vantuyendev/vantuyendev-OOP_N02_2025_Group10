# 🏪 Shop Management System

Java Swing application for managing shop operations with MySQL database.

## 🚀 Quick Start

```bash
# Thiết lập và chạy ứng dụng
make setup

# Hoặc sử dụng script trực tiếp
./scripts/setup_codespaces.sh
```

## 📱 Truy cập GUI

Sau khi chạy setup, truy cập GUI qua:
- **VS Code Ports Tab**: Mở tab PORTS → Click port 6080/8080/3000
- **Direct URLs**: Xem file `QUICK_ACCESS.txt`

## 🔐 Đăng nhập

**Employees:**
- Manager: `e001` / `123456`
- Cashier: `e002` / `123456`

**Customers:**
- Customer 1: `c001` / `123456` (Silver)
- Customer 2: `c002` / `123456` (Gold)

## 📂 Project Structure

```
shop-management-system/
├── src/main/java/com/shopmanagement/    # Java source code
├── build/classes/                       # Compiled classes
├── lib/                                 # Dependencies (MySQL connector)
├── sql/                                 # Database scripts
├── scripts/                             # Shell scripts
├── docs/                                # Documentation
├── config/                              # Configuration files
├── .vscode/                             # VS Code settings
├── pom.xml                              # Maven configuration
├── Makefile                             # Build commands
└── QUICK_ACCESS.txt                     # Quick access info
```

## 🛠️ Available Commands

```bash
make help       # Show all commands
make setup      # Setup environment and run app
make run        # Run app only (after build)
make build      # Compile Java code
make clean      # Clean build directory
make check      # Check services status
make stop       # Stop all services
make vnc        # Start VNC servers
```

## 🗄️ Database

- **Host**: localhost:3306
- **Database**: shopmanagement  
- **User**: root (no password)
- **Tables**: users, employees, customers, products

## 📚 Documentation

- `docs/ACCESS_INFO.md` - Detailed access information
- `docs/README_CODESPACES.md` - GitHub Codespaces setup
- `config/access_info.json` - Configuration in JSON format
- `QUICK_ACCESS.txt` - Quick reference

## 🔧 Development

**Requirements:**
- Java 11+
- MySQL 8.0+
- Maven 3.6+ (optional)

**IDE Setup:**
- VS Code with Java Extension Pack
- SQLTools extension for database management

## 🚨 Troubleshooting

If localhost refuses connection:
1. Use VS Code PORTS tab instead of localhost URLs
2. Copy GitHub Codespaces URLs from QUICK_ACCESS.txt
3. Restart services: `make setup`
4. Check status: `make check`

## **Mô tả:**

Hệ thống quản lý cửa hàng được xây dựng bằng __Java__ và __MySQL__

### **Package `com.shopmanagement.model`** - Chứa các class model chính:
- `User.java` - Abstract class cha cho Customer và Employee
- `Customer.java` - Class khách hàng
- `Employee.java` - Class nhân viên  
- `Product.java` - Class sản phẩm

### **Package `com.shopmanagement.util`** - Chứa các class tiện ích:
- `Database.java` - Cấu hình kết nối database
- `Theme.java` - Quản lý giao diện và màu sắc
- `ThemeManager.java` - Quản lý thay đổi theme

### **Package `com.shopmanagement.activity`** - Chứa các class giao diện:
- `LoginActivity.java` - Giao diện đăng nhập
- `SignupActivity.java` - Giao diện đăng ký
- `CustomerActivity.java` - Dashboard khách hàng
- `EmployeeActivity.java` - Dashboard nhân viên
- `ManageProduct.java` - Quản lý sản phẩm
- Các file View và Add khác cho từng chức năng

## **Cách build và chạy:**

```bash
# Sử dụng build script (khuyến nghị)
./build_and_run.sh

# Hoặc manual:
# Compile
javac -cp "lib/*" -d bin src/main/java/com/shopmanagement/**/*.java

# Run  
java -cp "bin:lib/*" com.shopmanagement.Start
```

## **Tính năng:**
* Đăng nhập cho Manager, Employee và Customer
* Admin có thể thêm Employee và Customer mới với mật khẩu tự động tạo
* Manager có thể chỉnh sửa thông tin Employee và Customer
* Manager có thể thêm, sửa và xóa sản phẩm
* Employee có thể bán sản phẩm
* Customer có thể xem sản phẩm và lịch sử mua hàng
* Hệ thống theme đa dạng với nhiều màu sắc

## **Hệ thống ID trong Project:**

### **1. User ID (userId):**

- **Định dạng:** Chuỗi 12 ký tự
- **Mục đích:** ID duy nhất cho tất cả người dùng (Employee và Customer)
- **Cách sử dụng:**
  - Khóa chính trong bảng `login`, `customer`, `employee`
  - Khóa ngoại trong bảng `purchaseinfo`
- **Ví dụ:**
  - Employee: `e001`, `e002`, `e003`, `e004`
  - Customer: `c001`, `c002`, `c003`, `c004`

### **2. Product ID (productId):**

- **Định dạng:** Integer 5 chữ số với zero-fill (00001, 00002, ...)
- **Mục đích:** ID duy nhất cho từng sản phẩm
- **Tính năng:** Auto-increment, tự động tăng khi thêm sản phẩm mới
- **Cách sử dụng:**
  - Khóa chính trong bảng `product`
  - Khóa ngoại trong bảng `purchaseinfo`
- **Ví dụ:** `00001` (Laptop Dell XPS 13), `00002` (Smartphone iPhone 15)

### **3. Purchase ID (purchaseId):**

- **Định dạng:** Integer 5 chữ số với zero-fill (00001, 00002, ...)
- **Mục đích:** ID duy nhất cho từng giao dịch mua hàng
- **Tính năng:** Auto-increment, tự động tăng khi có giao dịch mới
- **Cách sử dụng:**
  - Khóa chính trong bảng `purchaseinfo`
  - Liên kết thông tin: khách hàng, sản phẩm, số lượng, giá, ngày mua
- **Ví dụ:** `00001`, `00002`, `00003`

### **4. Status Code:**

- **Định dạng:** Integer (0 hoặc 1)
- **Mục đích:** Phân biệt loại tài khoản
- **Giá trị:**
  - `0`: Employee (Nhân viên/Manager)
  - `1`: Customer (Khách hàng)
- **Cách sử dụng:** Xác định quyền truy cập và giao diện phù hợp

### **5. Theme ID:**

- **Mục đích:** Quản lý các chủ đề giao diện
- **Cách sử dụng:** Trong `Theme.java` và `ThemeManager.java` để thay đổi màu sắc

### **6. Database Configuration:**

- **Database Name:** `f1`
- **Host:** `localhost:3306`
- **User:** `root`
- **Password:** (trống)
- **Connection URI:** `jdbc:mysql://localhost:3306/f1`

### **7. Employee Roles (vai trò nhân viên):**

- **Định dạng:** String enum
- **Các giá trị:**
  - `"General"`: Nhân viên thường
  - `"Manager"`: Quản lý
- **Cách sử dụng:** Phân quyền trong hệ thống, xác định chức năng được phép truy cập

### **8. Theme Variants (biến thể giao diện):**

- **Enum:** `ThemeVariant`
- **Các giá trị:**
  - `PROFESSIONAL_BLUE`: Giao diện xanh chuyên nghiệp
  - `MODERN_DARK`: Giao diện tối hiện đại
  - `ELEGANT_PURPLE`: Giao diện tím thanh lịch
  - `FRESH_GREEN`: Giao diện xanh tươi mát
  - `WARM_ORANGE`: Giao diện cam ấm áp
  - `CLASSIC_GRAY`: Giao diện xám cổ điển

### **9. Button Styles (kiểu nút):**

- **Enum:** `ButtonStyle`
- **Các giá trị:** `PRIMARY`, `SECONDARY`, `SUCCESS`, `DANGER`, `WARNING`, `INFO`, `OUTLINE`

### **10. UI Constants (hằng số giao diện):**

- **Kích thước cửa sổ:** 900x700 pixels
- **Kích thước nút chính:** 120x35 pixels
- **Kích thước nút phụ:** 100x35 pixels
- **Chiều cao input:** 30 pixels
- **Padding panel:** 20 pixels
- **Khoảng cách components:** 15 pixels

### **11. Font System (hệ thống font):**

- **Font chính:** Segoe UI
- **Các kích thước:**
  - Title: 32px (Bold)
  - Subtitle: 24px (Bold)
  - Heading: 20px (Bold)
  - Subheading: 16px (Bold)
  - Button/Regular: 14px
  - Caption: 12px
  - Small: 10px

### **12. Column Names (tên cột bảng):**

- **Customer Table:** `{"CustomerID", "CustomerName", "PhoneNumber", "Address"}`
- **Employee Table:** `{"EmployeeID", "EmployeeName", "PhoneNumber", "Role", "Salary"}`
- **Product Table:** `{"PID", "Name", "Price", "AvailableQuantity"}`
- **Purchase History:** `{"PurchaseID", "ProductID", "ProductName", "Amount", "Cost", "Date"}`




