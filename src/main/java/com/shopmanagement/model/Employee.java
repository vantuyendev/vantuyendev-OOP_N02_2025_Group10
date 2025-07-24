package com.shopmanagement.model;

import java.lang.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import com.shopmanagement.util.*;
import com.shopmanagement.activity.*;

/**
 * Class Employee - đại diện cho nhân viên trong hệ thống
 * Kế thừa từ User, chứa thông tin và chức năng liên quan đến nhân viên
 */
public class Employee extends User {
	private String employeeName;  // Tên nhân viên
	private String phoneNumber;   // Số điện thoại
	private String role;          // Vai trò (General/Manager)
	private double salary;        // Mức lương
	
	// Định nghĩa các cột cho bảng hiển thị và vai trò
	public static String[] columnNames = {"EmployeeID", "EmployeeName", "PhoneNumber", "Role", "Salary"};
	public static String[] roles = {"General", "Manager"};
	
	/**
	 * Constructor khởi tạo Employee
	 * @param userId ID của nhân viên
	 */
	public Employee(String userId) {
		super(userId);
		this.setStatus(0);  // Status 0 cho nhân viên
	}
	
	// Setter methods với validation
	public void setEmployeeName(String name) {
		if (!name.isEmpty())
			this.employeeName = name;
		else
			throw new IllegalArgumentException("Fill in the name");
	}
	public void setPhoneNumber(int num) {
		this.phoneNumber = "+880"+num;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	// Getter methods
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String getRole() {
		return role;
	}
	public double getSalary() {
		return salary;
	}
	
	/**
	 * Tạo mới nhân viên trong database
	 */
	public void createEmployee() {
		String query1 = "INSERT INTO `login` VALUES ('"+userId+"','"+password+"',"+status+");";
		String query2 = "INSERT INTO `employee` VALUES ('"+userId+"','"+employeeName+"','"+phoneNumber+"','"+role+"', '"+salary+"');";
		Connection con = null;
        Statement st = null;
		System.out.println(query1);
		System.out.println(query2);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.execute(query1);//insert
			st.execute(query2);
			System.out.println("data inserted");
			JOptionPane.showMessageDialog(null,"Account Created!");
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed to create account!");
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
	
	public void fetch() {
		String query = "SELECT u.user_id, u.full_name, u.phone, e.position, e.salary FROM users u LEFT JOIN employees e ON u.user_id = e.user_id WHERE u.username='"+this.userId+"';";     
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			boolean flag = false;
			while(rs.next()) {
				this.employeeName = rs.getString("full_name");
				this.phoneNumber = rs.getString("phone");
				this.role = rs.getString("position");
				this.salary = rs.getDouble("salary");
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
	
	public void updateEmployee(String name, int phone, String role, double salary) {
		String query = "UPDATE `employee` SET `employeeName`='"+name+"', `phoneNumber`='+880"+phone+"', `role`='"+role+"', `salary`="+salary+" WHERE `userId`='"+this.userId+"';";
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
			st.executeUpdate(query);//insert
			System.out.println("data inserted");
			JOptionPane.showMessageDialog(null,"Information Updated!");
			this.employeeName = name;
			this.phoneNumber = "+880"+phone;
			this.role = role;
			this.salary = salary;
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed to update account!");
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
	
	public void deleteEmployee() {
		String query1 = "DELETE FROM `login` WHERE `userId`='"+this.userId+"';";
		String query2 = "DELETE FROM `employee` WHERE `userId`='"+this.userId+"';";
		Connection con = null;
        Statement st = null;
		System.out.println(query1);
		System.out.println(query2);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.execute(query1);
			st.execute(query2);//delete
			System.out.println("data deleted");
			JOptionPane.showMessageDialog(null,"Account Deleted!");
			this.userId = "";
			this.employeeName = "";
			this.phoneNumber = "";
			this.role = "";
			this.salary = 0;
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed to delete account!");
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
	
	public static DefaultTableModel searchEmployee(String keyword, String byWhat) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		String query = "SELECT u.user_id, u.username, u.full_name, u.phone, e.position, e.salary FROM users u LEFT JOIN employees e ON u.user_id = e.user_id WHERE u.username='"+keyword+"';";
		if (byWhat.equals("By Name"))
			query = "SELECT u.user_id, u.username, u.full_name, u.phone, e.position, e.salary FROM users u LEFT JOIN employees e ON u.user_id = e.user_id WHERE u.full_name LIKE '%"+keyword+"%';";
		else {}
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				model.addRow(new Object[]{rs.getString("username"), rs.getString("full_name"), rs.getString("phone"), rs.getString("position"), rs.getString("salary")});
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
		return model;
	}
}