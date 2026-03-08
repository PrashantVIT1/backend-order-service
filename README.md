# Backend Order Service 
![Java](https://img.shields.io/badge/Java-17-blue)  ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)  ![Build](https://img.shields.io/github/actions/workflow/status/PrashantVIT1/backend-order-service/maven.yml)  [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) ![Docker](https://img.shields.io/badge/Docker-enabled-blue)

A production-ready **Spring Boot microservice** designed to manage the lifecycle of orders in a distributed microservices architecture.  
The service exposes RESTful APIs for creating, updating, retrieving, and deleting orders, and is fully **containerized using Docker** to ensure consistent behavior across development, testing, and deployment environments.

The application follows **industry-standard layered architecture** (Controller, Service, Repository) and is designed to be easily extensible for database integration, security, and cloud deployment. CI pipelines are configured using **GitHub Actions** to automate builds and ensure code quality.

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [System Design Considerations](#system-design-considerations)
- [Key Highlights](#key-highlights)
- [Local Setup Instructions](#local-setup-instructions)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [CI/CD Workflow](#ci-cd-workflow)
- [Swagger API Documentation](#swagger-api-documentation)
- [Endpoints](#endpoints)
- [Future Improvements](#future-improvements)
- [License](#license)

## Architecture Overview

```mermaid
graph TD
    Client[Client / API Consumer]

    subgraph API Layer
        Controller[Order Controller]
        DTO[DTOs - Request / Response]
    end

    subgraph Business Layer
        Service[Order Service]
    end

    subgraph Data Layer
        Repository[Order Repository]
        DB[(PostgreSQL Database)]
    end

    Client -->|HTTP Request| Controller
    Controller -->|Request DTO| DTO
    DTO -->|Validated Data| Service
    Service -->|JPA Calls| Repository
    Repository -->|SQL Queries| DB
    Service -->|Response DTO| DTO
    DTO -->|HTTP Response| Client
```

The service follows a standard layered architecture:

- **Controller Layer**  
  Handles incoming HTTP requests and delegates processing to the service layer.

- **DTO Layer**  
  Defines request and response objects used to transfer data between the API layer and business layer.

- **Service Layer**  
  Contains the core business logic and orchestrates application workflows.

- **Repository Layer**  
  Manages persistence and database interaction using Spring Data JPA.

## System Design Considerations

- **Stateless Service** – allows horizontal scaling in containerized environments.
- **DTO Layer** – prevents domain model leakage through API contracts.
- **Liquibase Migrations** – ensures consistent database schema evolution.
- **Testcontainers Integration Tests** – guarantees realistic database testing.
- **CI Pipeline** – ensures build reliability and automated validation.

## Key Highlights

- RESTful APIs built with Spring Boot
- Clean layered architecture (Controller, Service, Repository)
- DTO-based API design for clear separation of concerns
- PostgreSQL persistence with Liquibase database migrations
- Containerized deployment using Docker
- Integration testing using Testcontainers
- CI pipeline implemented with GitHub Actions
- OpenAPI/Swagger documentation for API exploration
- Designed following microservices and cloud-ready best practices

## Local Setup Instructions

### 1. Prerequisites

Ensure the following are installed:

- Java 17  
- Maven  
- PostgreSQL  
- Docker (required for running integration tests using Testcontainers)

---

### 2. Database Setup

Create a PostgreSQL database:
```sql
CREATE DATABASE orderdb;
```
No manual table creation is required. The database schema is automatically managed through Liquibase migrations during application startup.

---

### 3. Configure Database Credentials
```properties
Update your application.properties file:
spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

In production environments, credentials should be managed using environment variables or a secrets management service instead of hardcoding values.

---

### 4. Run the Application
Build and run the application:
```bash
mvn clean install
```
```bash
mvn spring-boot:run
```

The application will start at: 
```text
http://localhost:8082
```
Swagger API documentation will be available at: 
```text
http://localhost:8082/swagger-ui/index.html
```

---

### 5. Running Tests (Including Integration Tests)

To execute all unit and integration tests:
```bash
mvn test
```

Integration tests use Testcontainers, which automatically:

- Spins up a PostgreSQL Docker container
- Applies Liquibase migrations
- Tears down the container after execution.
- Docker must be running locally for integration tests to execute successfully.

## Tech Stack:
| Category           | Technology             |
| ------------------ | ---------------------- |
| Language           | Java 17                |
| Framework          | Spring Boot            |
| Build Tool         | Maven                  |
| Database           | PostgreSQL             |
| Testing            | JUnit5, Testcontainers |
| API Documentation  | Swagger / OpenAPI      |
| Database Migration | Liquibase              |
| Containerization   | Docker, Docker Compose |
| CI/CD              | GitHub Actions         |
| Code Quality       | SonarQube              |
| API Testing        | Postman                |


## Project Structure
```text
backend-order-service
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/prashant/backendorderservice
│   │   │       ├── controller
│   │   │       │   └── OrderController.java
│   │   │       │
│   │   │       ├── service
│   │   │       │   ├── OrderService.java
│   │   │       │   └── OrderServiceOperations.java
│   │   │       │
│   │   │       ├── dto
│   │   │       │   ├── request
│   │   │       │   │   ├── CreateOrderRequest.java
│   │   │       │   │   └── UpdateOrderStatusRequest.java
│   │   │       │   │
│   │   │       │   └── response
│   │   │       │       ├── ErrorResponse.java
│   │   │       │       ├── OrderResponse.java
│   │   │       │       └── UpdateOrderStatusResponse.java
│   │   │       │
│   │   │       ├── model
│   │   │       │   ├── Order.java
│   │   │       │   └── OrderStatus.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │   └── OrderRepository.java
│   │   │       │
│   │   │       ├── exception
│   │   │       │   ├── BusinessException.java   
│   │   │       │   ├── OrderNotFoundException.java        
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       │
│   │   │       ├── config      
│   │   │       │   └── OpenApiConfig.java
│   │   │       └── BackendOrderServiceApplication.java
│   │   │
│   │   └── resources
│   │       ├── db.changelog
│   │       │   ├── changes
│   │       │   │   └── 001-create-orders-table.yaml
│   │       │   └── db.changelog-master.yaml    
│   │       └── application.properties
│   │
│   └── test
│       ├── java
│       │   └── com/prashant/backendorderservice
│       │       ├── controller
│       │       │   └── OrderControllerTest.java
│       │       │
│       │       ├── service
│       │       │   └── OrderServiceTest.java
│       │       │
│       │       ├── repository
│       │       │   └── OrderRepositoryTest.java
│       │       │      
│       │       ├── integration
│       │       │   └── OrderRepositoryIntegrationTest.java
│       │       │      
│       │       │
│       │       └── BackendOrderServiceApplicationTest.java 
│       │
│       └── resources
│           └── application-test.yml
│
├── pom.xml
└── README.md

```
## CI/CD Workflow

<p align="center">
  <img width="1000" alt="CI/CD Workflow" src="https://github.com/user-attachments/assets/3ca6d4d6-d0b2-4325-908f-528656597ff7"/>
</p>

Continuous Integration is implemented using GitHub Actions. The pipeline automatically:

- Builds the application
- Executes unit and integration tests
- Performs static code analysis
- Ensures code quality before merging changes

## Swagger API documentation

Swagger UI: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

<p align="center">
  <img width="1000" height = "600" alt="Swagger API documentation" src="https://github.com/user-attachments/assets/22a02eac-b98d-46a6-b341-0736fd3bba1f" />
</p>

## Endpoints

| Method | Endpoint            | Status Code | Description              |
|--------|---------------------|:-----------:|--------------------------|
| POST   | /orders             | 201         | Create a new order       |
| PATCH  | /orders/{id}/status | 200         | Update order status      |
| GET    | /orders/{id}        | 200         | Retrieve an order by ID  |
| DELETE | /orders/{id}        | 204         | Delete an order          |


`POST`  http://localhost:8082/orders

Request Body :
```json
{
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}   
```
Status Code: `201`

Response Body :
```json
{
  "id": 15,
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}    
```

Example Reference:
<p align="center">
  <img width="1000" alt="POST method Postman" src="https://github.com/user-attachments/assets/42b6f1c5-fd61-438d-971c-404f043a2255" />
</p>

`PATCH` http://localhost:8082/orders/{id}/status

Allowed Status Values:
```text
    CREATED,
    PROCESSING,
    SHIPPED,
    COMPLETED,
    CANCELLED
```

Request Body :
```json
{ 
      "status": "SHIPPED" 
} 
```
Status Code: `200`

Response Body :
```json
{
  "id": 15,
  "status": "SHIPPED",
  "updatedAt": "2026-02-06T17:28:06.2548106"
}   
```

Example Reference:

<p align="center">
  <img width="1000" alt="PATCH method Postman" src="https://github.com/user-attachments/assets/46e9f47a-ee47-4a8f-8e1f-cfb89ed824bd" />
</p>


`GET`  http://localhost:8082/orders/{id}

Request Body :
```json
      
```
Status Code: `200`

Response Body :
```json
{
  "id": 15,
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}    
```

Example Reference:

<p align="center">
  <img width="1000" alt="image" src="https://github.com/user-attachments/assets/0b4a1722-adc4-4467-b938-84d95275457d" />
</p>

`DELETE` http://localhost:8082/orders/{id}

Request Body :
```json
      
```
Status Code: `204`

Response Body :
```json
      
```

Example Reference:

<p align="center">
  <img width="1000" alt="DELETE method Postman" src="https://github.com/user-attachments/assets/99987431-092c-4687-adc7-ed6f3b5a8d75" />
</p>

## Future Improvements

- Add authentication and authorization using Spring Security
- Introduce distributed tracing using OpenTelemetry
- Implement event-driven communication using Kafka
- Deploy using Kubernetes for scalable container orchestration
- Add caching using Redis for improved performance

## License

MIT © Prashant Raj
