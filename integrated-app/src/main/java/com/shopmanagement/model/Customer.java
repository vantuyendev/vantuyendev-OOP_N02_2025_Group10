package com.shopmanagement.model;

import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import com.shopmanagement.util.*;
import com.shopmanagement.activity.*;

/**
 * Class Customer - đại diện cho khách hàng trong hệ thống
 * Kế thừa từ User, chứa thông tin và chức năng liên quan đến khách hàng
 */
public class Customer extends User {
	private String customerName;  // Tên khách hàng
	private String phoneNumber;   // Số điện thoại
	private String address;       // Địa chỉ
	
	// Định nghĩa các cột cho bảng hiển thị
	public static String[] columnNames = {"PurchaseID", "ProductID", "ProductName", "Amount", "Cost", "Date"};
	public static String[] columnName = {"CustomerID", "CustomerName", "PhoneNumber", "Address"};
	
	/**
	 * Constructor khởi tạo Customer
	 * @param userId ID của khách hàng
	 */
	public Customer(String userId) {
		super(userId);
		
		this.setStatus(1);
	}
	
	// Setter methods với validation
	public void setCustomerName(String name) {
		if (!name.isEmpty())
			this.customerName = name;
		else
			throw new IllegalArgumentException("Fill in the name");
	}
	public void setPhoneNumber(String num) {
		this.phoneNumber = "+84"+num;
	}
	public void setAddress(String address) {
		if (!address.isEmpty())
			this.address = address;
		else
			throw new IllegalArgumentException("Fill in the address");
	}
	
	// Getter methods
	public String getCustomerName() {
		return customerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}

	/**
	 * Tạo mới khách hàng trong database
	 * @param sa JFrame cha để hiển thị thông báo
	 */
	public void createCustomer(JFrame sa) {
		String query1 = "INSERT INTO `login` VALUES ('"+userId+"','"+password+"',"+status+");";
		String query2 = "INSERT INTO `customer` VALUES ('"+userId+"','"+customerName+"','"+phoneNumber+"','"+address+"');";
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
			JOptionPane.showMessageDialog(sa,"Account Created!");
			sa.setVisible(false);
			new LoginActivity().setVisible(true);
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(sa,"Failed to create account!");
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
		String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer` WHERE userId='"+this.userId+"';";     
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				this.customerName = rs.getString("customerName");
				this.phoneNumber = rs.getString("phoneNumber");
				this.address = rs.getString("address");
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
	
	public void updateCustomer(String name, String phone, String address) {
		System.out.println("DEBUG: Input phone parameter: '" + phone + "'");
		System.out.println("DEBUG: Current phoneNumber field: '" + this.phoneNumber + "'");
		
		// Update users table for basic info
		String query1 = "UPDATE `users` SET `full_name`='"+name+"', `phone`='+84"+phone+"' WHERE `user_id`='"+this.userId+"';";
		// Update customers table for address
		String query2 = "UPDATE `customers` SET `address`='"+address+"' WHERE `user_id`='"+this.userId+"';";
		
		Connection con = null;
        Statement st = null;
		System.out.println("Query 1: " + query1);
		System.out.println("Query 2: " + query2);
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			
			// Execute both update queries
			st.executeUpdate(query1);//update users table
			System.out.println("users table updated");
			st.executeUpdate(query2);//update customers table
			System.out.println("customers table updated");
			
			JOptionPane.showMessageDialog(null,"Information Updated!");
			this.customerName = name;
			this.phoneNumber = "+84"+phone;
			this.address = address;
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
	public void deleteCustomer() {
		String query1 = "DELETE FROM `login` WHERE `userId`='"+this.userId+"';";
		String query2 = "DELETE FROM `customer` WHERE `userId`='"+this.userId+"';";
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
			this.customerName = "";
			this.phoneNumber = "";
			this.address = "";
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
	
	public DefaultTableModel myProduct() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		String query = "SELECT purchaseInfo.purchaseId, purchaseInfo.productId, product.productName, purchaseInfo.cost, purchaseInfo.amount, purchaseInfo.date FROM purchaseInfo, product WHERE (`purchaseInfo`.`userId`='"+this.userId+"' AND `purchaseInfo`.`productId`=`product`.`productId`) ORDER BY `date` DESC;";     
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		
		// Format cho hiển thị số thập phân
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				String col1 = rs.getString("purchaseId");
				String col2 = rs.getString("productId");
				String col3 = rs.getString("productName");
				int col4 = rs.getInt("amount");
				double col5 = rs.getDouble("cost");
				String formattedCost = df.format(col5);
				String col6 = rs.getString("date");
				model.addRow(new Object[]{col1, col2, col3, col4, formattedCost, col6});
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
	
	public static DefaultTableModel searchCustomer(String keyword, String byWhat) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnName);
		String query = "SELECT * FROM `customer` WHERE `userId`='"+keyword+"';";
		if (byWhat.equals("By Name"))
			query = "SELECT * FROM `customer` WHERE `customerName` LIKE '%"+keyword+"%';";
		else {}
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				model.addRow(new Object[]{rs.getString("userId"), rs.getString("customerName"), rs.getString("phoneNumber"), rs.getString("address")});
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