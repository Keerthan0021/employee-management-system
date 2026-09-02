package com.example.employee_service.service;

import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.ResourceNotFoundException;
import com.example.employee_service.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Alice Johnson");
        employee.setDepartment("Engineering");
        employee.setSalary(95000.00);
    }

    // --- TEST 1: Save Employee ---
    @Test
    @DisplayName("JUnit test for saveEmployee operation")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // ARRANGE (Given)
        given(employeeRepository.save(employee)).willReturn(employee);

        // ACT (When)
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // ASSERT (Then)
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(1L);
        assertThat(savedEmployee.getName()).isEqualTo("Alice Johnson");
        assertThat(savedEmployee.getDepartment()).isEqualTo("Engineering");
        
        verify(employeeRepository, times(1)).save(employee);

    }

    // --- TEST 2: GET Employee By Id --- (SUCCESS PATH)
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // 1. ARRANGE (Given)
        // We teach the mock repo that: "If someone calls findById(1L), return our Alice object wrapped in Optional"
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // 2. ACT (When)
        // We call the real service method
        Employee savedEmployee = employeeService.getEmployeeById(1L);

        // ASSERT (Then)
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(1L);
        assertThat(savedEmployee.getName()).isEqualTo("Alice Johnson");

        // Verify: Did the service actually call findById(1L) exactly 1 time?
        verify(employeeRepository, times(1)).findById(1L);
    }

    // --- TEST 3: Get Employee By ID (Negative / Exception Scenario) ---
    @Test
    @DisplayName("JUnit test for getEmployeeById - Throws ResourceNotFoundException")
    public void givenInvalidEmployeeId_whenGetEmployeeId_thenThrowException() {
        // 1. ARRANGE (Given)
        given(employeeRepository.findById(2L)).willReturn(Optional.empty());

        // ACT & ASSERT (When & Then)
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(2L);
        });

        // Verify the repository was queried with ID 2L
        verify(employeeRepository, times(1)).findById(2L);      
    }

    // --- TEST 4: Get All Employees Paginated ---
    @Test
    @DisplayName("JUnit test for getAllEmployeesPaginated")
    public void givenPageableParams_whenGetAllEmployeesPaginated_thenReturnEmployeePage () {
        // Given (Arrange): Build expected Pageable and a simulated Page containing 1 employee
        Pageable pageable = PageRequest.of(0, 5, Sort.by("salary").descending());
        Page<Employee> page = new PageImpl<>(List.of(employee));

        given(employeeRepository.findAll(pageable)).willReturn(page);

        // When (Act):  
        Page<Employee> employeePage = employeeService.getAllEmployeesPaginated(0, 5, "salary", "desc");
        
        // Then (Assert)
        assertThat(employeePage).isNotNull();
        assertThat(employeePage.getContent().size()).isEqualTo(1);
        assertThat(employeePage.getContent().get(0).getName()).isEqualTo("Alice Johnson");
        
        verify(employeeRepository, times(1)).findAll(pageable);
    }

    // --- TEST 5: Delete Employee ---
    @Test
    @DisplayName("JUnit test for deleteEmployee operation")
    public void givenEmployeeId_whenDeleteEmployee_thenEmployeeIsDeleted() {
        // Given (Arrange)
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        // When (Act)
        employeeService.deleteEmployee(1L);

        // Then (Assert)
        verify(employeeRepository, timeout(1)).delete(employee);
    }
}