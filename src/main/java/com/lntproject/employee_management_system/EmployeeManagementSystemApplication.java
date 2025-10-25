package com.lntproject.employee_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// ⚠️ Person 1: Remove the 'exclude' parameter after configuring database connection
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

}
