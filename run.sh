#!/bin/bash

# Quick run script for Shop Management System

echo "🏪 Shop Management System - Quick Run"
echo "======================================"

if [ "$1" = "help" ] || [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    echo "Usage: ./run.sh [command]"
    echo ""
    echo "Commands:"
    echo "  setup    - Setup environment and run application"
    echo "  app      - Run application only"
    echo "  vnc      - Start VNC servers only"
    echo "  check    - Check services status"
    echo "  health   - Health check with auto-fix suggestions"
    echo "  fix      - Auto-fix common issues"
    echo "  cleanup  - Clean up processes and conflicts"
    echo "  stop     - Stop all services"
    echo "  clean    - Clean build directory"
    echo "  help     - Show this help"
    echo ""
    echo "Default (no command): setup and run"
    exit 0
fi

case "$1" in
    "setup")
        echo "🚀 Setting up environment and running application..."
        make setup
        ;;
    "app")
        echo "📱 Running application..."
        make run
        ;;
    "vnc")
        echo "🖥️ Starting VNC servers..."
        make vnc
        ;;
    "check")
        echo "🔍 Checking services status..."
        make check
        ;;
    "health")
        echo "🏥 Running health check..."
        make health
        ;;
    "fix")
        echo "🔧 Auto-fixing common issues..."
        chmod +x scripts/auto_fix.sh
        ./scripts/auto_fix.sh
        ;;
    "cleanup")
        echo "🧹 Cleaning up processes..."
        make cleanup
        ;;
    "stop")
        echo "⏹️ Stopping all services..."
        make stop
        ;;
    "clean")
        echo "🧹 Cleaning build directory..."
        make clean
        ;;
    *)
        echo "🚀 Setting up environment and running application..."
        make setup
        ;;
esac
