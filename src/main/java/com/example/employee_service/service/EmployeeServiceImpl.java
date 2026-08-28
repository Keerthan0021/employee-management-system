package com.example.employee_service.service;

import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.ResourceNotFoundException;
import com.example.employee_service.repository.EmployeeRepository;
import com.example.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existingEmployee);
    }

    @Override
    public Page<Employee> getAllEmployeesPaginated(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> getEmployeesByDepartment(String department, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByDepartmentIgnoreCase(department, pageable);
    }

    @Override
    public Page<Employee> searchEmployeesByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
}