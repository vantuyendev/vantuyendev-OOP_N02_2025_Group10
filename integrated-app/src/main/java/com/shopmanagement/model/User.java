package com.shopmanagement.model;

import java.lang.*;
import java.sql.*;
import com.shopmanagement.model.*; import com.shopmanagement.util.*;
import javax.swing.*;
import com.shopmanagement.activity.*;

/**
 * Abstract class User - lớp cha cho tất cả các loại người dùng
 * Định nghĩa các thuộc tính và phương thức chung cho Customer và Employee
 */
public abstract class User {
	protected String userId;      // ID người dùng
	protected String password;    // Mật khẩu
	protected int status;         // Trạng thái tài khoản
	
	/**
	 * Constructor khởi tạo User với userId
	 * @param userId ID của người dùng
	 */
	public User(String userId) {
		if (!userId.isEmpty())
			this.userId = userId;
		else
			throw new IllegalArgumentException("Fill in the User ID");
	}
	
	/**
	 * Phương thức abstract để lấy thông tin người dùng từ database
	 * Mỗi lớp con sẽ implement theo cách riêng
	 */
	public abstract void fetch();
	
	// Getter và Setter methods
	public String getUserId() {
		return userId;
	}
	public void setStatus(int stts) {
		this.status = stts;
	}
	public void setPassword(String passwd) {
		if (!passwd.isEmpty())
			this.password = passwd;
		else
			throw new IllegalArgumentException("Fill in the password");
	}
	
	public static int checkStatus(String uid, String pass) {
		int result = -1;
		// Use parameterized query for better security and debugging
		String query = "SELECT `userId`, `password`, `status` FROM `login` WHERE `userId` = ? AND `password` = ?";     
        Connection con = null;
        PreparedStatement pst = null;
		ResultSet rs = null;
		
		// Trim inputs to avoid whitespace issues
		uid = uid.trim();
		pass = pass.trim();
		
		System.out.println("Checking login for user: '" + uid + "' with password: '" + pass + "'");
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");
			
			pst = con.prepareStatement(query);
			pst.setString(1, uid);
			pst.setString(2, pass);
			System.out.println("prepared statement created");
			
			rs = pst.executeQuery();
			System.out.println("query executed");
			
			if(rs.next()) {
                String userId = rs.getString("userId");
                String password = rs.getString("password");
				int status = rs.getInt("status");
				System.out.println("Found user: " + userId + " with status: " + status);
				result = status;
			} else {
				System.out.println("No matching user found for: " + uid);
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
			ex.printStackTrace();
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(pst!=null)
					pst.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
		System.out.println("Final result for user '" + uid + "': " + result);
		return result;
	}
	
	public void changePassword(ChangePasswordActivity a, String oldPass, String newPass) {
		String query = "UPDATE `login` SET `password`='"+newPass+"' WHERE (`userId`='"+this.userId+"' AND `password`='"+oldPass+"');";
		Connection con = null;
        Statement st = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			int res = st.executeUpdate(query);//insert
			System.out.println("data inserted");
			if (res > 0) {
				JOptionPane.showMessageDialog(null,"Password Updated!");
			a.setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null,"Password didn't match!");
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
}