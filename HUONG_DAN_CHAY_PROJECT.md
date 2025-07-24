# 🏪 HƯỚNG DẪN CHẠY PROJECT - SHOP MANAGEMENT SYSTEM

## 📋 YÊU CẦU HỆ THỐNG

- **Java**: 11 hoặc cao hơn
- **MySQL**: 8.0 hoặc cao hơn  
- **IDE**: VS Code với Java Extension Pack (khuyến nghị)
- **OS**: Windows, macOS, Linux

## 🚀 CÁCH CHẠY PROJECT

### Phương pháp 1: Sử dụng Makefile (Khuyến nghị)

```bash
# Thiết lập và chạy ứng dụng (lần đầu)
make setup

# Chỉ chạy ứng dụng (sau khi đã build)
make run

# Xem tất cả các lệnh có sẵn
make help
```

### Phương pháp 2: Sử dụng script shell

```bash
# Cấp quyền thực thi cho script
chmod +x run.sh

# Chạy script
./run.sh
```

### Phương pháp 3: Sử dụng task VS Code

1. Mở VS Code
2. Nhấn `Ctrl+Shift+P` (hoặc `Cmd+Shift+P` trên macOS)
3. Gõ "Tasks: Run Task"
4. Chọn "Run Java Shop Management App"

### Phương pháp 4: Manual compilation và run

```bash
# Biên dịch Java code
javac -cp "lib/mysql-connector-j-8.0.33.jar" -d build/classes src/main/java/com/shopmanagement/**/*.java

# Chạy ứng dụng
java -cp "build/classes:lib/mysql-connector-j-8.0.33.jar" com.shopmanagement.Start
```

## 🗄️ THIẾT LẬP DATABASE

### Bước 1: Tạo database

```sql
CREATE DATABASE shopmanagement;
USE shopmanagement;
```

### Bước 2: Import dữ liệu

```bash
# Sử dụng MySQL command line
mysql -u root -p shopmanagement < sql/shopmanagement.sql
```

### Bước 3: Cấu hình kết nối

Đảm bảo MySQL đang chạy với cấu hình:
- **Host**: localhost:3306
- **Database**: shopmanagement
- **Username**: root
- **Password**: (để trống hoặc theo cấu hình của bạn)

## 🔐 TÀI KHOẢN ĐĂNG NHẬP MẶC ĐỊNH

### Nhân viên (Employees):
- **Manager**: 
  - Username: `e001`
  - Password: `123456`
  
- **Cashier**: 
  - Username: `e002`
  - Password: `123456`

### Khách hàng (Customers):
- **Customer 1** (Silver):
  - Username: `c001`
  - Password: `123456`
  
- **Customer 2** (Gold):
  - Username: `c002`
  - Password: `123456`

## 🎨 TÍNH NĂNG CHÍNH

### Cho Manager

- ✅ Quản lý nhân viên (thêm, sửa, xóa, xem)
- ✅ Quản lý khách hàng (thêm, sửa, xóa, xem)
- ✅ Quản lý sản phẩm (thêm, sửa, xóa, xem)
- ✅ Xem báo cáo bán hàng
- ✅ Thay đổi theme giao diện

### Cho Employee

- ✅ Bán hàng cho khách hàng
- ✅ Xem thông tin sản phẩm
- ✅ Cập nhật thông tin cá nhân
- ✅ Thay đổi theme giao diện

### Cho Customer

- ✅ Xem danh sách sản phẩm
- ✅ Xem lịch sử mua hàng
- ✅ Cập nhật thông tin cá nhân
- ✅ Thay đổi theme giao diện

## 🎨 HỆ THỐNG THEME

Ứng dụng hỗ trợ 6 theme khác nhau:
1. **Professional Blue** - Xanh chuyên nghiệp
2. **Modern Dark** - Tối hiện đại
3. **Elegant Purple** - Tím thanh lịch
4. **Fresh Green** - Xanh tươi mát
5. **Warm Orange** - Cam ấm áp
6. **Classic Gray** - Xám cổ điển

## 🛠️ CÁC LỆNH MAKE HỮU ÍCH

```bash
make help       # Hiển thị tất cả lệnh có sẵn
make setup      # Thiết lập môi trường và chạy app
make run        # Chỉ chạy app (sau khi đã build)
make build      # Biên dịch Java code
make clean      # Xóa thư mục build
make check      # Kiểm tra trạng thái services
make stop       # Dừng tất cả services
make vnc        # Khởi động VNC servers
```

## 📁 CẤU TRÚC PROJECT

```
shop-management-system/
├── src/main/java/com/shopmanagement/    # Java source code
│   ├── Start.java                       # Main class
│   ├── activity/                        # GUI Activities
│   ├── model/                           # Data models
│   └── util/                            # Utilities
├── build/classes/                       # Compiled classes
├── lib/                                 # Dependencies
├── sql/                                 # Database scripts
├── scripts/                             # Shell scripts
├── Makefile                             # Build commands
└── run.sh                               # Run script
```

## 🐛 TROUBLESHOOTING

### Lỗi kết nối Database:
```bash
# Kiểm tra MySQL có chạy không
sudo systemctl status mysql
# hoặc
brew services list | grep mysql

# Khởi động MySQL
sudo systemctl start mysql
# hoặc
brew services start mysql
```

### Lỗi Java classpath:
```bash
# Đảm bảo MySQL connector có trong thư mục lib/
ls -la lib/mysql-connector-j-8.0.33.jar

# Kiểm tra JAVA_HOME
echo $JAVA_HOME

# Kiểm tra Java version
java -version
javac -version
```

### Lỗi compilation:
```bash
# Clean và build lại
make clean
make build
```

### Giao diện không hiển thị:
```bash
# Thiết lập DISPLAY (trên Linux)
export DISPLAY=:99

# Khởi động VNC
make vnc
```

