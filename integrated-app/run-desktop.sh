#!/bin/bash

# Script to run Shop Management System in Desktop Mode
echo "Starting Shop Management System in Desktop Mode..."

# Navigate to the integrated project directory
cd /workspaces/vantuyendev-OOP_N02_2025_Group10/gs-serving-web-content-main/gs-serving-web-content-main/complete

# Run the application in desktop mode
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"

echo "Shop Management System Desktop Mode started!"
