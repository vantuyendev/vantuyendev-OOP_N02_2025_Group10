package com.shopmanagement.test;

import com.shopmanagement.util.Database;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = Database.getConnection();
            if (conn != null) {
                System.out.println("✅ Database connection successful!");
                System.out.println("Database URL: " + Database.HOST_URI);
                System.out.println("Username: " + Database.USER);
                conn.close();
            } else {
                System.out.println("❌ Database connection failed - connection is null");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed with SQLException:");
            System.out.println("Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("❌ Database connection failed with Exception:");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
