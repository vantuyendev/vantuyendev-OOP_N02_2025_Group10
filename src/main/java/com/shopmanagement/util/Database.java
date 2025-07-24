package com.shopmanagement.util;

import java.sql.*;

/**
 * Class Database - chứa thông tin cấu hình kết nối database
 * Định nghĩa các constants và methods để kết nối đến MySQL database
 */
public class Database {
	public static final String HOST_URI = "jdbc:mysql://localhost:3306/shopmanagement";  // URI kết nối MySQL
	public static final String USER = "root";                                            // Tên đăng nhập database
	public static final String PASSWORD = "";                                            // Mật khẩu database
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";                    // MySQL driver mới
	
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
			throw new SQLException("MySQL Driver not found", e);
		}
	}
}
