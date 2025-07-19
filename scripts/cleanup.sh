#!/bin/bash

# Script cleanup để dọn dẹp tất cả processes và tránh conflicts

echo "🧹 CLEANUP SYSTEM PROCESSES"
echo "=========================="

# Dừng tất cả Java applications liên quan
echo "1. Stopping Java applications..."
pkill -f 'java.*com.shopmanagement.Start' 2>/dev/null || true
pkill -f 'java.*Start' 2>/dev/null || true

# Dừng tất cả VNC processes
echo "2. Stopping VNC processes..."
pkill -f 'x11vnc' 2>/dev/null || true

# Dừng tất cả noVNC/websockify processes
echo "3. Stopping noVNC/websockify processes..."
pkill -f 'websockify' 2>/dev/null || true

# Dừng Xvfb (nếu cần restart hoàn toàn)
if [ "$1" = "full" ]; then
    echo "4. Stopping Xvfb (full cleanup)..."
    pkill -f 'Xvfb' 2>/dev/null || true
fi

# Chờ processes dừng hoàn toàn
echo "5. Waiting for processes to stop..."
sleep 3

# Kiểm tra ports có bị chiếm không
echo "6. Checking for occupied ports..."
OCCUPIED_PORTS=$(netstat -tlnp | grep -E ':(5900|5901|6080|8080|3000)' | wc -l)
if [ $OCCUPIED_PORTS -gt 0 ]; then
    echo "⚠️  Some ports still occupied:"
    netstat -tlnp | grep -E ':(5900|5901|6080|8080|3000)'
    
    # Force kill processes on specific ports
    echo "7. Force killing processes on ports..."
    for port in 5900 5901 6080 8080 3000; do
        PID=$(lsof -t -i:$port 2>/dev/null || true)
        if [ ! -z "$PID" ]; then
            echo "   Killing process $PID on port $port"
            kill -9 $PID 2>/dev/null || true
        fi
    done
fi

# Xóa lock files và temp files
echo "8. Cleaning lock files..."
rm -f /tmp/.X99-lock 2>/dev/null || true
rm -f /tmp/.X11-unix/X99 2>/dev/null || true

echo "✅ Cleanup completed!"
echo ""

# Hiển thị trạng thái cuối
echo "📊 Current status:"
echo "Java processes: $(ps aux | grep -E 'java.*com.shopmanagement' | grep -v grep | wc -l)"
echo "VNC processes: $(ps aux | grep -E '(Xvfb|x11vnc|websockify)' | grep -v grep | wc -l)"
echo "Occupied ports: $(netstat -tlnp | grep -E ':(5900|5901|6080|8080|3000)' | wc -l)"
