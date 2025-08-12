package com.shopmanagement.test;

import com.shopmanagement.model.User;
import java.sql.*;
import com.shopmanagement.util.Database;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("=== TESTING LOGIN FUNCTIONALITY ===");
        
        // Test accounts
        String[] testUsers = {"admin", "e001", "e002", "c001", "c002"};
        String password = "123456";
        
        for (String user : testUsers) {
            System.out.println("\n--- Testing user: " + user + " ---");
            int status = User.checkStatus(user, password);
            System.out.println("Result for " + user + ": " + status);
            
            if (status == 0) {
                System.out.println("✅ " + user + " - Employee login successful");
            } else if (status == 1) {
                System.out.println("✅ " + user + " - Customer login successful");
            } else {
                System.out.println("❌ " + user + " - Login failed");
            }
        }
        
        System.out.println("\n=== TESTING DATABASE DIRECT ACCESS ===");
        testDirectDatabaseAccess();
    }
    
    public static void testDirectDatabaseAccess() {
        String query = "SELECT userId, password, status FROM login";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            System.out.println("Database records:");
            while(rs.next()) {
                String userId = rs.getString("userId");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                System.out.println("User: " + userId + ", Pass: " + password + ", Status: " + status);
            }
        } catch(Exception ex) {
            System.out.println("Database error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(st != null) st.close();
                if(con != null) con.close();
            } catch(Exception ex) {}
        }
    }
}
