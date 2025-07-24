# Shop Management System - Makefile

.PHONY: help setup run clean build compile check-status stop cleanup health vnc-multi

# Default target
help:
	@echo "Shop Management System - Available commands:"
	@echo ""
	@echo "  setup       - Thiết lập môi trường và chạy ứng dụng"
	@echo "  run         - Chỉ chạy ứng dụng (không setup VNC)"
	@echo "  build       - Compile Java source code"
	@echo "  clean       - Xóa các file compiled"
	@echo "  check       - Kiểm tra trạng thái services"
	@echo "  health      - Health check và auto-fix"
	@echo "  stop        - Dừng tất cả services"
	@echo "  cleanup     - Dọn dẹp processes và conflicts"
	@echo "  vnc         - Khởi động VNC servers"
	@echo "  vnc-multi   - Khởi động multi-port VNC (enhanced)"
	@echo ""

# Thiết lập môi trường và chạy ứng dụng
setup:
	@chmod +x scripts/setup_codespaces.sh
	@./scripts/setup_codespaces.sh

# Chỉ compile và chạy Java app
run: build
	@echo "Chạy Shop Management Application..."
	@export DISPLAY=:99 && cd . && java -cp build/classes:lib/* com.shopmanagement.Start

# Compile Java source code
build:
	@echo "Compiling Java source code..."
	@mkdir -p build/classes
	@javac -cp lib/* -d build/classes src/main/java/com/shopmanagement/*.java src/main/java/com/shopmanagement/**/*.java

# Compile với verbose để debug
compile:
	@echo "Compiling with verbose output..."
	@mkdir -p build/classes
	@javac -cp lib/* -d build/classes -verbose src/main/java/com/shopmanagement/*.java src/main/java/com/shopmanagement/**/*.java

# Xóa các file compiled
clean:
	@echo "Cleaning build directory..."
	@rm -rf build/classes/*

# Kiểm tra trạng thái services
check:
	@chmod +x scripts/check_access_methods.sh
	@./scripts/check_access_methods.sh

# Health check và auto-fix
health:
	@chmod +x scripts/health_check.sh
	@./scripts/health_check.sh

# Dọn dẹp processes và conflicts
cleanup:
	@chmod +x scripts/cleanup.sh
	@./scripts/cleanup.sh

# Khởi động VNC servers
vnc:
	@chmod +x scripts/start_multiport_vnc.sh
	@./scripts/start_multiport_vnc.sh

# Khởi động multi-port VNC (enhanced)
vnc-multi:
	@chmod +x scripts/start_multiport_vnc_v2.sh
	@./scripts/start_multiport_vnc_v2.sh

# Dừng tất cả services
stop:
	@echo "Stopping all services..."
	@pkill -f 'java.*com.shopmanagement.Start' || true
	@pkill -f 'x11vnc' || true
	@pkill -f 'websockify' || true
	@pkill -f 'Xvfb' || true
	@echo "All services stopped."

# Khởi động lại MySQL
mysql-restart:
	@echo "Restarting MySQL..."
	@sudo service mysql restart

# Xem logs
logs:
	@echo "Java processes:"
	@ps aux | grep -E 'java.*com.shopmanagement' | grep -v grep || echo "No Java processes running"
	@echo ""
	@echo "VNC processes:"
	@ps aux | grep -E '(Xvfb|x11vnc|websockify)' | grep -v grep || echo "No VNC processes running"
	@echo ""
	@echo "Listening ports:"
	@netstat -tlnp | grep -E ':(3000|5900|5901|6080|8080|3306)' || echo "No relevant ports listening"
