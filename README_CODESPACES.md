# 🛍️ Hệ thống quản lý cửa hàng - Chạy trên GitHub Codespaces

## 📋 Tổng quan

Đây là hệ thống quản lý cửa hàng được xây dựng bằng **Java Swing** và **MySQL**. Project này đã được cấu hình để chạy hoàn toàn trên **GitHub Codespaces** với GUI thông qua noVNC.

## 🚀 Cách chạy trên GitHub Codespaces

### Bước 1: Khởi động tự động (Khuyến nghị)
```bash
./setup_codespaces.sh
```

### Bước 2: Xem giao diện GUI
1. Trong VS Code, mở tab **"PORTS"** (bên cạnh tab Terminal)
2. Tìm port **6080** và click vào biểu tượng 🌐 để mở trong browser
3. Trang noVNC sẽ mở ra, click **"Connect"** để kết nối
4. Giao diện đăng nhập của ứng dụng sẽ hiển thị

### Bước 3: Đăng nhập hệ thống
Sử dụng một trong các tài khoản mẫu:

**👨‍💼 Manager:**
- User ID: `e001`  
- Password: `123456`

**👤 Employee:**
- User ID: `e002`
- Password: `123456`

**🛒 Customer:**
- User ID: `c001`
- Password: `123456`

## 🔧 Thiết lập thủ công (nếu cần)

### 1. Cài đặt các extension cần thiết:
```bash
# Java Extension Pack đã được cài sẵn
# SQLTools đã được cài sẵn  
# MySQL extension đã được cài sẵn
```

### 2. Thiết lập MySQL:
```bash
sudo service mysql start
sudo mysql -u root -e "CREATE DATABASE f1;"
sudo mysql -u root f1 < sql/quanly.sql
```

### 3. Thiết lập môi trường GUI:
```bash
# Khởi động virtual display
export DISPLAY=:99
Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &

# Khởi động VNC server  
x11vnc -display :99 -forever -nopw -quiet -shared > /dev/null 2>&1 &

# Khởi động noVNC web interface
websockify --web=/usr/share/novnc/ 6080 localhost:5900 > /dev/null 2>&1 &
```

### 4. Compile và chạy ứng dụng:
```bash
javac -cp ".:mysql-connector-j-8.0.33.jar" attribute/*.java activity/*.java Start.java
java -cp ".:mysql-connector-j-8.0.33.jar" Start
```

## 📁 Cấu trúc dự án

```
├── attribute/              # Các class model
│   ├── User.java          # Abstract class cha
│   ├── Customer.java      # Class khách hàng
│   ├── Employee.java      # Class nhân viên
│   ├── Product.java       # Class sản phẩm
│   ├── Database.java      # Kết nối database
│   └── Theme*.java        # Quản lý giao diện
├── activity/              # Các class giao diện
│   ├── LoginActivity.java # Màn hình đăng nhập
│   ├── *Activity.java     # Các màn hình khác
│   └── *.java            # UI components
├── sql/
│   └── quanly.sql        # Database schema & data
├── mysql-connector-j-8.0.33.jar  # JDBC driver
├── setup_codespaces.sh   # Script thiết lập tự động
└── Start.java            # Main class
```

## ✨ Tính năng chính

- 🔐 **Đăng nhập đa vai trò** - Manager, Employee, Customer
- 👥 **Quản lý nhân viên** - Thêm, sửa, xóa nhân viên
- 👤 **Quản lý khách hàng** - Quản lý thông tin khách hàng  
- 🛍️ **Quản lý sản phẩm** - CRUD operations cho sản phẩm
- 🛒 **Bán hàng** - Xử lý giao dịch bán hàng
- 📊 **Báo cáo** - Xem lịch sử mua hàng
- 🎨 **Đa theme** - Nhiều giao diện màu sắc

## 🔍 Troubleshooting

### Ứng dụng không hiển thị GUI:
```bash
# Kiểm tra các process đang chạy
ps aux | grep -E '(Xvfb|x11vnc|websockify|java)'

# Khởi động lại tất cả
pkill -f 'Xvfb|x11vnc|websockify|java'
./setup_codespaces.sh
```

### MySQL không kết nối được:
```bash
# Kiểm tra trạng thái MySQL
sudo service mysql status

# Khởi động MySQL
sudo service mysql start

# Kiểm tra database
sudo mysql -u root -e "SHOW DATABASES;"
```

### Port 6080 không accessible:
1. Vào tab **PORTS** trong VS Code
2. Kiểm tra port 6080 có visibility là **Public** không
3. Nếu chưa, click chuột phải và chọn **"Port Visibility" > "Public"**

## 📝 Thông tin bổ sung

- **Java Version:** OpenJDK 11+
- **MySQL Version:** 8.0+
- **GUI Framework:** Java Swing
- **VNC Resolution:** 1024x768
- **Web VNC Port:** 6080

## 🤝 Đóng góp

1. Fork project
2. Tạo feature branch
3. Commit changes
4. Push to branch
5. Tạo Pull Request

---

**💡 Tip:** Sử dụng VS Code Tasks (Ctrl+Shift+P > "Run Task") để nhanh chóng compile và chạy ứng dụng!
