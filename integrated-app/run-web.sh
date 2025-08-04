#!/bin/bash

# Script to run Shop Management System in Web Mode
echo "🌐 Starting Shop Management System in Web Mode..."
echo "================================================="

# Navigate to the integrated project directory
cd /workspaces/vantuyendev-OOP_N02_2025_Group10/integrated-app

# Clean and compile first
echo "📦 Cleaning and compiling project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Starting web application..."
    
    # Run the application in web mode
    mvn spring-boot:run -Dspring-boot.run.arguments="--mode=web"
    
else
    echo "❌ Compilation failed! Please check for errors."
    exit 1
fi

echo "🌐 Shop Management System Web Mode started!"
echo "📍 Access the application at:"
echo "   Dashboard: http://localhost:3000/shop/dashboard"
echo "   Employees: http://localhost:3000/shop/employees"
echo "   Products:  http://localhost:3000/shop/products"
echo "   Customers: http://localhost:3000/shop/customers"
