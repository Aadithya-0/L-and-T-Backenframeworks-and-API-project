package com.lntproject.employee_management_system.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ⚠️ PLACEHOLDER - TO BE IMPLEMENTED BY PERSON 1 (Phase 1: Database Setup & Connection)
 * 
 * This class should provide a static method to get database connections.
 * 
 * Person 1's Tasks:
 * 1. Create SQL script to create database and table
 * 2. Implement getConnection() method using DriverManager
 * 3. Handle ClassNotFoundException for JDBC driver
 * 4. Add a main() method to test the connection
 * 
 * Expected implementation:
 * - Use DriverManager.getConnection(URL, USER, PASSWORD)
 * - Handle ClassNotFoundException in a static block
 * - URL should be: jdbc:mysql://localhost:3306/EmployeeDB
 */
public class DBConnection {

    /**
     * ⚠️ TO BE IMPLEMENTED BY PERSON 1
     * 
     * This method should:
     * 1. Load MySQL JDBC Driver (Class.forName("com.mysql.cj.jdbc.Driver"))
     * 2. Return DriverManager.getConnection(URL, USER, PASSWORD)
     * 3. Throw SQLException if connection fails
     * 
     * @return Connection object to EmployeeDB database
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        // TODO: Person 1 - Implement this method
        // Example:
        // try {
        //     Class.forName("com.mysql.cj.jdbc.Driver");
        // } catch (ClassNotFoundException e) {
        //     throw new SQLException("MySQL JDBC Driver not found", e);
        // }
        // return DriverManager.getConnection(
        //     "jdbc:mysql://localhost:3306/EmployeeDB",
        //     "root",
        //     "password"
        // );
        
        throw new UnsupportedOperationException(
            "DBConnection.getConnection() not implemented yet. " +
            "Person 1 (Phase 1) needs to implement this method."
        );
    }
    
    /**
     * ⚠️ TO BE IMPLEMENTED BY PERSON 1
     * 
     * Add a main() method here to test the database connection.
     * Should print "Connection Successful!" or handle SQLException.
     */
    // public static void main(String[] args) {
    //     try {
    //         Connection conn = getConnection();
    //         System.out.println("Connection Successful!");
    //         conn.close();
    //     } catch (SQLException e) {
    //         System.err.println("Connection failed: " + e.getMessage());
    //     }
    // }
}
