#!/bin/bash
# Script để chạy Shop Management System

echo "=== Shop Management System ==="
echo "Đang biên dịch chương trình..."

# Xóa các file .class cũ
find . -name "*.class" -delete

# Biên dịch tất cả các file Java
javac -cp ".:lib/*" *.java activity/*.java attribute/*.java

if [ $? -eq 0 ]; then
    echo "Biên dịch thành công!"
    echo "Đang khởi động chương trình..."
    
    # Chạy chương trình
    java -cp ".:lib/*" Start
else
    echo "Lỗi biên dịch!"
    exit 1
fi

# Dọn dẹp file .class sau khi chạy
find . -name "*.class" -delete
