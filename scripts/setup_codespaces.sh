#!/bin/bash

# Script thiết lập môi trường để chạy Java Swing app trên GitHub Codespaces

echo "=== Thiết lập môi trường Java Swing trên GitHub Codespaces ==="

# Di chuyển về thư mục gốc project
cd "$(dirname "$0")/.."

# 1. Khởi động MySQL nếu chưa chạy
echo "1. Kiểm tra và khởi động MySQL..."
sudo service mysql status > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "Khởi động MySQL..."
    sudo service mysql start
else
    echo "MySQL đã đang chạy."
fi

# 2. Thiết lập virtual display
echo "2. Thiết lập virtual display..."
export DISPLAY=:99

# Kiểm tra Xvfb có đang chạy không
if ! pgrep -x "Xvfb" > /dev/null; then
    echo "Khởi động Xvfb..."
    Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &
    sleep 2
else
    echo "Xvfb đã đang chạy."
fi

# 3. Thiết lập VNC server với port check
echo "3. Thiết lập VNC server..."

# Cleanup existing VNC processes first
pkill -f 'x11vnc.*5900' 2>/dev/null || true
sleep 1

# Check if port 5900 is free
if netstat -tlnp | grep -q ':5900 '; then
    echo "⚠️  Port 5900 is occupied, killing process..."
    PID=$(lsof -t -i:5900 2>/dev/null || true)
    [ ! -z "$PID" ] && kill -9 $PID 2>/dev/null
    sleep 2
fi

echo "Khởi động x11vnc trên port 5900..."
x11vnc -display :99 -forever -nopw -quiet -shared -rfbport 5900 > /dev/null 2>&1 &
VNC_PID=$!
sleep 2

# Verify VNC started successfully
if ! kill -0 $VNC_PID 2>/dev/null || ! netstat -tlnp | grep -q ':5900 '; then
    echo "❌ Failed to start x11vnc on port 5900"
    exit 1
fi
echo "✅ x11vnc started successfully on port 5900"

# 4. Thiết lập noVNC web server với port check
echo "4. Thiết lập noVNC web server..."

# Cleanup existing websockify processes
pkill -f 'websockify.*6080' 2>/dev/null || true
sleep 1

# Check if port 6080 is free
if netstat -tlnp | grep -q ':6080 '; then
    echo "⚠️  Port 6080 is occupied, killing process..."
    PID=$(lsof -t -i:6080 2>/dev/null || true)
    [ ! -z "$PID" ] && kill -9 $PID 2>/dev/null
    sleep 2
fi

echo "Khởi động noVNC trên port 6080..."
websockify --web=/usr/share/novnc/ 6080 localhost:5900 > /dev/null 2>&1 &
NOVNC_PID=$!
sleep 2

# Verify noVNC started successfully
if ! kill -0 $NOVNC_PID 2>/dev/null || ! netstat -tlnp | grep -q ':6080 '; then
    echo "❌ Failed to start noVNC on port 6080"
    exit 1
fi
echo "✅ noVNC started successfully on port 6080"

# 5. Compile và chạy ứng dụng Java
echo "5. Compile và chạy ứng dụng Java..."
cd /workspaces/vantuyendev-OOP_N02_2025_Group10

# Tạo thư mục output
mkdir -p build/classes

echo "Đang compile..."
find src/main/java -name "*.java" -print | xargs javac -cp "lib/*:." -d build/classes

echo "Đang chạy ứng dụng..."
export DISPLAY=:99
java -cp "build/classes:lib/*" com.shopmanagement.Start &

echo ""
echo "=== THIẾT LẬP HOÀN TẤT ==="
echo ""
echo "🎉 Ứng dụng Java đã được khởi động!"
echo ""
echo "📱 Để xem giao diện GUI:"
echo "   1. Mở tab 'PORTS' trong VS Code"
echo "   2. Click vào port 6080 để mở noVNC"
echo "   3. Click 'Connect' để kết nối VNC"
echo ""
echo "📊 Thông tin đăng nhập mẫu:"
echo "   Manager: userId=e001, password=123456"
echo "   Employee: userId=e002, password=123456"
echo "   Customer: userId=c001, password=123456"
echo ""
echo "🔧 Lệnh hữu ích:"
echo "   - Kiểm tra MySQL: sudo service mysql status"
echo "   - Kiểm tra processes: ps aux | grep -E '(Xvfb|x11vnc|websockify|java)'"
echo "   - Dừng tất cả: pkill -f 'Xvfb|x11vnc|websockify|java'"
