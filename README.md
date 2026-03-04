# Backend Order Service

A production-ready <b>Spring Boot microservice</b> responsible for managing order lifecycle operations in a microservices architecture. The service exposes RESTful APIs for creating, updating, retrieving, and deleting orders, and is fully <b>containerized using Docker</b> to ensure consistency across development, testing, and deployment environments.

The application follows <b>industry-standard layered architecture</b> (Controller, Service, Repository) and is designed to be easily extensible for database integration, security, and cloud deployment. CI pipelines are configured using <b>GitHub Actions</b> to automate builds and ensure code quality.

##  Key Highlights

- RESTful APIs developed using Spring Boot
- Clean, scalable, company-grade layered architecture
- DTO-based design for clear separation between API and domain models
- PostgreSQL persistence (easy to switch to MySQL)
- Dockerized application for containerized deployment
- Docker Compose support for multi-service environments
- CI pipeline implemented using GitHub Actions
- Follows industry best practices for microservices and cloud readiness

## Local Setup Instructions

### 1. Prerequisites

Ensure the following are installed:

- Java 17  
- Maven  
- PostgreSQL  
- Docker (required only for integration tests via Testcontainers)

---

### 2. Database Setup

Create a PostgreSQL database:
```sql
CREATE DATABASE orderdb;
```
No manual table creation is required. The database schema is automatically managed using Liquibase during application startup.

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
- Java 17
- Spring Boot
- Maven
- JUnit5
- Liquibase
- SonarQube
- Swagger
- Postman
- Docker & Docker Compose
- GitHub Actions

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
│       │       │   └── OrderRepositoryIntegrationTest
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

<img width="990" height="604" alt="image" src="https://github.com/user-attachments/assets/3ca6d4d6-d0b2-4325-908f-528656597ff7" />

## Swagger API documentation

Link: http://localhost:8082/swagger-ui/index.html


<img width="1901" height="867" alt="image" src="https://github.com/user-attachments/assets/70819900-adbd-4fec-bbfa-a3ebd5caf365" />


## Endpoints
`POST`  http://localhost:8082/orderplace

Request Body :
```json
{
  "customerId": 14,
  "description": "Medicine and Hospital equipments"
}   
```
Response Body :
```json
{
  "id": 17,
  "customerId": 14,
  "description": "Medicine and Hospital equipments",
  "status": "CREATED"
}    
```

Example Reference:  

<img width="1819" height="957" alt="image" src="https://github.com/user-attachments/assets/f0ea0c44-c8b0-405c-bf50-9c8982587f9a" />


`PATCH` http://localhost:8082/orders/26/status

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
Response Body :
```json
{
  "id": 26,
  "status": "SHIPPED",
  "updatedAt": "2026-02-06T17:28:06.2548106"
}   
```

Note:
Provide the order ID as a path variable:
```text
http://localhost:8082/orders/{Place order Id here}/status
```
Example Reference:


<img width="1815" height="930" alt="image" src="https://github.com/user-attachments/assets/8d85497b-eb72-4b88-b8dd-eb61bad5fd59" />


`GET`  http://localhost:8082/order?id=17

Request Body :
```json
      
```

Response Body :
```json
{
  "id": 17,
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}    
```

Note:
To retrieve a specific order, provide the id query parameter: 
```text
http://localhost:8082/order?id={specific order id here}
```
Example Reference:

<img width="1816" height="923" alt="image" src="https://github.com/user-attachments/assets/cb4674e7-0a76-4fe6-9876-938922c6f0e0" />

`DELETE` http://localhost:8082/order/remove/17

Request Body :
```json
      
```
Response Body :
```json
      
```
Note:
Provide the order ID as a path variable to delete the order:
```text
http://localhost:8082/order/remove/{Place order Id here}
```

Example Reference:

<img width="1817" height="927" alt="image" src="https://github.com/user-attachments/assets/f1f90ea2-4939-4ca2-bf9d-dc4bb5d8bc98" />


## License

MIT © Prashant Raj
