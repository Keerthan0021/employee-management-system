# 🏢 Employee Management System (EMS)

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)](https://aws.amazon.com/ec2/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger_UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)

A containerized, cloud-deployed RESTful microservice built with **Spring Boot 3**, **Spring Data JPA**, and **MySQL 8**. The system handles comprehensive employee lifecycle operations with support for pagination, sorting, dynamic keyword search, and department filtering. Orchestrated using **Docker Compose** and deployed live on an **AWS EC2 (Ubuntu)** instance.

---

## 🚀 Live Demo & Interactive Documentation

* **Interactive Swagger UI:** [http://15.252.121.75:8080/swagger-ui/index.html](http://15.252.121.75:8080/swagger-ui/index.html)
* **Raw OpenAPI 3 Specification:** [http://15.252.121.75:8080/v3/api-docs](http://15.252.121.75:8080/v3/api-docs)

*(Note: Live endpoint availability is subject to active EC2 host instance scheduling).*

---

## 🏗️ Architecture & System Design

The application follows a standard N-tier architecture containerized within an isolated Docker bridge network:

```
                  ┌──────────────────────────────────────────────┐
                  │               AWS EC2 (Linux)                │
                  │                                              │
 Internet ──8080──┼─► [ Spring Boot 3 Container ] (Port 8080)   │
 (Browser/Client) │           │                                  │
                  │           │ Spring Data JPA / Hibernate      │
                  │           ▼                                  │
                  │   [ MySQL 8 Container ] (Port 3306)          │
                  │           ▲                                  │
                  │           │                                  │
                  │   [ Named Volume: mysql_data ]               │
                  └──────────────────────────────────────────────┘
```

* **Application Tier:** Spring Boot container running OpenJDK 17/21, exposing REST APIs and Swagger UI.
* **Database Tier:** Isolated MySQL 8 container with data persistence across container recycles via named volume (`mysql_data`).
* **High Availability:** Configured with `restart: always` to ensure seamless service recovery upon host reboots or unexpected process termination.

---

## 🛠️ Technology Stack

| Layer | Technology | Purpose |
|---|---|---|
| **Backend Framework** | Spring Boot 3.x | Application scaffolding and dependency injection |
| **Persistence** | Spring Data JPA / Hibernate | ORM, query derivation, transaction management |
| **Database** | MySQL 8.0 | Relational database engine |
| **Documentation** | Springdoc OpenAPI (Swagger UI) | Interactive documentation and API contract testing |
| **Containerization** | Docker & Docker Compose | Multi-container orchestration and environment isolation |
| **Cloud Hosting** | AWS EC2 (t2/t3.micro) | Remote compute and public IP routing |
| **VCS & Build** | Git & Maven | Version control and dependency lifecycle build management |

---

## 📡 REST API Reference

All employee resources are mapped under the `/api/employees` prefix.

| Method | Endpoint | Description | Query / Path Parameters |
|---|---|---|---|
| `POST` | `/api/employees` | Create a new employee | N/A (JSON Body required) |
| `GET` | `/api/employees` | Get all employees (Paginated) | `page` (default: 0), `size` (default: 5), `sortBy` (default: id), `direction` (default: asc) |
| `GET` | `/api/employees/{id}` | Get employee by ID | `id` (Long, Path Variable) |
| `PUT` | `/api/employees/{id}` | Update existing employee | `id` (Long, Path Variable), JSON Body |
| `DELETE` | `/api/employees/{id}` | Delete employee by ID | `id` (Long, Path Variable) |
| `GET` | `/api/employees/search` | Search by name / keyword | `keyword` (String, Query Param), `page`, `size` |
| `GET` | `/api/employees/department/{department}` | Filter by department | `department` (String, Path Variable), `page`, `size` |

### Sample Payload (`POST` / `PUT`)

```json
{
  "name": "Arjun Sharma",
  "department": "Engineering",
  "salary": 95000.0
}
```

---

## 💻 Local Development Setup

### Prerequisites
* **Java 17** or later
* **Maven 3.8+**
* **Docker & Docker Desktop** (if running via containers)
* **Git**

### 1. Clone the Repository
```bash
git clone [https://github.com/](https://github.com/)<your-username>/employee-management-system.git
cd employee-management-system
```

### 2. Run via Docker Compose (Recommended)
Build and start both the Spring Boot app and MySQL database with one command:

```bash
docker compose up --build -d
```

Once running, access Swagger UI at:
👉 **http://localhost:8080/swagger-ui/index.html**

To view logs:
```bash
docker compose logs -f app
```

To stop containers (preserving database data):
```bash
docker compose down
```

---

## ☁️ Cloud Deployment (AWS EC2)

The application is deployed on an Ubuntu EC2 instance using the following deployment pattern:

### 1. Configure AWS Security Group
Add an **Inbound Rule** to permit external web traffic to the application:
* **Type:** Custom TCP
* **Port Range:** `8080`
* **Source:** `0.0.0.0/0` (Anywhere-IPv4)

### 2. SSH into Instance
```bash
ssh -i "your-key.pem" ubuntu@<your-ec2-public-ip>
```

### 3. Deploy Multi-Container Architecture
```bash
git clone [https://github.com/](https://github.com/)<your-username>/employee-management-system.git
cd employee-management-system
docker compose up --build -d
```

### 4. Verify Service Health
```bash
docker compose ps
curl -i http://localhost:8080/v3/api-docs
```

---

## 🧪 Production Engineering & Troubleshooting Notes

Building and shipping this project to a cloud environment surfaced several real-world DevOps challenges:

* **Resource-Constrained Instances:** Running MySQL 8 alongside a JVM on a 1 GB RAM instance (`t2/t3.micro`) required tuning container startup lifecycles and resolving memory limits.
* **Volume Persistence & Disk Hygiene:** Handled `Error number 28: No space left on device` during heavy multi-stage Docker builds by optimizing layer caching, using automated volume pruning (`docker system prune`), and isolating corrupted volume initialization.
* **Network & Database Handshakes:** Configured `depends_on` and resilient JDBC parameters (`createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true`) to avoid race conditions between Spring Boot connection pooling and MySQL initialization.
* **Auto-Recovery:** Implemented `restart: always` on all service manifests so containers recover automatically across server maintenance cycles.

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).