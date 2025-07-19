#!/bin/bash

# Enhanced Multi-port VNC setup với conflict prevention

echo "🖥️ STARTING MULTI-PORT VNC SETUP"
echo "================================="

# Function to check and kill process on port
kill_port() {
    local port=$1
    local pid=$(lsof -t -i:$port 2>/dev/null)
    if [ ! -z "$pid" ]; then
        echo "⚠️  Killing existing process $pid on port $port"
        kill -9 $pid 2>/dev/null
        sleep 1
    fi
}

# Function to start VNC server safely
start_vnc() {
    local port=$1
    echo "Starting x11vnc on port $port..."
    
    kill_port $port
    
    x11vnc -display :99 -forever -nopw -shared -rfbport $port > /dev/null 2>&1 &
    local pid=$!
    sleep 2
    
    if kill -0 $pid 2>/dev/null && netstat -tlnp | grep -q ":$port "; then
        echo "✅ x11vnc started successfully on port $port (PID: $pid)"
        return 0
    else
        echo "❌ Failed to start x11vnc on port $port"
        return 1
    fi
}

# Function to start noVNC safely  
start_novnc() {
    local web_port=$1
    local vnc_port=$2
    echo "Starting noVNC on port $web_port (connecting to VNC port $vnc_port)..."
    
    kill_port $web_port
    
    websockify --web=/usr/share/novnc/ $web_port localhost:$vnc_port > /dev/null 2>&1 &
    local pid=$!
    sleep 2
    
    if kill -0 $pid 2>/dev/null && netstat -tlnp | grep -q ":$web_port "; then
        echo "✅ noVNC started successfully on port $web_port (PID: $pid)"
        return 0
    else
        echo "❌ Failed to start noVNC on port $web_port"
        return 1
    fi
}

# Ensure Xvfb is running
if ! pgrep -f "Xvfb :99" > /dev/null; then
    echo "Starting Xvfb virtual display..."
    export DISPLAY=:99
    Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &
    sleep 2
fi

# Cleanup any existing processes first
echo "1. Cleaning up existing processes..."
pkill -f 'x11vnc' 2>/dev/null || true
pkill -f 'websockify' 2>/dev/null || true
sleep 2

# Start VNC servers
echo "2. Starting VNC servers..."
start_vnc 5900
start_vnc 5901

# Start noVNC web interfaces
echo "3. Starting noVNC web interfaces..."
start_novnc 6080 5900
start_novnc 8080 5901
start_novnc 3000 5900

# Final status check
echo ""
echo "📊 FINAL STATUS:"
LISTENING_PORTS=$(netstat -tlnp 2>/dev/null | grep -E ':(5900|5901|6080|8080|3000)' | wc -l)
echo "Active ports: $LISTENING_PORTS/5"

if [ $LISTENING_PORTS -eq 5 ]; then
    echo "🎉 All ports started successfully!"
    echo ""
    echo "🌐 Access URLs:"
    echo "   Primary:   https://$CODESPACE_NAME-6080.app.github.dev/vnc.html"
    echo "   Backup 1:  https://$CODESPACE_NAME-8080.app.github.dev/vnc.html"
    echo "   Backup 2:  https://$CODESPACE_NAME-3000.app.github.dev/vnc.html"
else
    echo "⚠️  Some ports failed to start:"
    netstat -tlnp 2>/dev/null | grep -E ':(5900|5901|6080|8080|3000)'
fi

echo ""
echo "💡 TIP: Use VS Code PORTS tab for best results!"
