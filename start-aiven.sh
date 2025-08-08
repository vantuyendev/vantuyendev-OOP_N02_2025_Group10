#!/bin/bash

# Shop Management System - Aiven MySQL Startup Script
echo "Starting Shop Management System with Aiven MySQL..."

# Set Aiven MySQL connection environment variables
export MYSQL_HOST="mysql-288ed145-tuongvantuyen2006.h.aivencloud.com"
export MYSQL_PORT="16302"
export MYSQL_DB="defaultdb"
export MYSQL_USER="avnadmin"
export MYSQL_SSL_MODE="REQUIRED"

# Ask for password securely or use environment variable
if [ -z "$MYSQL_PASSWORD" ]; then
    echo -n "Enter Aiven MySQL password: "
    read -s MYSQL_PASSWORD
    export MYSQL_PASSWORD
    echo
else
    echo "Using MYSQL_PASSWORD from environment variable"
fi

# Set Spring profile to mysql
export SPRING_PROFILES_ACTIVE=mysql

echo "Environment variables set:"
echo "- MYSQL_HOST: $MYSQL_HOST"
echo "- MYSQL_PORT: $MYSQL_PORT"
echo "- MYSQL_DB: $MYSQL_DB"
echo "- MYSQL_USER: $MYSQL_USER"
echo "- MYSQL_SSL_MODE: $MYSQL_SSL_MODE"
echo "- SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo

# Navigate to the application directory
cd integrated-app

# Start the Spring Boot application
echo "Starting application with Maven..."
mvn spring-boot:run