###### Inprogress
# Backend Order Service

A production-ready <b>Spring Boot microservice</b> responsible for managing order lifecycle operations in a microservices architecture. The service exposes RESTful APIs for creating, updating, retrieving, and deleting orders, and is fully <b>containerized using Docker</b> to ensure consistency across development, testing, and deployment environments.

The application follows <b>industry-standard layered architecture</b> (Controller, Service, Repository) and is designed to be easily extensible for database integration, security, and cloud deployment. CI pipelines are configured using <b>GitHub Actions</b> to automate builds and ensure code quality.

## Key Highlights:

- RESTful APIs built with Spring Boot
- Clean, company-grade project structure
- In-memory storage (easily replaceable with a real database)
- Dockerized for container-based deployment
- Docker Compose support for multi-service setups
- CI workflow using GitHub Actions
- Ready for cloud and microservices environments

## Tech Stack:
- Java 17
- Spring Boot
- Maven
- Docker & Docker Compose
- GitHub Actions

## Project Structure
<pre>
src/main/java/com/prashant/backendorderservice
├── controller
│   └── OrderController.java
│
├── service
│   ├── OrderService.java
│   └── OrderServiceOperations.java
│
├── dto
│   ├── request
│   │   └── CreateOrderRequest.java
│   │
│   └── response
│       └── OrderResponse.java
│
├── model
│   ├── Order.java
│   └── OrderStatus.java
│
├── repository
│   └── OrderRepository.java
│
├── config
│   └── (future configs: SwaggerConfig, SecurityConfig, etc.)
│
└── BackendOrderServiceApplication.java
</pre>
## CI/CD Workflow
<img width="990" height="604" alt="image" src="https://github.com/user-attachments/assets/3ca6d4d6-d0b2-4325-908f-528656597ff7" />

## Examples
Post : http://localhost:8082/orderplace
<img width="1757" height="505" alt="image" src="https://github.com/user-attachments/assets/93b96099-4026-4678-a021-aee3fc4fe983" />
Get : http://localhost:8082/order?id=13
<img width="1816" height="960" alt="image" src="https://github.com/user-attachments/assets/ad9f7b19-4bc8-4c5e-8a11-51a9dfc1064d" />



