#!/bin/bash

# Shop Management System - Aiven MySQL Demo Script
echo "=== Shop Management System - Aiven MySQL Configuration ==="
echo

# Set Aiven MySQL connection environment variables
export MYSQL_HOST="mysql-288ed145-tuongvantuyen2006.h.aivencloud.com"
export MYSQL_PORT="16302"
export MYSQL_DB="defaultdb"
export MYSQL_USER="avnadmin"
export MYSQL_SSL_MODE="REQUIRED"
export SPRING_PROFILES_ACTIVE=mysql

echo "🔧 Environment variables configured:"
echo "   MYSQL_HOST: $MYSQL_HOST"
echo "   MYSQL_PORT: $MYSQL_PORT"
echo "   MYSQL_DB: $MYSQL_DB"
echo "   MYSQL_USER: $MYSQL_USER"
echo "   MYSQL_SSL_MODE: $MYSQL_SSL_MODE"
echo "   SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo

echo "⚠️  IMPORTANT: You need to set your Aiven MySQL password!"
echo "   Run: export MYSQL_PASSWORD=\"your_actual_password\""
echo "   Or edit this script to include the password."
echo

# Check if password is set
if [ "$MYSQL_PASSWORD" = "" ]; then
    echo "❌ MYSQL_PASSWORD not set. Please set it first:"
    echo "   export MYSQL_PASSWORD=\"your_actual_password\""
    echo "   Then run: mvn spring-boot:run"
    echo
    echo "📋 To test the connection first:"
    echo "   mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER -p $MYSQL_DB"
    exit 1
fi

echo "✅ Starting application with Aiven MySQL..."
echo

# Navigate to the application directory
cd integrated-app

# Start the Spring Boot application
mvn spring-boot:run
