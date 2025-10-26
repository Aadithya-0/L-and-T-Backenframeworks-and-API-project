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
import com.lntproject.employee_management_system.util.DBConnection;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee createEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
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

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("employee_id"));
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

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("employee_id"));
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

    @Override
    public Optional<Employee> updateEmployee(long id, Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Employee cannot be null");
        if (id <= 0) throw new IllegalArgumentException("Invalid id");
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("email is required");
        }

        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ? WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setLong(4, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                employee.setId(id);
                return Optional.of(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteEmployee(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage(), e);
        }
    }

}