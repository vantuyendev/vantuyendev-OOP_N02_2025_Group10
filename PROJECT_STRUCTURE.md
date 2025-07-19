# Shop Management System

## Cấu trúc dự án đã được sắp xếp lại theo chuẩn Java Maven:

```
project/
├── lib/                           # Thư viện dependencies
│   └── mysql-connector-j-8.0.33.jar
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── shopmanagement/
│       │           ├── Start.java         # Main class
│       │           ├── activity/          # UI và logic
│       │           │   ├── LoginActivity.java
│       │           │   ├── CustomerActivity.java
│       │           │   ├── EmployeeActivity.java
│       │           │   └── ...
│       │           ├── model/              # Data models
│       │           │   ├── User.java
│       │           │   ├── Customer.java
│       │           │   ├── Employee.java
│       │           │   └── Product.java
│       │           └── util/               # Utilities
│       │               ├── Database.java
│       │               ├── Theme.java
│       │               └── ThemeManager.java
│       └── resources/
│           └── sql/                # Database scripts
│               └── f1.sql
├── bin/                           # Compiled classes (tự động tạo)
└── README.md
```

## Lợi ích của cấu trúc mới:

1. **Tổ chức rõ ràng theo chức năng:**
   - `model/`: Các class dữ liệu (User, Customer, Employee, Product)
   - `activity/`: Các class giao diện người dùng
   - `util/`: Các class tiện ích (Database, Theme)

2. **Package structure chuẩn:**
   - Sử dụng reverse domain naming: `com.shopmanagement`
   - Dễ mở rộng và maintain

3. **Separation of concerns:**
   - Code và resources tách biệt
   - Dependencies quản lý trong thư mục `lib/`

## Cách build và chạy:

```bash
# Compile
javac -cp "lib/*" -d bin src/main/java/com/shopmanagement/**/*.java

# Run
java -cp "bin:lib/*" com.shopmanagement.Start
```
