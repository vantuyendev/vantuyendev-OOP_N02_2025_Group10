# 🏪 Shop Management System - Spring Boot

Modern Spring Boot web application for managing shop operations with MySQL database, REST APIs, and responsive web interface.

![Java](https://img.shields.io/badge/Java-11+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)
![Status](https://img.shields.io/badge/Status-Working-green.svg)

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- MySQL 8.0+
- Maven 3.6+

### Running the Application

#### Method 1: Maven (Recommended)
```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

#### Method 2: VS Code Tasks
1. Press `Ctrl+Shift+P`
2. Type "Tasks: Run Task"  
3. Select "Build Spring Boot App" or "Run Spring Boot App"

#### Method 3: JAR file
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/shop-management-system-1.0.0.jar
```

### Access the Application
- **Web Interface**: http://localhost:8080
- **REST API**: http://localhost:8080/api
- **API Documentation**: http://localhost:8080/swagger-ui.html (if configured)
make run      # Run app only
make help     # Show all commands
```

## 🖥️ GUI Access

After running setup, access the GUI via VNC:

### 🌐 Web Access (GitHub Codespaces):
- **Primary**: Port 6080 (VS Code PORTS tab)
- **Backup**: Port 8080, 3000 
- **URLs**: Auto-generated based on your Codespace

### 🖱️ Local Access:
- `http://localhost:6080/vnc.html`
- `http://localhost:8080/vnc.html`
- `http://localhost:3000/vnc.html`

## 🔐 Login Credentials

### Employees:
| Role | Username | Password | Description |
|------|----------|----------|-------------|
| Manager | `e001` | `123456` | Full access to all features |
| Cashier | `e002` | `123456` | Sales and basic operations |

### Customers:
| Type | Username | Password | Membership |
|------|----------|----------|------------|
| Customer 1 | `c001` | `123456` | Silver |
| Customer 2 | `c002` | `123456` | Gold |

## 📊 Database Management

### SQLTools Integration:
- **Extension**: SQLTools with MySQL driver installed
- **Connection**: "Shop Management DB" (auto-configured)
- **Access**: SQLTools sidebar → Connect → Browse tables

### MySQL Database Client:
- **Extension**: MySQL Database Client installed  
- **Connection**: "Shop Management MySQL" (auto-configured)
- **Features**: Visual data browsing, query execution, schema designer

### Database Details:
- **Host**: localhost:3306
- **Database**: `shopmanagement`
- **User**: `root` (no password)
- **Tables**: `users`, `employees`, `customers`, `products`

## 📂 Project Structure

```
shop-management-system/
├── src/main/java/com/shopmanagement/     # Java source code
│   ├── Start.java                        # Main application entry
│   ├── activity/                         # UI Activities (Login, Dashboard, etc.)
│   ├── model/                           # Data models (User, Product, etc.)
│   ├── util/                            # Utilities (Database, Theme)
│   └── test/                            # Test utilities
├── build/classes/                        # Compiled Java classes
├── lib/                                 # Dependencies (MySQL connector)
├── sql/                                 # Database scripts and test queries
├── scripts/                             # Shell scripts for setup/management
├── .vscode/                             # VS Code configuration
│   ├── settings.json                    # SQLTools and extensions config
│   ├── tasks.json                       # Build and run tasks
│   └── database-client.json             # Database client connections
├── DATABASE_CONNECTIONS.md              # Database setup guide
├── PROJECT_STATUS_REPORT.md             # Latest project status
├── Makefile                             # Build automation
├── run.sh                               # Enhanced run script
└── pom.xml                              # Maven configuration
```

## 🛠️ Available Commands

### Run Script Commands:
```bash
./run.sh setup     # Complete setup: VNC + Build + Run  
./run.sh build     # Build application only
./run.sh run       # Run application only (requires build)
./run.sh app       # Build and run application
./run.sh vnc       # Start VNC servers only
./run.sh check     # Check all services status
./run.sh mysql     # Check and start MySQL service
./run.sh status    # Show running processes
./run.sh stop      # Stop Java application
./run.sh cleanup   # Stop all services and clean up
./run.sh clean     # Clean build directory
./run.sh urls      # Show access URLs
./run.sh help      # Show help
```

### Make Commands:
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

### VS Code Tasks:
- **Build Java Shop Management App**: Compile source code
- **Run Java Shop Management App**: Launch application
- **Clean and Run**: Kill existing processes and restart

## 🎯 Features

### For Managers:
- ✅ Full employee management (add, edit, delete, view)
- ✅ Customer management and membership tracking
- ✅ Complete product catalog management
- ✅ Sales reporting and analytics
- ✅ System administration

### For Employees (Cashier):
- ✅ Product sales and checkout
- ✅ Customer lookup and verification
- ✅ Basic inventory viewing
- ✅ Transaction processing

### For Customers:
- ✅ Product browsing and search
- ✅ Purchase history viewing
- ✅ Profile management
- ✅ Membership benefits tracking

### System Features:
- ✅ Multi-user authentication system
- ✅ Theme customization (multiple color schemes)
- ✅ Real-time database synchronization
- ✅ VNC remote access support
- ✅ Comprehensive error handling

## 🧪 Testing

### Test Database Connection:
```bash
# Run connection test
java -cp "build/classes:lib/mysql-connector-j-8.0.33.jar" com.shopmanagement.test.TestConnection

# Test with SQL file
mysql -u root shopmanagement < sql/test_connection.sql
```

### Manual Testing:
1. **Login Testing**: Test all user roles with provided credentials
2. **CRUD Operations**: Test create, read, update, delete for all entities
3. **Database Integrity**: Verify foreign key constraints and data consistency
4. **GUI Responsiveness**: Test all UI components and interactions

## 🔧 Development Environment

### Requirements:
- **Java**: 11+ (OpenJDK recommended)
- **MySQL**: 8.0+ 
- **OS**: Linux (Ubuntu 20.04+), Windows, macOS
- **Memory**: 2GB+ RAM recommended

### IDE Setup:
- **VS Code** with Java Extension Pack
- **SQLTools** with MySQL driver for database management
- **MySQL Database Client** for visual database operations

### Extensions Installed:
- `redhat.java` - Java language support
- `mtxr.sqltools` - SQL database management
- `mtxr.sqltools-driver-mysql` - MySQL driver for SQLTools
- `cweijan.vscode-mysql-client2` - Advanced MySQL client

## 🚨 Troubleshooting

### Common Issues:

#### 1. "Page not working" error:
- ✅ Use VS Code PORTS tab instead of direct URLs
- ✅ Check if VNC services are running: `./run.sh check`
- ✅ Restart VNC services: `./run.sh vnc`

#### 2. Java ClassNotFoundException:
- ✅ Rebuild application: `./run.sh build`
- ✅ Check MySQL connector: `ls -la lib/`
- ✅ Verify classpath in tasks.json

#### 3. Database connection failed:
- ✅ Start MySQL: `sudo service mysql start`
- ✅ Check database exists: `mysql -u root -e "SHOW DATABASES;"`
- ✅ Recreate database: `mysql -u root shopmanagement < sql/shopmanagement.sql`

#### 4. GUI not displaying:
- ✅ Check DISPLAY variable: `echo $DISPLAY`
- ✅ Verify Xvfb running: `ps aux | grep Xvfb`
- ✅ Restart VNC: `./run.sh cleanup && ./run.sh vnc`

### Debug Commands:
```bash
# Check all services
./run.sh status

# View process details
ps aux | grep -E "(java|vnc|mysql)"

# Check ports
netstat -tlnp | grep -E ":(3306|5900|5901|6080|8080|3000)"

# View application logs
tail -f /var/log/mysql/error.log
```

## 📚 Documentation Files

- `DATABASE_CONNECTIONS.md` - Database setup and connection guide
- `PROJECT_STATUS_REPORT.md` - Latest project status and fixes
- `HUONG_DAN_CHAY_PROJECT.md` - Vietnamese setup guide
- `docs/README.md` - **UML & Activity Diagrams Documentation**
- `docs/diagrams/class-diagram.puml` - **UML Class Diagram**
- `docs/activity-diagrams/` - **5 Activity Diagrams**
- `sql/test_connection.sql` - Database test queries

## 📊 UML & Activity Diagrams

### 🏗️ UML Class Diagram
Comprehensive class diagram showing:
- **Model Package**: User (abstract), Employee, Customer, Product
- **Utility Package**: Database, Theme, ThemeManager with enums
- **Activity Package**: All UI components and activities
- **Test Package**: TestConnection, TestLogin
- **Relationships**: Inheritance, Composition, Association, Dependencies

### 🔄 Activity Diagrams (5 Diagrams)
1. **Login Process** - User authentication and role-based navigation
2. **Product Management (CRUD)** - Complete product lifecycle management
3. **Employee Management** - Manager-only employee administration
4. **Customer Product Browsing** - Customer interaction and purchase history
5. **Theme Management System** - Real-time UI theme switching

### 🎨 Design Patterns Implemented
- **MVC Pattern**: Clear separation of Model, View, Controller
- **Observer Pattern**: Theme management with real-time updates
- **Factory Pattern**: User object creation based on role
- **Singleton Pattern**: Database connection management
- **Strategy Pattern**: Multiple search strategies

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Support

If you encounter issues:
1. Check `PROJECT_STATUS_REPORT.md` for latest fixes
2. Run `./run.sh check` to diagnose problems
3. Use VS Code PORTS tab for GUI access
4. Consult troubleshooting section above
5. **View UML & Activity Diagrams in `docs/` folder**

## 📊 **UML & Activity Diagrams Added!**

### 🎉 **New Documentation**
- **📋 Complete UML Class Diagram**: Shows all classes, relationships, and architecture
- **🔄 5 Activity Diagrams**: Detailed workflows for all major system processes
- **📁 Location**: `docs/` folder with comprehensive documentation

### 🚀 **What's Included**
✅ **UML Class Diagram** - Complete system architecture  
✅ **Login Process** - Authentication and role-based navigation  
✅ **Product Management** - Full CRUD operations workflow  
✅ **Employee Management** - Manager-only administration flows  
✅ **Customer Browsing** - Product browsing and purchase history  
✅ **Theme Management** - Real-time UI theme switching system  

### 📖 **How to View**
1. **Install PlantUML extension** in VS Code
2. **Open `.puml` files** in `docs/diagrams/` and `docs/activity-diagrams/`
3. **Use Preview** to see rendered diagrams
4. **Read `docs/README.md`** for complete documentation

**Happy coding! 🚀**
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




