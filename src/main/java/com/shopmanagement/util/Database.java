package com.shopmanagement.util;

import java.sql.*;

/**
 * Class Database - chứa thông tin cấu hình kết nối database
 * Định nghĩa các constants và methods để kết nối đến H2 database
 */
public class Database {
	public static final String HOST_URI = "jdbc:h2:mem:shopmanagement";  // URI kết nối H2 in-memory
	public static final String USER = "sa";                               // Tên đăng nhập database
	public static final String PASSWORD = "password";                     // Mật khẩu database
	private static final String DRIVER = "org.h2.Driver";                // H2 driver
	
	/**
	 * Phương thức tạo kết nối đến database
	 * @return Connection object
	 * @throws SQLException nếu kết nối thất bại
	 */
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(HOST_URI, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("H2 Driver not found", e);
		}
	}
}
