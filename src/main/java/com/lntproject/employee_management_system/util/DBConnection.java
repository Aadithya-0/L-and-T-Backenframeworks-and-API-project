package com.lntproject.employee_management_system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root123";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    
    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Please ensure mysql-connector-j is in the classpath.", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("✅ Connection Successful!");
            System.out.println("Database URL: " + DB_URL);
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName() + 
                             " " + conn.getMetaData().getDatabaseProductVersion());
            conn.close();
            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            System.err.println("Please check:");
            System.err.println("1. MySQL server is running");
            System.err.println("2. Database 'EmployeeDB' exists");
            System.err.println("3. Username and password are correct");
            System.err.println("4. MySQL connector dependency is included");
        }
    }
}