package com.lntproject.employee_management_system;

import com.lntproject.employee_management_system.model.Employee;
import com.lntproject.employee_management_system.service.EmployeeService;
import com.lntproject.employee_management_system.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for EmployeeServiceImpl (Person 2's implementation)
 * 
 * These tests verify:
 * 1. Employee model structure (Step 1)
 * 2. Service methods compile and have correct signatures (Step 2)
 * 3. Basic object creation and manipulation
 * 
 * NOTE: These tests DO NOT test actual database operations since Person 1
 * hasn't implemented DBConnection yet. They verify your code structure is correct.
 * 
 * ✅ SIMPLIFIED - No Spring Boot context needed!
 */
class EmployeeServiceImplTest {

    /**
     * Test 1: Verify Employee model can be created (Step 1)
     */
    @Test
    void testEmployeeModelCreation() {
        // Test no-arg constructor
        Employee emp1 = new Employee();
        assertNotNull(emp1, "Employee should be created with no-arg constructor");
        
        // Test all-args constructor
        Employee emp2 = new Employee(1L, "John", "Doe", "john@example.com");
        assertNotNull(emp2, "Employee should be created with all-args constructor");
        assertEquals(1L, emp2.getId(), "ID should be set correctly");
        assertEquals("John", emp2.getFirstName(), "First name should be set correctly");
        assertEquals("Doe", emp2.getLastName(), "Last name should be set correctly");
        assertEquals("john@example.com", emp2.getEmail(), "Email should be set correctly");
    }

    /**
     * Test 2: Verify Employee getters and setters work (Step 1)
     */
    @Test
    void testEmployeeGettersAndSetters() {
        Employee emp = new Employee();
        
        emp.setId(100L);
        emp.setFirstName("Jane");
        emp.setLastName("Smith");
        emp.setEmail("jane@example.com");
        
        assertEquals(100L, emp.getId(), "getId() should return correct value");
        assertEquals("Jane", emp.getFirstName(), "getFirstName() should return correct value");
        assertEquals("Smith", emp.getLastName(), "getLastName() should return correct value");
        assertEquals("jane@example.com", emp.getEmail(), "getEmail() should return correct value");
    }

    /**
     * Test 3: Verify Employee toString() works (Step 1)
     */
    @Test
    void testEmployeeToString() {
        Employee emp = new Employee(1L, "John", "Doe", "john@example.com");
        String result = emp.toString();
        
        assertNotNull(result, "toString() should not return null");
        assertTrue(result.contains("John"), "toString() should contain first name");
        assertTrue(result.contains("Doe"), "toString() should contain last name");
        assertTrue(result.contains("john@example.com"), "toString() should contain email");
    }

    /**
     * Test 4: Verify Employee equals() and hashCode() work (Step 1)
     */
    @Test
    void testEmployeeEqualsAndHashCode() {
        Employee emp1 = new Employee(1L, "John", "Doe", "john@example.com");
        Employee emp2 = new Employee(1L, "John", "Doe", "john@example.com");
        Employee emp3 = new Employee(2L, "Jane", "Smith", "jane@example.com");
        
        assertEquals(emp1, emp2, "Employees with same data should be equal");
        assertNotEquals(emp1, emp3, "Employees with different data should not be equal");
        assertEquals(emp1.hashCode(), emp2.hashCode(), "Equal employees should have same hashCode");
    }

    /**
     * Test 5: Verify EmployeeServiceImpl can be instantiated (Step 2)
     */
    @Test
    void testServiceInstantiation() {
        EmployeeService service = new EmployeeServiceImpl();
        assertNotNull(service, "EmployeeServiceImpl should be instantiable");
    }

    /**
     * Test 6: Verify service methods exist with correct signatures (Steps 3-4)
     */
    @Test
    void testServiceMethodSignatures() throws NoSuchMethodException {
        Class<EmployeeServiceImpl> serviceClass = EmployeeServiceImpl.class;
        
        // Verify createEmployee() exists (Step 3)
        var createMethod = serviceClass.getDeclaredMethod("createEmployee", Employee.class);
        assertEquals(Employee.class, createMethod.getReturnType(), 
            "createEmployee() should return Employee");
        
        // Verify getAllEmployees() exists (Step 4)
        var getAllMethod = serviceClass.getDeclaredMethod("getAllEmployees");
        assertEquals(List.class, getAllMethod.getReturnType(), 
            "getAllEmployees() should return List");
        
        // Verify getEmployeeById() exists (Bonus)
        var getByIdMethod = serviceClass.getDeclaredMethod("getEmployeeById", long.class);
        assertEquals(Optional.class, getByIdMethod.getReturnType(), 
            "getEmployeeById() should return Optional");
        
        // Verify updateEmployee() exists (Bonus)
        var updateMethod = serviceClass.getDeclaredMethod("updateEmployee", long.class, Employee.class);
        assertEquals(Optional.class, updateMethod.getReturnType(), 
            "updateEmployee() should return Optional");
        
        // Verify deleteEmployee() exists (Bonus)
        var deleteMethod = serviceClass.getDeclaredMethod("deleteEmployee", long.class);
        assertEquals(boolean.class, deleteMethod.getReturnType(), 
            "deleteEmployee() should return boolean");
        
        System.out.println("✅ All required service methods exist with correct signatures!");
    }

    /**
     * Test 7: Verify service throws exception when DBConnection not implemented
     * This confirms your code correctly depends on Person 1's work
     */
    @Test
    void testServiceThrowsExceptionWithoutDBConnection() {
        EmployeeService service = new EmployeeServiceImpl();
        Employee emp = new Employee(null, "Test", "User", "test@example.com");
        
        // Should throw exception because Person 1 hasn't implemented DBConnection yet
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.createEmployee(emp);
        });
        
        String message = exception.getMessage();
        assertTrue(message.contains("not implemented") || message.contains("Error"), 
            "Should throw error about DBConnection not being implemented");
        
        System.out.println("✅ Service correctly depends on Person 1's DBConnection!");
    }
}
