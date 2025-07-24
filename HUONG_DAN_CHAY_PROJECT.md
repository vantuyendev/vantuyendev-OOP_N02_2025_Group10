# 🏪 HƯỚNG DẪN CHẠY PROJECT - SHOP MANAGEMENT SYSTEM

## 📋 YÊU CẦU HỆ THỐNG

- **Java**: 11 hoặc cao hơn
- **MySQL**: 8.0 hoặc cao hơn  
- **IDE**: VS Code với Java Extension Pack (khuyến nghị)
- **OS**: Windows, macOS, Linux
- **RAM**: 2GB+ (khuyến nghị)

## 🚀 CÁCH CHẠY PROJECT - CẬP NHẬT MỚI

### 🎯 Phương pháp 1: Script tự động (Khuyến nghị mạnh)

```bash
# Cấp quyền thực thi (chỉ cần làm 1 lần)
chmod +x run.sh

# Thiết lập hoàn chỉnh và chạy ứng dụng
./run.sh setup

# Chỉ chạy ứng dụng (sau khi đã build)
./run.sh app

# Xem tất cả lệnh có sẵn
./run.sh help
```

### 🎯 Phương pháp 2: VS Code Tasks (Dễ sử dụng)

1. Mở VS Code
2. Nhấn `Ctrl+Shift+P` (hoặc `Cmd+Shift+P` trên macOS)
3. Gõ "Tasks: Run Task"
4. Chọn "Run Java Shop Management App"

### 🎯 Phương pháp 3: Makefile (Cho dev có kinh nghiệm)

```bash
# Thiết lập và chạy ứng dụng (lần đầu)
make setup

# Chỉ chạy ứng dụng (sau khi đã build)
make run

# Xem tất cả các lệnh có sẵn
make help
```

### 🎯 Phương pháp 4: Manual compilation (Debug/Dev)

```bash
# Biên dịch Java code
javac -cp "lib/mysql-connector-j-8.0.33.jar" -d build/classes src/main/java/com/shopmanagement/**/*.java

# Thiết lập display (quan trọng!)
export DISPLAY=:99

# Chạy ứng dụng
java -cp "build/classes:lib/mysql-connector-j-8.0.33.jar" com.shopmanagement.Start
```

## 🖥️ TRUY CẬP GUI - CẬP NHẬT VNC

### 🌐 Trong GitHub Codespaces:
1. **Sử dụng VS Code PORTS Tab** (Khuyến nghị nhất):
   - Mở tab PORTS ở thanh dưới VS Code
   - Tìm port 6080, 8080, hoặc 3000
   - Click vào icon địa cầu để mở GUI

2. **URLs trực tiếp** (nếu cần):
   - Primary: `https://[CODESPACE-NAME]-6080.app.github.dev/vnc.html`
   - Backup: `https://[CODESPACE-NAME]-8080.app.github.dev/vnc.html`

### 🖱️ Trên máy local:
- `http://localhost:6080/vnc.html`
- `http://localhost:8080/vnc.html`
- `http://localhost:3000/vnc.html`

## 🗄️ THIẾT LẬP DATABASE - ĐÃ TỰ ĐỘNG

### ✅ Database đã được thiết lập tự động!
Khi chạy `./run.sh setup` hoặc `make setup`, hệ thống sẽ tự động:
- Tạo database `shopmanagement` 
- Import dữ liệu mẫu từ `sql/shopmanagement.sql`
- Kiểm tra kết nối MySQL

### 🔧 Thiết lập thủ công (nếu cần):

```bash
# Tạo database
mysql -u root -e "CREATE DATABASE IF NOT EXISTS shopmanagement;"

# Import dữ liệu
mysql -u root shopmanagement < sql/shopmanagement.sql

# Kiểm tra kết nối
mysql -u root -e "USE shopmanagement; SHOW TABLES;"
```

### 📊 Thông tin kết nối:
- **Host**: localhost:3306
- **Database**: shopmanagement
- **Username**: root
- **Password**: (để trống)

## 🔐 TÀI KHOẢN ĐĂNG NHẬP MẶC ĐỊNH

### Nhân viên (Employees):
- **Manager**: 
  - Username: `e001`
  - Password: `123456`
  - Quyền: Quản lý toàn bộ hệ thống
  
- **Cashier**: 
  - Username: `e002`
  - Password: `123456`
  - Quyền: Bán hàng và thao tác cơ bản

### Khách hàng (Customers):
- **Customer 1** (Silver):
  - Username: `c001`
  - Password: `123456`
  - Thành viên: Bạc
  
- **Customer 2** (Gold):
  - Username: `c002`
  - Password: `123456`
  - Thành viên: Vàng

## 🎨 TÍNH NĂNG CHÍNH

### 👨‍💼 Cho Manager:
- ✅ Quản lý nhân viên (thêm, sửa, xóa, xem)
- ✅ Quản lý khách hàng và membership
- ✅ Quản lý sản phẩm hoàn chỉnh
- ✅ Báo cáo bán hàng và thống kê
- ✅ Cài đặt hệ thống và theme

### 👩‍💻 Cho Nhân viên (Cashier):
- ✅ Bán hàng và thanh toán
- ✅ Tra cứu khách hàng
- ✅ Xem kho hàng
- ✅ Xử lý giao dịch

### 👥 Cho Khách hàng:
- ✅ Duyệt sản phẩm và tìm kiếm
- ✅ Xem lịch sử mua hàng
- ✅ Quản lý hồ sơ cá nhân
- ✅ Theo dõi membership

