package com.lntproject.employee_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "app.cli.enabled=false")
@ImportAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class EmployeeManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
