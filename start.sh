#!/bin/bash

# Shop Management System - Spring Boot Launcher
echo "🏪 Starting Shop Management System..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6+."
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"
echo "✅ Maven version: $(mvn --version | head -n 1)"

# Function to show usage
show_usage() {
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  dev         - Run in development mode (default)"
    echo "  build       - Build the application"
    echo "  clean       - Clean build artifacts"
    echo "  package     - Build JAR package"
    echo "  test        - Run tests"
    echo "  help        - Show this help"
    echo ""
    echo "Examples:"
    echo "  $0           # Start development server"
    echo "  $0 dev       # Start development server"
    echo "  $0 build     # Build the application"
    echo "  $0 package   # Create JAR file"
}

# Default command
COMMAND=${1:-dev}

case $COMMAND in
    "dev"|"start"|"run")
        echo "🚀 Starting Spring Boot application in development mode..."
        echo "🌐 Application will be available at: http://localhost:8080"
        echo "📱 Press Ctrl+C to stop the application"
        echo ""
        mvn spring-boot:run
        ;;
    "build"|"compile")
        echo "🔨 Building application..."
        mvn clean compile
        ;;
    "clean")
        echo "🧹 Cleaning build artifacts..."
        mvn clean
        ;;
    "package"|"jar")
        echo "📦 Building JAR package..."
        mvn clean package
        echo "✅ JAR file created: target/shop-management-system-1.0.0.jar"
        echo "🚀 Run with: java -jar target/shop-management-system-1.0.0.jar"
        ;;
    "test")
        echo "🧪 Running tests..."
        mvn test
        ;;
    "help"|"-h"|"--help")
        show_usage
        ;;
    *)
        echo "❌ Unknown command: $COMMAND"
        echo ""
        show_usage
        exit 1
        ;;
esac