### 🎨 Tính năng hệ thống:
- ✅ Hệ thống đăng nhập đa người dùng
- ✅ Tùy chỉnh theme (nhiều màu sắc):
  1. **Professional Blue** - Xanh chuyên nghiệp
  2. **Forest Green** - Xanh rừng
  3. **Sunset Orange** - Cam hoàng hôn
  4. **Royal Purple** - Tím hoàng gia
  5. **Classic Red** - Đỏ cổ điển
  6. **Steel Gray** - Xám thép
- ✅ Đồng bộ database thời gian thực
- ✅ Hỗ trợ truy cập từ xa qua VNC
- ✅ Xử lý lỗi toàn diện

## 🗄️ QUẢN LÝ DATABASE VỚI VS CODE

### 🔌 SQLTools (Đã cài đặt sẵn):
1. Mở sidebar SQLTools
2. Chọn connection "Shop Management DB"
3. Browse tables và viết SQL queries

### 🖥️ MySQL Database Client (Đã cài đặt sẵn):
1. Click icon Database ở sidebar
2. Chọn connection "Shop Management MySQL"
3. Sử dụng UI trực quan để quản lý data

## 🧪 KIỂM TRA VÀ TEST

### ✅ Test kết nối database:
```bash
# Test với Java
java -cp "build/classes:lib/mysql-connector-j-8.0.33.jar" com.shopmanagement.test.TestConnection

# Test với SQL
mysql -u root shopmanagement < sql/test_connection.sql
```

### ✅ Kiểm tra trạng thái hệ thống:
```bash
# Kiểm tra tất cả services
./run.sh status

# Kiểm tra chi tiết
./run.sh check
```

## 🚨 TROUBLESHOOTING - GIẢI QUYẾT LỖI

### ❌ Lỗi "Trang này hiện không hoạt động":
**Giải pháp:**
- ✅ Sử dụng VS Code PORTS tab thay vì URL trực tiếp
- ✅ Kiểm tra VNC: `./run.sh check`
- ✅ Restart VNC: `./run.sh vnc`

### ❌ Lỗi Java ClassNotFoundException:
**Giải pháp:**
- ✅ Build lại: `./run.sh build`
- ✅ Kiểm tra MySQL connector: `ls -la lib/`
- ✅ Clean và rebuild: `./run.sh clean && ./run.sh build`

### ❌ Lỗi kết nối Database:
**Giải pháp:**
```bash
# Khởi động MySQL
sudo service mysql start

# Kiểm tra database tồn tại
mysql -u root -e "SHOW DATABASES;"

# Tạo lại database nếu cần
mysql -u root -e "CREATE DATABASE shopmanagement;"
mysql -u root shopmanagement < sql/shopmanagement.sql
```

### ❌ GUI không hiển thị:
**Giải pháp:**
```bash
# Kiểm tra DISPLAY
echo $DISPLAY

# Kiểm tra Xvfb
ps aux | grep Xvfb

# Restart toàn bộ VNC
./run.sh cleanup
./run.sh vnc
```

### ❌ Lỗi compilation:
**Giải pháp:**
```bash
# Clean và build lại
./run.sh clean
./run.sh build

# Hoặc chi tiết hơn
rm -rf build/classes/*
javac -cp "lib/mysql-connector-j-8.0.33.jar" -d build/classes src/main/java/com/shopmanagement/**/*.java
```

## 📊 LỆNH KIỂM TRA NHANH

### 🔍 Debug Commands:
```bash
# Xem tất cả processes liên quan
ps aux | grep -E "(java|vnc|mysql)"

# Kiểm tra các ports
netstat -tlnp | grep -E ":(3306|5900|5901|6080|8080|3000)"

# Xem logs MySQL
sudo tail -f /var/log/mysql/error.log

# Test database connection
mysql -u root -e "SELECT 'Database OK' as Status;"
```

## 🆘 COMMANDS HỮU ÍCH

### 📱 Quản lý ứng dụng:
```bash
./run.sh setup      # Thiết lập hoàn chỉnh và chạy
./run.sh app        # Build và chạy
./run.sh stop       # Dừng ứng dụng
./run.sh cleanup    # Dọn dẹp tất cả
./run.sh status     # Xem trạng thái
./run.sh urls       # Hiển thị URLs truy cập
```

### 🗄️ Quản lý database:
```bash
./run.sh mysql      # Kiểm tra MySQL
make db-reset       # Reset database (nếu có)
```

## 💡 TIPS & TRICKS

### 🎯 Khuyến nghị sử dụng:
1. **Luôn dùng VS Code PORTS tab** cho GUI access
2. **Chạy `./run.sh status`** trước khi báo lỗi
3. **Sử dụng SQLTools** cho database management
4. **Backup data** trước khi thay đổi lớn

### 🚀 Workflow phát triển:
```bash
# Bước 1: Setup lần đầu
./run.sh setup

# Bước 2: Phát triển code
# ... edit files ...

# Bước 3: Test thay đổi
./run.sh stop
./run.sh build
./run.sh run

# Bước 4: Debug nếu cần
./run.sh check
./run.sh status
```

---

## 🎉 KẾT LUẬN

Project **Shop Management System** hiện đã:
- ✅ **Hoạt động ổn định** với GUI đầy đủ
- ✅ **Database integration** hoàn chỉnh
- ✅ **VNC remote access** mượt mà
- ✅ **Development tools** được setup sẵn
- ✅ **Comprehensive error handling** và troubleshooting

**Chúc bạn code vui vẻ! 🚀**

---

> 📞 **Hỗ trợ**: Nếu gặp vấn đề, hãy kiểm tra `PROJECT_STATUS_REPORT.md` và chạy `./run.sh check` để chẩn đoán.
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

