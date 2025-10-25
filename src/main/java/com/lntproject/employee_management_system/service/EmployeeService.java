package com.lntproject.employee_management_system.service;

import java.util.List;
import java.util.Optional;

import com.lntproject.employee_management_system.model.Employee;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(long id);

    Optional<Employee> updateEmployee(long id, Employee employee);

    boolean deleteEmployee(long id);
}
