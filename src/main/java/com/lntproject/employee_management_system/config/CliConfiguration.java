package com.lntproject.employee_management_system.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lntproject.employee_management_system.model.Employee;
import com.lntproject.employee_management_system.service.EmployeeService;

@Configuration
public class CliConfiguration {

    @Bean
    @ConditionalOnMissingBean(EmployeeService.class)
    public EmployeeService inMemoryEmployeeService() {
        return new InMemoryEmployeeService();
    }

    private static final class InMemoryEmployeeService implements EmployeeService {

        private final AtomicLong idSequence = new AtomicLong(1);
        private final Map<Long, Employee> store = new ConcurrentHashMap<>();

        @Override
        public Employee createEmployee(Employee employee) {
            Long requestedId = employee.getId();
            @SuppressWarnings("UnnecessaryUnboxing")
            long id = requestedId != null ? requestedId.longValue() : idSequence.getAndIncrement();
            Employee persisted = copyOf(employee);
            persisted.setId(id);
            store.put(id, persisted);
            return copyOf(persisted);
        }

        @Override
        public List<Employee> getAllEmployees() {
            if (store.isEmpty()) {
                return Collections.emptyList();
            }
            List<Employee> snapshot = new ArrayList<>(store.size());
            store.values().forEach(employee -> snapshot.add(copyOf(employee)));
            snapshot.sort((left, right) -> Long.compare(left.getId(), right.getId()));
            return snapshot;
        }

        @Override
        public Optional<Employee> getEmployeeById(long id) {
            Employee employee = store.get(id);
            return Optional.ofNullable(employee).map(this::copyOf);
        }

        @Override
        public Optional<Employee> updateEmployee(long id, Employee employee) {
            return Optional.ofNullable(store.computeIfPresent(id, (key, existing) -> {
                existing.setFirstName(employee.getFirstName());
                existing.setLastName(employee.getLastName());
                existing.setEmail(employee.getEmail());
                return existing;
            })).map(this::copyOf);
        }

        @Override
        public boolean deleteEmployee(long id) {
            return store.remove(id) != null;
        }

        private Employee copyOf(Employee employee) {
            if (employee == null) {
                return null;
            }
            return new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
        }
    }
}
