package com.example.employee_service.repository;

import com.example.employee_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
    // Custom query method: Spring auto-generates the SQL query based on the method name
    List<Employee> findByDepartment(String department);
}