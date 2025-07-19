#!/bin/bash

# Build script for Shop Management System
echo "=== Building Shop Management System ==="

# Create bin directory if not exists
mkdir -p bin

# Compile all Java files
echo "Compiling Java files..."
javac -cp "lib/*" -d bin src/main/java/com/shopmanagement/**/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    
    # Run the application
    echo "Starting application..."
    export DISPLAY=:99
    java -cp "bin:lib/*" com.shopmanagement.Start
else
    echo "❌ Compilation failed!"
    exit 1
fi
