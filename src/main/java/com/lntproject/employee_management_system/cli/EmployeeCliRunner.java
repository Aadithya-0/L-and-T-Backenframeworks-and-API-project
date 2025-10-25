package com.lntproject.employee_management_system.cli;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.lntproject.employee_management_system.model.Employee;
import com.lntproject.employee_management_system.service.EmployeeService;

@Component
@ConditionalOnProperty(value = "app.cli.enabled", havingValue = "true", matchIfMissing = true)
public class EmployeeCliRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final Scanner scanner;

    public EmployeeCliRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {
        if (isNonInteractive()) {
            return;
        }
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Choose an option: ").trim();
            switch (choice) {
                case "1" -> handleCreate();
                case "2" -> handleList();
                case "3" -> handleUpdate();
                case "4" -> handleDelete();
                case "5" -> running = false;
                default -> System.out.println("Unknown option. Please select between 1 and 5.");
            }
        }
        System.out.println("Exiting Employee Management System CLI. Goodbye!");
    }

    private void handleCreate() {
        String firstName = readRequired("First name: ");
        String lastName = readRequired("Last name: ");
        String email = readRequired("Email: ");
        Employee created = employeeService.createEmployee(new Employee(null, firstName, lastName, email));
        System.out.println("Employee created with id: " + created.getId());
    }

    private void handleList() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("Registered employees:");
        employees.forEach(employee -> System.out.println("- " + describe(employee)));
    }

    private void handleUpdate() {
        Optional<Employee> existing = employeeService.getEmployeeById(readId());
        if (existing.isEmpty()) {
            System.out.println("Employee not found.");
            return;
        }
        Employee current = existing.get();
        System.out.println("Updating employee: " + describe(current));
        String firstName = readOptional("First name", current.getFirstName());
        String lastName = readOptional("Last name", current.getLastName());
        String email = readOptional("Email", current.getEmail());
        Employee updated = new Employee(current.getId(), firstName, lastName, email);
        employeeService.updateEmployee(current.getId(), updated).ifPresentOrElse(
                value -> System.out.println("Employee updated."),
                () -> System.out.println("Employee could not be updated."));
    }

    private void handleDelete() {
        long id = readId();
        if (employeeService.deleteEmployee(id)) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private String readRequired(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value is required.");
        }
    }

    private String readOptional(String fieldName, String currentValue) {
        String line = readLine(fieldName + " [" + currentValue + "]: ").trim();
        return line.isEmpty() ? currentValue : line;
    }

    private long readId() {
        while (true) {
            String value = readLine("Enter employee id: ").trim();
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid numeric id.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException("No input available for CLI", ex);
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add Employee");
        System.out.println("2. View Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
    }

    private void printBanner() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("      Employee Management System CLI");
        System.out.println("========================================");
    }

    private String describe(Employee employee) {
        return "[id=" + employee.getId() + ", "
                + employee.getFirstName() + " " + employee.getLastName()
                + ", email=" + employee.getEmail() + "]";
    }

    private boolean isNonInteractive() {
        return System.console() == null && Boolean.getBoolean("app.cli.requireConsole");
    }
}
