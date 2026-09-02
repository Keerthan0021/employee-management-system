# 🚀 Spring Boot Employee Management REST API

[![Java CI with Maven](https://github.com/Keerthan0021/employee-management-system/actions/workflows/ci.yml/badge.svg)](https://github.com/Keerthan0021/employee-management-system/actions/workflows/ci.yml)

A robust, production-ready RESTful backend service built with **Spring Boot 3.4**, **Spring Data JPA**, **MySQL**, and automated **CI/CD via GitHub Actions**. 

This application provides a complete solution for enterprise employee record management, featuring request validation, server-side pagination/sorting, interactive OpenAPI documentation, and automated cloud build testing.

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.4
* **Persistence:** Spring Data JPA & Hibernate
* **Database:** MySQL 8.x
* **Validation:** Jakarta Bean Validation (Hibernate Validator)
* **API Documentation:** Springdoc OpenAPI 3 / Swagger UI
* **Testing:** JUnit 5, Mockito, MockMvc
* **CI/CD:** GitHub Actions (Ubuntu cloud runner)
* **Build Tool:** Apache Maven (Wrapper included)

---

## ✨ Key Features

* **Layered Architecture:** Clear separation of concerns into Controller, Service, Repository, Entity, and Exception layers.
* **Full CRUD Operations:** Manage employee records (Create, Read, Update, Delete) with persistent database sync.
* **Pagination & Sorting:** Server-side pagination and dynamic attribute sorting to efficiently handle large employee datasets.
* **Keyword Search:** Flexible query filtering to search employees by name.
* **Robust Input Validation:** Strict payload constraints using `@Valid`, `@NotBlank`, `@Size`, and `@Positive` annotations.
* **Global Exception Handling:** Centralized `@RestControllerAdvice` delivering consistent, structured JSON responses for `400 Bad Request` and `404 Not Found`.
* **Interactive API Documentation:** Live Swagger UI dashboard for inspecting schemas and executing live requests directly from the browser.
* **Automated CI/CD Pipeline:** GitHub Actions workflow verifying every commit with cloud compilation, dependency caching, and clean test suite execution.

---

## 🏗️ Project Architecture

```text
src/main/java/com/example/employee_service/
 ├── controller/        # REST endpoints (EmployeeController)
 ├── entity/            # JPA entities (Employee)
 ├── exception/         # Custom exceptions & @RestControllerAdvice handler
 ├── repository/        # Spring Data JPA interfaces (EmployeeRepository)
 ├── service/           # Business logic & implementation
 └── EmployeeServiceApplication.java
📡 REST API EndpointsBase URL: http://localhost:8080/api/employeesMethodEndpointDescriptionStatus CodePOST/api/employeesCreate a new employee201 CreatedGET/api/employees/{id}Retrieve employee by ID200 OK / 404 Not FoundGET/api/employeesGet all employees (Supports pagination & sorting)200 OKGET/api/employees/search?keyword={name}Search employees by name200 OKPUT/api/employees/{id}Update existing employee record200 OK / 404 Not FoundDELETE/api/employees/{id}Remove employee by ID200 OK / 404 Not FoundSample JSON Payload (Create / Update):JSON{
  "name": "Alice Johnson",
  "department": "Engineering",
  "salary": 95000.00
}
📖 Interactive API Documentation (Swagger)Once the application is running locally, access the interactive Swagger UI dashboard at:Plaintexthttp://localhost:8080/swagger-ui/index.html
Raw OpenAPI JSON specification:Plaintexthttp://localhost:8080/v3/api-docs
⚙️ Getting StartedPrerequisitesJava 17 or higherMySQL Server running on port 3306Git1. Clone the RepositoryBashgit clone [https://github.com/Keerthan0021/employee-management-system.git](https://github.com/Keerthan0021/employee-management-system.git)
cd employee-management-system
2. Configure DatabaseUpdate src/main/resources/application.properties with your MySQL credentials:Propertiesspring.datasource.url=jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3. Build and RunUsing the Maven Wrapper:PowerShell# Run the application
.\mvnw.cmd spring-boot:run

# Execute automated tests
.\mvnw.cmd test
🔄 CI/CD PipelineAutomated integration runs via GitHub Actions (.github/workflows/ci.yml) on every push to main:Checks out repository code into an Ubuntu cloud runner.Sets up Eclipse Temurin JDK 17 with Maven caching.Automatically executes compilation and testing (./mvnw clean test).Reflects live status on the repository's dynamic build badge.