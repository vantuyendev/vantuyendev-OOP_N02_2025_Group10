@echo off
echo Starting Shop Management System in Desktop Mode...
echo =================================================

echo Building project...
call mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo Build successful!
    echo Starting desktop application...
    java -jar target\shop-management-web-system-1.0.0.jar --mode=desktop
) else (
    echo Build failed! Please check for errors.
    pause
)
