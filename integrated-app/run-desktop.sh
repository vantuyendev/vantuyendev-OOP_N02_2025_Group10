#!/bin/bash

# Script to run Shop Management System in Desktop Mode
echo "🖥️  Starting Shop Management System in Desktop Mode..."
echo "====================================================="

# Navigate to the integrated project directory
cd /workspaces/vantuyendev-OOP_N02_2025_Group10/integrated-app

# Clean and compile first
echo "📦 Cleaning and compiling project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Starting desktop application..."
    
    # Run the application in desktop mode
    mvn spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"
    
else
    echo "❌ Compilation failed! Please check for errors."
    exit 1
fi

echo "🖥️  Shop Management System Desktop Mode started!"
