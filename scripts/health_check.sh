#!/bin/bash

# Health check script để monitor và auto-fix system issues

echo "🔍 SYSTEM HEALTH CHECK"
echo "====================="

ISSUES_FOUND=0

# Function để báo lỗi
report_issue() {
    echo "❌ ISSUE: $1"
    ISSUES_FOUND=$((ISSUES_FOUND + 1))
}

# Function để báo OK
report_ok() {
    echo "✅ OK: $1"
}

# 1. Check MySQL
echo "1. Checking MySQL..."
if sudo service mysql status > /dev/null 2>&1; then
    report_ok "MySQL is running"
else
    report_issue "MySQL is not running"
    echo "   Fix: sudo service mysql start"
fi

# 2. Check Xvfb
echo "2. Checking Xvfb virtual display..."
if pgrep -f "Xvfb :99" > /dev/null; then
    report_ok "Xvfb is running on display :99"
else
    report_issue "Xvfb is not running"
    echo "   Fix: Xvfb :99 -screen 0 1024x768x24 &"
fi

# 3. Check VNC server
echo "3. Checking VNC server..."
if netstat -tlnp 2>/dev/null | grep -q ':5900 '; then
    report_ok "VNC server is listening on port 5900"
else
    report_issue "VNC server is not running on port 5900"
    echo "   Fix: x11vnc -display :99 -forever -nopw -shared -rfbport 5900 &"
fi

# 4. Check noVNC web server
echo "4. Checking noVNC web server..."
if netstat -tlnp 2>/dev/null | grep -q ':6080 '; then
    report_ok "noVNC is listening on port 6080"
else
    report_issue "noVNC is not running on port 6080"
    echo "   Fix: websockify --web=/usr/share/novnc/ 6080 localhost:5900 &"
fi

# 5. Check Java application
echo "5. Checking Java application..."
if pgrep -f "com.shopmanagement.Start" > /dev/null; then
    report_ok "Java Shop Management app is running"
else
    report_issue "Java Shop Management app is not running"
    echo "   Fix: cd /workspaces/vantuyendev-OOP_N02_2025_Group10 && java -cp build/classes:lib/* com.shopmanagement.Start &"
fi

# 6. Check for port conflicts
echo "6. Checking for port conflicts..."
CONFLICTS=$(netstat -tlnp 2>/dev/null | grep -E ':(5900|6080)' | wc -l)
if [ $CONFLICTS -eq 2 ]; then
    report_ok "No port conflicts detected"
else
    report_issue "Port conflicts detected ($CONFLICTS processes on critical ports)"
    echo "   Ports status:"
    netstat -tlnp 2>/dev/null | grep -E ':(5900|5901|6080|8080|3000)'
fi

# 7. Check disk space
echo "7. Checking disk space..."
DISK_USAGE=$(df / | tail -1 | awk '{print $5}' | sed 's/%//')
if [ $DISK_USAGE -lt 90 ]; then
    report_ok "Disk usage is acceptable ($DISK_USAGE%)"
else
    report_issue "Disk usage is high ($DISK_USAGE%)"
    echo "   Fix: Clean build files with 'make clean'"
fi

# 8. Check memory usage
echo "8. Checking memory usage..."
MEMORY_USAGE=$(free | awk '/^Mem:/{printf "%.0f", $3/$2 * 100.0}')
if [ $MEMORY_USAGE -lt 85 ]; then
    report_ok "Memory usage is acceptable ($MEMORY_USAGE%)"
else
    report_issue "Memory usage is high ($MEMORY_USAGE%)"
    echo "   Fix: Restart services to free memory"
fi

# Summary
echo ""
echo "📊 HEALTH CHECK SUMMARY"
echo "======================="
if [ $ISSUES_FOUND -eq 0 ]; then
    echo "🎉 All systems are healthy!"
    echo ""
    echo "🌐 Access URLs:"
    echo "   VS Code Ports: Open PORTS tab → Click port 6080"
    echo "   Direct: https://$CODESPACE_NAME-6080.app.github.dev/vnc.html"
    echo ""
    echo "🔐 Login credentials:"
    echo "   Manager: e001/123456"
    echo "   Customer: c001/123456"
else
    echo "⚠️  Found $ISSUES_FOUND issue(s) that need attention"
    echo ""
    echo "🔧 Quick fix commands:"
    echo "   Full restart: ./run.sh setup"
    echo "   Cleanup only: ./scripts/cleanup.sh"
    echo "   Check again: ./scripts/health_check.sh"
fi

exit $ISSUES_FOUND
