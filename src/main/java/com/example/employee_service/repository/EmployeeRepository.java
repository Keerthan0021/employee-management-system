package com.example.employee_service.repository;

import com.example.employee_service.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
    // Custom query method: Spring auto-generates the SQL query based on the method name
    List<Employee> findByDepartment(String department);

    // Find employees by department with pagination
    Page<Employee> findByDepartmentIgnoreCase(String department, Pageable pageable);

    // Search employees whose name contains a keyword (case-insensitive) with pagination
    Page<Employee> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}