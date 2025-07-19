#!/bin/bash

# Multi-port VNC launcher script

echo "=== Khởi động Multi-port VNC Server ==="

# Dừng các process cũ
pkill -f 'x11vnc|websockify' 2>/dev/null || true

# Khởi động Xvfb nếu chưa chạy
if ! pgrep -x "Xvfb" > /dev/null; then
    echo "Khởi động Xvfb..."
    export DISPLAY=:99
    Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &
    sleep 2
fi

echo "Khởi động VNC servers trên nhiều port..."

# VNC trên port 5900
x11vnc -display :99 -forever -nopw -shared -rfbport 5900 > /dev/null 2>&1 &
VNC1_PID=$!

# VNC trên port 5901  
x11vnc -display :99 -forever -nopw -shared -rfbport 5901 > /dev/null 2>&1 &
VNC2_PID=$!

sleep 2

echo "Khởi động noVNC web servers..."

# noVNC trên port 6080
websockify --web=/usr/share/novnc/ 6080 localhost:5900 > /dev/null 2>&1 &
WEB1_PID=$!

# noVNC trên port 8080
websockify --web=/usr/share/novnc/ 8080 localhost:5901 > /dev/null 2>&1 &
WEB2_PID=$!

# noVNC trên port 3000 (HTTP server)
python3 -m http.server 3000 --directory /usr/share/novnc/ > /dev/null 2>&1 &
WEB3_PID=$!

sleep 2

echo ""
echo "=== SERVERS ĐÃ KHỞI ĐỘNG ==="
echo ""
echo "🌐 CÁC CÁCH TRUY CẬP VNC:"
echo "   Method 1: http://localhost:6080/vnc.html (Port 6080)"
echo "   Method 2: http://localhost:8080/vnc.html (Port 8080)" 
echo "   Method 3: http://localhost:3000/vnc.html (Port 3000)"
echo ""
echo "🔧 VNC DIRECT ACCESS:"
echo "   VNC Client → localhost:5900"
echo "   VNC Client → localhost:5901"
echo ""
echo "📊 KIỂM TRA TRẠNG THÁI:"
echo "   netstat -tlnp | grep -E '(5900|5901|6080|8080|3000)'"
echo ""
echo "🛑 DỪNG TẤT CẢ:"
echo "   pkill -f 'x11vnc|websockify|python.*http.server'"
echo ""

# Hiển thị trạng thái ports
echo "📡 PORTS ĐANG LẮNG NGHE:"
netstat -tlnp 2>/dev/null | grep -E '(5900|5901|6080|8080|3000)' | while read line; do
    echo "   $line"
done
