#!/bin/bash

# Port forwarding và tunneling script

echo "=== GitHub Codespaces Port Access Methods ==="
echo ""

# Kiểm tra các port đang hoạt động
echo "🔍 Checking active ports..."
PORTS=(3000 5900 5901 6080 8080)

for port in "${PORTS[@]}"; do
    if netstat -tlnp 2>/dev/null | grep -q ":$port "; then
        echo "   ✅ Port $port: ACTIVE"
        
        # Hiển thị URL cho web ports
        if [[ "$port" =~ ^(3000|6080|8080)$ ]]; then
            echo "      → Web Access: http://localhost:$port/vnc.html"
        fi
        
        # Hiển thị VNC info cho VNC ports  
        if [[ "$port" =~ ^(5900|5901)$ ]]; then
            echo "      → VNC Client: localhost:$port"
        fi
    else
        echo "   ❌ Port $port: NOT ACTIVE"
    fi
done

echo ""
echo "📝 HƯỚNG DẪN SỬ DỤNG:"
echo ""
echo "1. 🌐 TRUY CẬP QUA BROWSER:"
echo "   • Mở tab PORTS trong VS Code"
echo "   • Tìm port 3000, 6080, hoặc 8080"
echo "   • Click vào biểu tượng 🌐"
echo "   • Thêm '/vnc.html' vào cuối URL"
echo ""
echo "2. 🖥️ TRUY CẬP QUA VNC CLIENT:"
echo "   • Sử dụng VNC Viewer"
echo "   • Kết nối đến: <codespace-url>:5900"
echo "   • Hoặc: <codespace-url>:5901"
echo ""
echo "3. 🔧 KHỞI ĐỘNG LẠI NẾU CẦN:"
echo "   ./start_multiport_vnc.sh"
echo ""
echo "4. 🚀 CHẠY ỨNG DỤNG JAVA:"
echo "   export DISPLAY=:99"
echo "   java -cp \"build/classes:lib/*\" com.shopmanagement.Start &"
echo ""

# Tạo URLs cho GitHub Codespaces
if [[ -n "$CODESPACE_NAME" ]]; then
    echo "🌍 CODESPACES URLs:"
    for port in 3000 6080 8080; do
        if netstat -tlnp 2>/dev/null | grep -q ":$port "; then
            echo "   https://$CODESPACE_NAME-$port.app.github.dev/vnc.html"
        fi
    done
fi
