#!/bin/bash

# Quick Start Script for Shop Management System
echo "🚀 Shop Management System - Quick Start"
echo "======================================="

# Function to display menu
show_menu() {
    echo ""
    echo "Choose an option:"
    echo "1. 🌐 Start Web Mode (port 3000)"
    echo "2. 🖥️  Start Desktop Mode"
    echo "3. 🧪 Run Tests"
    echo "4. 📦 Clean & Package"
    echo "5. 🛠️  Clean Project"
    echo "6. ❌ Exit"
    echo ""
}

# Function to run web mode
start_web() {
    echo "🌐 Starting Web Mode..."
    cd integrated-app
    mvn clean compile spring-boot:run -Dspring-boot.run.arguments="--mode=web"
}

# Function to run desktop mode
start_desktop() {
    echo "🖥️ Starting Desktop Mode..."
    cd integrated-app
    mvn clean compile spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"
}

# Function to run tests
run_tests() {
    echo "🧪 Running Tests..."
    cd integrated-app
    mvn clean test
}

# Function to package
package_app() {
    echo "📦 Cleaning & Packaging..."
    cd integrated-app
    mvn clean package
}

# Function to clean
clean_project() {
    echo "🛠️ Cleaning Project..."
    cd integrated-app
    mvn clean
    echo "✅ Project cleaned successfully!"
}

# Main menu loop
while true; do
    show_menu
    read -p "Enter your choice (1-6): " choice
    
    case $choice in
        1)
            start_web
            break
            ;;
        2)
            start_desktop
            break
            ;;
        3)
            run_tests
            echo "Press Enter to continue..."
            read
            ;;
        4)
            package_app
            echo "Press Enter to continue..."
            read
            ;;
        5)
            clean_project
            echo "Press Enter to continue..."
            read
            ;;
        6)
            echo "👋 Goodbye!"
            exit 0
            ;;
        *)
            echo "❌ Invalid option. Please choose 1-6."
            ;;
    esac
done
