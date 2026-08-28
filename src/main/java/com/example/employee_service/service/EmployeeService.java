package com.example.employee_service.service;

import com.example.employee_service.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employeeDetails);
    void deleteEmployee(Long id);

    // --- Pagination & Search Methods ---
    Page<Employee> getAllEmployeesPaginated(int page, int size, String sortBy, String direction);
    Page<Employee> getEmployeesByDepartment(String department, int page, int size);
    Page<Employee> searchEmployeesByName(String keyword, int page, int size);
}