#!/bin/bash

# Script to run Shop Management System in Web Mode
echo "Starting Shop Management System in Web Mode..."

# Navigate to the integrated project directory
cd /workspaces/vantuyendev-OOP_N02_2025_Group10/gs-serving-web-content-main/gs-serving-web-content-main/complete

# Run the application in web mode
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=web"

echo "Shop Management System Web Mode started!"
echo "Access the application at: http://localhost:8080"
