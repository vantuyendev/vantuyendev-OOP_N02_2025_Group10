#!/bin/bash

# Auto-fix script để tự động khắc phục các lỗi phổ biến

echo "🔧 AUTO-FIX SYSTEM ISSUES"
echo "========================="

# Function để log actions
log_action() {
    echo "🔄 $1"
}

# Function để log success
log_success() {
    echo "✅ $1"
}

# Function để log warning
log_warning() {
    echo "⚠️  $1"
}

# 1. Fix MySQL nếu không chạy
log_action "Checking and fixing MySQL..."
if ! sudo service mysql status > /dev/null 2>&1; then
    log_action "Starting MySQL service..."
    sudo service mysql start
    if sudo service mysql status > /dev/null 2>&1; then
        log_success "MySQL started successfully"
    else
        log_warning "Failed to start MySQL"
    fi
else
    log_success "MySQL is already running"
fi

# 2. Fix virtual display
log_action "Checking and fixing virtual display..."
if ! pgrep -f "Xvfb :99" > /dev/null; then
    log_action "Starting Xvfb virtual display..."
    export DISPLAY=:99
    Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &
    sleep 2
    if pgrep -f "Xvfb :99" > /dev/null; then
        log_success "Xvfb started successfully"
    else
        log_warning "Failed to start Xvfb"
    fi
else
    log_success "Xvfb is already running"
fi

# 3. Fix port conflicts
log_action "Fixing port conflicts..."
CRITICAL_PORTS=(5900 6080)
for port in "${CRITICAL_PORTS[@]}"; do
    CONFLICTS=$(netstat -tlnp 2>/dev/null | grep ":$port " | wc -l)
    if [ $CONFLICTS -gt 1 ]; then
        log_action "Fixing conflict on port $port (found $CONFLICTS processes)..."
        # Kill all processes on this port except the first one
        PIDS=$(lsof -t -i:$port 2>/dev/null | tail -n +2)
        for pid in $PIDS; do
            kill -9 $pid 2>/dev/null
            log_action "Killed duplicate process $pid on port $port"
        done
    fi
done

# 4. Fix VNC server nếu không có
log_action "Checking and fixing VNC server..."
if ! netstat -tlnp 2>/dev/null | grep -q ':5900 '; then
    log_action "Starting VNC server on port 5900..."
    x11vnc -display :99 -forever -nopw -shared -rfbport 5900 > /dev/null 2>&1 &
    sleep 2
    if netstat -tlnp 2>/dev/null | grep -q ':5900 '; then
        log_success "VNC server started successfully"
    else
        log_warning "Failed to start VNC server"
    fi
else
    log_success "VNC server is already running"
fi

# 5. Fix noVNC web server nếu không có
log_action "Checking and fixing noVNC web server..."
if ! netstat -tlnp 2>/dev/null | grep -q ':6080 '; then
    log_action "Starting noVNC web server on port 6080..."
    websockify --web=/usr/share/novnc/ 6080 localhost:5900 > /dev/null 2>&1 &
    sleep 2
    if netstat -tlnp 2>/dev/null | grep -q ':6080 '; then
        log_success "noVNC web server started successfully"
    else
        log_warning "Failed to start noVNC web server"
    fi
else
    log_success "noVNC web server is already running"
fi

# 6. Fix Java application nếu không chạy
log_action "Checking Java application..."
if ! pgrep -f "com.shopmanagement.Start" > /dev/null; then
    log_action "Java application not running, checking if it should be started..."
    log_warning "Java application is not running. Use './run.sh app' to start it."
else
    log_success "Java application is running"
fi

# 7. Clean up lock files
log_action "Cleaning up lock files..."
rm -f /tmp/.X99-lock 2>/dev/null || true
rm -f /tmp/.X11-unix/X99 2>/dev/null || true
log_success "Lock files cleaned"

# 8. Fix file permissions
log_action "Fixing script permissions..."
chmod +x scripts/*.sh 2>/dev/null || true
chmod +x run.sh 2>/dev/null || true
log_success "Script permissions fixed"

# Final status
echo ""
echo "📊 AUTO-FIX RESULTS:"
echo "===================="

# Check what's working now
WORKING_SERVICES=0
TOTAL_SERVICES=4

if sudo service mysql status > /dev/null 2>&1; then
    echo "✅ MySQL: Running"
    WORKING_SERVICES=$((WORKING_SERVICES + 1))
else
    echo "❌ MySQL: Not running"
fi

if pgrep -f "Xvfb :99" > /dev/null; then
    echo "✅ Xvfb: Running"
    WORKING_SERVICES=$((WORKING_SERVICES + 1))
else
    echo "❌ Xvfb: Not running"
fi

if netstat -tlnp 2>/dev/null | grep -q ':5900 '; then
    echo "✅ VNC Server: Running on port 5900"
    WORKING_SERVICES=$((WORKING_SERVICES + 1))
else
    echo "❌ VNC Server: Not running"
fi

if netstat -tlnp 2>/dev/null | grep -q ':6080 '; then
    echo "✅ noVNC Web: Running on port 6080"
    WORKING_SERVICES=$((WORKING_SERVICES + 1))
else
    echo "❌ noVNC Web: Not running"
fi

echo ""
echo "📊 Summary: $WORKING_SERVICES/$TOTAL_SERVICES services working"

if [ $WORKING_SERVICES -eq $TOTAL_SERVICES ]; then
    echo "🎉 All critical services are now working!"
    echo ""
    echo "🌐 Access GUI via:"
    echo "   VS Code PORTS tab → Click port 6080"
    echo "   Direct: https://$CODESPACE_NAME-6080.app.github.dev/vnc.html"
else
    echo "⚠️  Some services still need attention"
    echo "💡 Try: './run.sh setup' for full restart"
fi
