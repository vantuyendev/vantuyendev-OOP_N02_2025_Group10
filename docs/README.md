# 📊 UML & Activity Diagrams Documentation

Tài liệu này chứa các sơ đồ UML và Activity Diagram cho hệ thống Shop Management System.

## 📋 Danh sách Diagrams

### 🏗️ UML Class Diagram
- **File**: [`class-diagram.puml`](diagrams/class-diagram.puml)
- **Mô tả**: Sơ đồ lớp toàn bộ hệ thống bao gồm:
  - Model Package: User, Employee, Customer, Product
  - Utility Package: Database, Theme, ThemeManager
  - Activity Package: Tất cả các giao diện người dùng
  - Test Package: Các lớp kiểm thử
- **Mối quan hệ**: Inheritance, Composition, Association, Dependency

### 🔄 Activity Diagrams

#### 1. Login Process
- **File**: [`01-login-process.puml`](activity-diagrams/01-login-process.puml)
- **Mô tả**: Quy trình đăng nhập người dùng
- **Bao gồm**:
  - Xác thực thông tin đăng nhập
  - Phân biệt Employee/Customer
  - Phân quyền Manager/Employee thường
  - Điều hướng đến dashboard phù hợp

#### 2. Product Management (CRUD)
- **File**: [`02-product-management.puml`](activity-diagrams/02-product-management.puml)
- **Mô tả**: Quản lý sản phẩm (Create, Read, Update, Delete)
- **Bao gồm**:
  - Xem và tìm kiếm sản phẩm
  - Thêm sản phẩm mới
  - Cập nhật thông tin sản phẩm
  - Xóa sản phẩm

#### 3. Employee Management (Manager Only)
- **File**: [`03-employee-management.puml`](activity-diagrams/03-employee-management.puml)
- **Mô tả**: Quản lý nhân viên (chỉ dành cho Manager)
- **Bao gồm**:
  - Kiểm tra quyền Manager
  - CRUD operations cho Employee
  - Phân quyền và vai trò
  - Quản lý thông tin nhân viên

#### 4. Customer Product Browsing
- **File**: [`04-customer-browsing.puml`](activity-diagrams/04-customer-browsing.puml)
- **Mô tả**: Khách hàng duyệt sản phẩm và xem lịch sử mua hàng
- **Bao gồm**:
  - Duyệt và tìm kiếm sản phẩm
  - Xem lịch sử mua hàng
  - Quản lý thông tin cá nhân
  - Hỗ trợ khách hàng

#### 5. Theme Management System
- **File**: [`05-theme-management.puml`](activity-diagrams/05-theme-management.puml)
- **Mô tả**: Hệ thống quản lý giao diện theme
- **Bao gồm**:
  - Chọn và xem trước theme
  - Áp dụng theme real-time
  - Observer pattern cho theme changes
  - Cập nhật toàn bộ giao diện

## 🛠️ Cách sử dụng

### Xem Diagrams
1. Cài đặt PlantUML extension cho VS Code
2. Mở file `.puml` trong VS Code
3. Sử dụng Preview để xem diagram

### Xuất ra hình ảnh
```bash
# Sử dụng PlantUML command line
java -jar plantuml.jar diagrams/*.puml
java -jar plantuml.jar activity-diagrams/*.puml
```

### Online Viewer
Có thể paste nội dung file `.puml` vào [PlantUML Online Server](http://www.plantuml.com/plantuml/uml/)

## 📐 Design Patterns Sử dụng

### 1. Model-View-Controller (MVC)
- **Model**: User, Employee, Customer, Product classes
- **View**: Activity classes (LoginActivity, EmployeeActivity, etc.)
- **Controller**: Logic xử lý trong Activity classes

### 2. Observer Pattern
- **Subject**: ThemeManager
- **Observers**: Registered JFrames, ThemeChangeListeners
- **Use case**: Real-time theme updates

### 3. Factory Pattern
- **Implementation**: User object creation based on status
- **Location**: LoginActivity.performLogin()

### 4. Singleton Pattern
- **Implementation**: Database connection management
- **Benefit**: Single point of database access

### 5. Strategy Pattern
- **Implementation**: Different search strategies (By ID, By Name)
- **Use case**: Product, Employee, Customer search functionality

## 🔄 Workflow Relationships

```
Start Application → Login → Role Check → Dashboard
     ↓
Dashboard → Feature Selection → CRUD Operations → Database
     ↓
Theme Management → Observer Pattern → UI Updates
     ↓
Logout → Return to Login
```

## 📊 Database Relationships

```
users (parent table)
  ├── employees (1:1 relationship)
  └── customers (1:1 relationship)

products (independent table)
  └── purchase_history (1:many relationship with customers)
```

## 🎨 Theme System Architecture

```
Theme (Static Configuration)
  ↓
ThemeManager (Observer Management)
  ↓
Registered Components (Automatic Updates)
  ↓
User Interface (Real-time Changes)
```

## 🧪 Testing Coverage

Các diagrams bao gồm test scenarios cho:
- ✅ User authentication and authorization
- ✅ CRUD operations validation
- ✅ Role-based access control
- ✅ Theme system functionality
- ✅ Database connectivity
- ✅ Error handling flows

## 📝 Notes

- Tất cả diagrams tuân theo UML 2.0 standards
- Activity diagrams sử dụng swimlane notation để phân biệt actors
- Class diagram bao gồm đầy đủ methods và attributes
- Relationships được mô tả chính xác với cardinality
- Error handling flows được included trong activity diagrams

---

**Tác giả**: OOP_N02_2025_Group10  
**Ngày tạo**: July 25, 2025  
**Phiên bản**: 1.0  
**Công cụ**: PlantUML
