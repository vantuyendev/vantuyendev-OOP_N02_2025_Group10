# 🚨 TROUBLESHOOTING GUIDE

## 🎯 Quick Fix Commands

```bash
# Automatic health check and suggestions
./run.sh health

# Auto-fix common issues
./run.sh fix

# Clean up all processes and conflicts
./run.sh cleanup

# Full system restart
./run.sh setup
```

## 🔍 Common Issues & Solutions

### 1. "localhost từ chối kết nối" (localhost refuses connection)

**Symptoms:**
- Cannot access GUI via localhost URLs
- Browser shows connection refused error

**Solutions:**
```bash
# Method 1: Use VS Code PORTS tab (Recommended)
# Open PORTS tab → Click port 6080

# Method 2: Use GitHub Codespaces URLs
# Copy from QUICK_ACCESS.txt

# Method 3: Auto-fix
./run.sh fix
```

### 2. Port Conflicts

**Symptoms:**
- Multiple processes on same port
- Services fail to start
- "Address already in use" errors

**Solutions:**
```bash
# Clean up conflicts
./run.sh cleanup

# Check port status
netstat -tlnp | grep -E ':(5900|6080|8080|3000)'

# Kill specific port (replace 6080 with actual port)
sudo lsof -t -i:6080 | xargs kill -9
```

### 3. VNC Connection Issues

**Symptoms:**
- "Connected (encrypted) to codespaces" message
- Black screen in VNC viewer
- Cannot see Java GUI

**Solutions:**
```bash
# Restart VNC services
make vnc

# Use enhanced multi-port VNC
make vnc-multi

# Check if Java app is running
ps aux | grep "com.shopmanagement.Start"
```

### 4. Java Application Not Starting

**Symptoms:**
- Compilation errors
- ClassNotFoundException
- GUI doesn't appear

**Solutions:**
```bash
# Clean and rebuild
make clean
make build

# Run with verbose output
make compile

# Check classpath
java -cp build/classes:lib/* com.shopmanagement.Start
```

### 5. MySQL Connection Issues

**Symptoms:**
- SQLException: Access denied
- Cannot connect to database
- MySQL service not running

**Solutions:**
```bash
# Restart MySQL
sudo service mysql restart

# Check MySQL status
sudo service mysql status

# Reset MySQL permissions
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';"
```

### 6. Memory/Performance Issues

**Symptoms:**
- Slow response
- High memory usage
- System freezes

**Solutions:**
```bash
# Check memory usage
free -h

# Clean up processes
./run.sh cleanup

# Restart services
./run.sh setup
```

## 🛠️ Diagnostic Commands

### System Status
```bash
# Overall health check
./run.sh health

# Check all processes
ps aux | grep -E '(java|x11vnc|websockify|Xvfb)' | grep -v grep

# Check listening ports
netstat -tlnp | grep -E ':(3000|5900|5901|6080|8080|3306)'
```

### Service-Specific Checks
```bash
# MySQL
sudo service mysql status
mysql -u root -e "SELECT 1;"

# Java Application
ps aux | grep "com.shopmanagement.Start"

# VNC Services
ps aux | grep -E "(Xvfb|x11vnc|websockify)"
```

### Resource Usage
```bash
# Disk space
df -h

# Memory usage
free -h
top -bn1 | head -20

# CPU usage
htop  # or top
```

## 🔧 Prevention Best Practices

### 1. Always Use Scripts
```bash
# Instead of manual commands, use:
./run.sh setup    # Full setup
./run.sh fix      # Fix issues
./run.sh cleanup  # Clean before restart
```

### 2. Regular Health Checks
```bash
# Check system health regularly
./run.sh health

# Monitor critical ports
watch 'netstat -tlnp | grep -E ":(5900|6080)"'
```

### 3. Proper Shutdown
```bash
# Always stop services cleanly
./run.sh stop

# Or clean shutdown
make stop
```

### 4. Use VS Code PORTS Tab
- More reliable than localhost URLs
- Automatic GitHub Codespaces URL generation
- Better error handling

## 📱 Access Methods Priority

1. **VS Code PORTS Tab** (Highest priority)
   - Most reliable
   - Automatic URL generation
   - Built-in error handling

2. **GitHub Codespaces Direct URLs**
   - Copy from `QUICK_ACCESS.txt`
   - Works from external browsers
   - Fallback option

3. **localhost URLs** (Last resort)
   - Only works within VS Code
   - Prone to connection issues
   - Use only for testing

## 🆘 Emergency Recovery

If everything fails:

```bash
# Nuclear option - full cleanup and restart
pkill -f 'java|x11vnc|websockify|Xvfb'
sleep 5
./run.sh setup
```

## 📞 Getting Help

1. **Check logs first:**
   ```bash
   ./run.sh health
   ```

2. **Try auto-fix:**
   ```bash
   ./run.sh fix
   ```

3. **Full restart:**
   ```bash
   ./run.sh setup
   ```

4. **Check documentation:**
   - `QUICK_ACCESS.txt` - Quick reference
   - `docs/ACCESS_INFO.md` - Detailed info
   - This file - Troubleshooting

---

*📅 Last updated: July 19, 2025*
*🔧 Shop Management System - OOP_N02_2025_Group10*
