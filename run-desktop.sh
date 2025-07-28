#!/bin/bash
# Shop Management Desktop App Runner

echo "Building Shop Management Desktop Application..."
mvn clean package -q

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "🚀 Starting Shop Management Desktop App..."
    java -jar target/shop-management-system-1.0.0.jar
else
    echo "❌ Build failed! Please check the errors above."
    exit 1
fi
