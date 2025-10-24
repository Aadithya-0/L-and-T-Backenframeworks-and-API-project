package com.employeemanagement.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {   
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    
    private static final String USER = "root"; 
    private static final String PASSWORD = "root123"; 

    static {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("FATAL ERROR: MySQL JDBC Driver not found.");
            e.printStackTrace();
            System.exit(1); 
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }      
    public static void main(String[] args) {
        System.out.println("--- Starting DB Connection Test ---");
        try (Connection conn = getConnection()) {
            if (conn != null) { 
                System.out.println("SUCCESS! Connection is established and ready.");                
            }
        } catch (SQLException e) {
            System.err.println("FAILURE: Database connection failed.");                    
            e.printStackTrace();
        }
    }
}
