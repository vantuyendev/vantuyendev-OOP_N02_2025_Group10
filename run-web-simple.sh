#!/bin/bash

# Simple script to run Shop Management System in Web Mode
echo "🌐 Starting Shop Management System - Web Mode"
echo "============================================="

cd /workspaces/vantuyendev-OOP_N02_2025_Group10/integrated-app

echo "📦 Cleaning and compiling..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🚀 Starting web application on port 8080..."
    echo "📍 Access URLs:"
    echo "   Dashboard: http://localhost:8080/shop/dashboard"
    echo "   Employees: http://localhost:8080/shop/employees"
    echo "   Products:  http://localhost:8080/shop/products"
    echo "   Customers: http://localhost:8080/shop/customers"
    echo ""
    echo "⚠️  Press Ctrl+C to stop the application"
    echo ""
    
    mvn spring-boot:run -Dspring-boot.run.arguments="--mode=web"
else
    echo "❌ Compilation failed! Please check for errors."
    exit 1
fi
