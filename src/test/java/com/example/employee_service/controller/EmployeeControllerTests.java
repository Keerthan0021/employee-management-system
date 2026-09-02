package com.example.employee_service.controller;

import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.ResourceNotFoundException;
import com.example.employee_service.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Alice Johnson");
        employee.setDepartment("Engineering");
        employee.setSalary(95000.00);
    }

    // 1. Create Employee Test (POST /api/employees)
    @Test
    @DisplayName("Test Create Employee REST API")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())));
    }

    // 2. Get Employee by ID - Success (GET /api/employees/{id})
    @Test
    @DisplayName("Test Get Employee by ID - Success")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        given(employeeService.getEmployeeById(1L)).willReturn(employee);

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())));
    }

    // 3. Get Employee by ID - 404 Not Found (GET /api/employees/{id})
    @Test
    @DisplayName("Test Get Employee by ID - Not Found")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnNotFound() throws Exception {
        given(employeeService.getEmployeeById(2L))
                .willThrow(new ResourceNotFoundException("Employee not found with id: 2"));

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 2L));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // 4. Paginated Employees (GET /api/employees?page=0&size=5)
    @Test
    @DisplayName("Test Get All Employees Paginated")
    public void givenPaginationParams_whenGetAllEmployees_thenReturnPageObject() throws Exception {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        given(employeeService.getAllEmployeesPaginated(0, 5, "id", "asc")).willReturn(page);

        ResultActions response = mockMvc.perform(get("/api/employees")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("direction", "asc"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(employee.getName())));
    }

    // 5. Update Employee (PUT /api/employees/{id})
    @Test
    @DisplayName("Test Update Employee REST API")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedObject() throws Exception {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setName("Alice Updated");
        updatedEmployee.setDepartment("DevOps");
        updatedEmployee.setSalary(110000.00);

        given(employeeService.updateEmployee(eq(1L), any(Employee.class))).willReturn(updatedEmployee);

        ResultActions response = mockMvc.perform(put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedEmployee.getName())))
                .andExpect(jsonPath("$.department", is(updatedEmployee.getDepartment())))
                .andExpect(jsonPath("$.salary", is(updatedEmployee.getSalary())));
    }

    // 6. Delete Employee (DELETE /api/employees/{id})
    @Test
    @DisplayName("Test Delete Employee REST API")
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        willDoNothing().given(employeeService).deleteEmployee(1L);

        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully!"));
    }
}