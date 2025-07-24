#!/bin/bash

# Enhanced run script for Shop Management System
# Version 2.0 - Updated with VNC support and proper classpath

echo "🏪 Shop Management System - Enhanced Run Script"
echo "==============================================="

# Function to check if Java process is running
check_java_process() {
    if pgrep -f "com.shopmanagement.Start" > /dev/null; then
        echo "✅ Java application is running"
        return 0
    else
        echo "❌ Java application is not running"
        return 1
    fi
}

# Function to check VNC services
check_vnc_services() {
    local vnc_count=$(pgrep -f "x11vnc" | wc -l)
    local websockify_count=$(pgrep -f "websockify" | wc -l)
    
    echo "VNC Servers: $vnc_count running"
    echo "WebSocket Servers: $websockify_count running"
    
    if [ $vnc_count -gt 0 ] && [ $websockify_count -gt 0 ]; then
        return 0
    else
        return 1
    fi
}

# Function to setup and start VNC
setup_vnc() {
    echo "🖥️ Setting up VNC environment..."
    chmod +x scripts/start_multiport_vnc_v2.sh
    ./scripts/start_multiport_vnc_v2.sh
}

# Function to build application
build_app() {
    echo "🔨 Building application..."
    
    # Check if lib directory exists
    if [ ! -f "lib/mysql-connector-j-8.0.33.jar" ]; then
        echo "❌ MySQL connector not found in lib/ directory"
        exit 1
    fi
    
    # Create build directory if it doesn't exist
    mkdir -p build/classes
    
    # Compile Java sources
    echo "📦 Compiling Java sources..."
    javac -cp "lib/mysql-connector-j-8.0.33.jar" -d build/classes src/main/java/com/shopmanagement/**/*.java
    
    if [ $? -eq 0 ]; then
        echo "✅ Compilation successful"
    else
        echo "❌ Compilation failed"
        exit 1
    fi
}

# Function to run application
run_app() {
    echo "🚀 Starting Shop Management Application..."
    
    # Set display environment
    export DISPLAY=:99
    
    # Kill existing Java processes
    pkill -f "com.shopmanagement.Start" 2>/dev/null || true
    sleep 1
    
    # Run the application
    echo "📱 Launching GUI application..."
    java -cp "build/classes:lib/mysql-connector-j-8.0.33.jar" com.shopmanagement.Start &
    
    # Wait a moment and check if it started
    sleep 2
    if check_java_process; then
        echo "✅ Application started successfully"
    else
        echo "❌ Failed to start application"
        exit 1
    fi
}

# Function to check MySQL service
check_mysql() {
    echo "🗄️ Checking MySQL service..."
    if sudo service mysql status > /dev/null 2>&1; then
        echo "✅ MySQL service is running"
        return 0
    else
        echo "⚠️ MySQL service is not running"
        echo "💡 Starting MySQL service..."
        sudo service mysql start
        return $?
    fi
}

# Function to show access URLs
show_access_info() {
    echo ""
    echo "🌐 ACCESS INFORMATION:"
    echo "====================="
    if [ ! -z "$CODESPACE_NAME" ]; then
        echo "🎯 VNC Access URLs:"
        echo "   Primary:   https://$CODESPACE_NAME-6080.app.github.dev/vnc.html"
        echo "   Backup 1:  https://$CODESPACE_NAME-8080.app.github.dev/vnc.html"
        echo "   Backup 2:  https://$CODESPACE_NAME-3000.app.github.dev/vnc.html"
    else
        echo "🎯 Local VNC Access:"
        echo "   http://localhost:6080/vnc.html"
        echo "   http://localhost:8080/vnc.html"
        echo "   http://localhost:3000/vnc.html"
    fi
    echo ""
    echo "👥 Test Accounts:"
    echo "   Manager:  e001 / 123456"
    echo "   Cashier:  e002 / 123456"
    echo "   Customer: c001 / 123456"
    echo ""
}

if [ "$1" = "help" ] || [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    echo "Usage: ./run.sh [command]"
    echo ""
    echo "Commands:"
    echo "  setup     - Complete setup: VNC + Build + Run"
    echo "  build     - Build application only"
    echo "  run       - Run application only (requires build)"
    echo "  app       - Build and run application"
    echo "  vnc       - Start VNC servers only"
    echo "  check     - Check all services status"
    echo "  mysql     - Check and start MySQL service"
    echo "  status    - Show running processes"
    echo "  stop      - Stop Java application"
    echo "  cleanup   - Stop all services and clean up"
    echo "  clean     - Clean build directory"
    echo "  urls      - Show access URLs"
    echo "  help      - Show this help"
    echo ""
    echo "Default (no command): Complete setup and run"
    exit 0
fi

case "$1" in
    "setup")
        echo "🚀 Complete setup and run..."
        check_mysql
        setup_vnc
        build_app
        run_app
        show_access_info
        ;;
    "build")
        echo "🔨 Building application..."
        build_app
        ;;
    "run")
        echo "📱 Running application..."
        export DISPLAY=:99
        run_app
        ;;
    "app")
        echo "��📱 Build and run application..."
        build_app
        run_app
        ;;
    "vnc")
        echo "🖥️ Starting VNC servers..."
        setup_vnc
        ;;
    "check")
        echo "🔍 Checking all services..."
        check_mysql
        check_vnc_services
        check_java_process
        ;;
    "mysql")
        echo "🗄️ Checking MySQL service..."
        check_mysql
        ;;
    "status")
        echo "� Current status:"
        echo "=================="
        check_mysql
        check_vnc_services  
        check_java_process
        ;;
    "stop")
        echo "⏹️ Stopping Java application..."
        pkill -f "com.shopmanagement.Start"
        echo "✅ Application stopped"
        ;;
    "cleanup")
        echo "🧹 Cleaning up all services..."
        pkill -f "com.shopmanagement.Start" 2>/dev/null || true
        pkill -f "x11vnc" 2>/dev/null || true
        pkill -f "websockify" 2>/dev/null || true
        echo "✅ Cleanup completed"
        ;;
    "clean")
        echo "🧹 Cleaning build directory..."
        rm -rf build/classes/*
        echo "✅ Build directory cleaned"
        ;;
    "urls")
        show_access_info
        ;;
    *)
        echo "🚀 Complete setup and run (default)..."
        check_mysql
        setup_vnc
        build_app
        run_app
        show_access_info
        ;;
esac
