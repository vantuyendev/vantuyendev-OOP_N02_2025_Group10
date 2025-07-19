# 🚀 SHOP MANAGEMENT - THÔNG TIN TRUY CẬP

## 📱 LINK TRUY CẬP GUI APPLICATION

### 🌐 GitHub Codespaces URLs (Khuyên dùng)
```
Port 6080: https://super-winner-wrgrj5rxg77xh5456-6080.app.github.dev/vnc.html
Port 8080: https://super-winner-wrgrj5rxg77xh5456-8080.app.github.dev/vnc.html
Port 3000: https://super-winner-wrgrj5rxg77xh5456-3000.app.github.dev/vnc.html
```

### 🖥️ Localhost URLs (Trong VS Code)
```
http://localhost:6080/vnc.html
http://localhost:8080/vnc.html
http://localhost:3000/vnc.html
```

### 📋 Cách truy cập:
1. **VS Code Ports Tab:** Mở tab PORTS → Click vào port 6080/8080/3000
2. **Direct URLs:** Copy link GitHub Codespaces ở trên và mở trong browser
3. **Simple Browser:** Sử dụng Simple Browser trong VS Code với localhost URLs

---

## 🔐 THÔNG TIN ĐĂNG NHẬP APPLICATION

### 👨‍💼 EMPLOYEES (Nhân viên)

#### Manager - Sales Manager
```
Username: e001
Password: 123456
Role: Sales Manager
Department: Sales
Salary: 15,000,000 VND
```

#### Employee - Cashier  
```
Username: e002
Password: 123456
Role: Cashier
Department: Sales
Salary: 8,000,000 VND
```

### 👥 CUSTOMERS (Khách hàng)

#### Customer 1 - Silver Member
```
Username: c001
Password: 123456
Full Name: Le Van C
Membership: Silver
Total Purchases: 5,000,000 VND
Address: 123 Nguyen Trai, Q1, TPHCM
```

#### Customer 2 - Gold Member
```
Username: c002
Password: 123456
Full Name: Pham Thi D
Membership: Gold
Total Purchases: 12,000,000 VND
Address: 456 Le Loi, Q3, TPHCM
```

---

## 🗄️ DATABASE ACCESS INFORMATION

### MySQL Database
```
Host: localhost
Port: 3306
Database Name: shopmanagement
Username: root
Password: (empty/blank)
```

### SQLTools Connection
```
Connection Name: MySQL-ShopManagement
Server: localhost
Port: 3306
Database: shopmanagement
Username: root
Password: (leave empty)
```

---

## 🛠️ TECHNICAL INFORMATION

### VNC Access
```
VNC Server 1: localhost:5900
VNC Server 2: localhost:5901
noVNC Web 1: localhost:6080
noVNC Web 2: localhost:8080
noVNC Web 3: localhost:3000
```

### Java Application
```
Main Class: com.shopmanagement.Start
Classpath: build/classes:lib/*
Database Driver: mysql-connector-j-8.0.33.jar
```

---

## 🚨 TROUBLESHOOTING

### Nếu "localhost từ chối kết nối":
1. **Sử dụng VS Code Ports Tab** (Cách tốt nhất)
2. **Copy GitHub Codespaces URLs** và mở trong browser mới
3. **Restart services:** `./setup_codespaces.sh`
4. **Check processes:** `ps aux | grep -E '(java|x11vnc|websockify)'`

### Kiểm tra services:
```bash
# Check MySQL
sudo service mysql status

# Check VNC processes
ps aux | grep -E '(Xvfb|x11vnc|websockify|java)' | grep -v grep

# Check listening ports
netstat -tlnp | grep -E ':(3000|5900|5901|6080|8080|3306)'
```

---

## 📊 SAMPLE DATA

### Products in Database:
- iPhone 15 Pro - 29,990,000 VND
- Samsung Galaxy S24 - 22,990,000 VND  
- MacBook Air M3 - 34,990,000 VND
- Dell XPS 13 - 25,990,000 VND
- Sony WH-1000XM5 - 7,990,000 VND

### All Users:
- admin/123456 (System Administrator)
- e001/123456 (Sales Manager)
- e002/123456 (Cashier)
- c001/123456 (Silver Customer)
- c002/123456 (Gold Customer)

---

## 🎯 QUICK ACCESS

**Để chạy ứng dụng:**
```bash
./setup_codespaces.sh
```

**Để chỉ khởi động VNC:**
```bash
./start_multiport_vnc.sh
```

**Để kiểm tra trạng thái:**
```bash
./check_access_methods.sh
```

---

*📅 Created: July 19, 2025*
*🏢 Project: Shop Management System - OOP_N02_2025_Group10*
