# 🚀 Spring Boot Employee Management REST API

[![Java CI with Maven](https://github.com/Keerthan0021/employee-management-system/actions/workflows/ci.yml/badge.svg)](https://github.com/Keerthan0021/employee-management-system/actions/workflows/ci.yml)

A robust, production-ready RESTful API built with Spring Boot 3, Spring Data JPA, MySQL, and Swagger/OpenAPI documentation.

## ✨ Key Features
- **Full CRUD Operations**: Manage employee records (Create, Read, Update, Delete).
- **Database Integration**: Persisted using MySQL with Spring Data JPA & Hibernate ORM.
- **Robust Input Validation**: DTO/Bean validation using `@Valid`, `@NotBlank`, and `@Min` annotations.
- **Global Exception Handling**: Centralized `@RestControllerAdvice` delivering structured JSON error responses (`400 Bad Request`, `404 Not Found`).
- **Interactive API Documentation**: Live Swagger UI dashboard for real-time testing and schema inspection.

## 🛠️ Tech Stack
- **Backend Framework**: Java 17+, Spring Boot 3
- **ORM / Persistence**: Spring Data JPA, Hibernate
- **Database**: MySQL
- **Documentation**: Springdoc OpenAPI / Swagger UI
- **Build Tool**: Maven

## 📌 API Endpoints

| HTTP Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/employees` | Create a new employee | `201 Created` |
| `GET` | `/api/employees` | Retrieve all employees | `200 OK` |
| `GET` | `/api/employees/{id}` | Retrieve employee by ID | `200 OK` / `404 Not Found` |
| `PUT` | `/api/employees/{id}` | Update existing employee details | `200 OK` / `404 Not Found` |
| `DELETE` | `/api/employees/{id}` | Delete employee by ID | `200 OK` / `404 Not Found` |

## 🚀 Running the Project
1. Clone the repository:
   ```bash
   git clone [https://github.com/YOUR_GITHUB_USERNAME/employee-management-system.git](https://github.com/Keerthan_0021/employee-management-system.git)