#!/bin/bash

# Start MySQL Database Service
# This script ensures MySQL is running for the Shop Management System

echo "Starting MySQL service..."
sudo service mysql start

# Wait a moment for MySQL to fully start
sleep 2

# Check if MySQL is running
if sudo service mysql status | grep -q "running"; then
    echo "✅ MySQL is now running successfully!"
    echo ""
    echo "Database Information:"
    echo "- Host: localhost"
    echo "- Port: 3306" 
    echo "- Username: root"
    echo "- Password: (empty)"
    echo "- Databases: shopmanagement, f1"
    echo ""
    echo "You can now connect using SQLTools in VS Code!"
else
    echo "❌ Failed to start MySQL"
    exit 1
fi
