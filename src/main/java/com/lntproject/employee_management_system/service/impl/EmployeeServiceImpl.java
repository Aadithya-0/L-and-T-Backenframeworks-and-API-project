package com.lntproject.employee_management_system.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lntproject.employee_management_system.model.Employee;
import com.lntproject.employee_management_system.service.EmployeeService;
import com.lntproject.employee_management_system.util.DBConnection; // ⚠️ Person 1 must implement this

/**
 * Phase 2: Data Model & CRUD (Create/Read) - Person 2 Implementation
 * 
 * JDBC-based implementation of EmployeeService
 * Contains all database CRUD operations using JDBC
 * 
 * ⚠️ DEPENDENCY: Requires DBConnection.getConnection() from Person 1 (Phase 1)
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Step 3: Create (Add) Employee
     * Uses PreparedStatement to securely insert employee data
     * 
     * @param employee the employee object to create
     * @return the created employee with generated ID
     */
    @Override
    public Employee createEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters using PreparedStatement (prevents SQL injection)
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            
            // Execute the insert
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Retrieve the auto-generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return employee;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error creating employee: " + e.getMessage(), e);
        }
    }

    /**
     * Step 4: Read (View All) Employees
     * Uses Statement to execute SELECT query and map ResultSet to Employee objects
     * 
     * @return List of all employees in the database
     */
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Iterate through ResultSet and map each row to an Employee object
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employees: " + e.getMessage(), e);
        }
        
        return employees;
    }

    /**
     * Get employee by ID (bonus - not required for your phase)
     */
    @Override
    public Optional<Employee> getEmployeeById(long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setEmail(rs.getString("email"));
                    return Optional.of(employee);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employee by ID: " + e.getMessage(), e);
        }
        
        return Optional.empty();
    }

    /**
     * Update employee (bonus - not required for your phase)
     */
    @Override
    public Optional<Employee> updateEmployee(long id, Employee employee) {
        // ✅ Step 1: Validate input before touching the database
        if (employee == null) throw new IllegalArgumentException("Employee cannot be null");
        if (id <= 0) throw new IllegalArgumentException("Invalid id");
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("email is required");
        }

        // ✅ Step 2: Write the SQL query
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

        // ✅ Step 3: Execute query
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setLong(4, id);

            int rowsAffected = pstmt.executeUpdate();

            // ✅ Step 4: If update successful, return updated employee
            if (rowsAffected > 0) {
                employee.setId(id);
                return Optional.of(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }

        // ✅ Step 5: If no record was updated, return empty
        return Optional.empty();
    }

    /**
     * Delete employee (bonus - not required for your phase)
     */
    @Override
    public boolean deleteEmployee(long id) {
        // Step 1: Validate input
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        String sql = "DELETE FROM employees WHERE id = ?";

        // Step 2: Execute delete
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // true if deleted, false if id not found

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage(), e);
        }
    }

}
