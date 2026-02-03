###### Inprogress
# Backend Order Service

A production-ready <b>Spring Boot microservice</b> responsible for managing order lifecycle operations in a microservices architecture. The service exposes RESTful APIs for creating, updating, retrieving, and deleting orders, and is fully <b>containerized using Docker</b> to ensure consistency across development, testing, and deployment environments.

The application follows <b>industry-standard layered architecture</b> (Controller, Service, Repository) and is designed to be easily extensible for database integration, security, and cloud deployment. CI pipelines are configured using <b>GitHub Actions</b> to automate builds and ensure code quality.

## 🚀 Key Highlights

- RESTful APIs developed using Spring Boot
- Clean, scalable, company-grade layered architecture
- DTO-based design for clear separation between API and domain models
- PostgreSQL persistence (easy to switch to MySQL)
- Dockerized application for containerized deployment
- Docker Compose support for multi-service environments
- CI pipeline implemented using GitHub Actions
- Follows industry best practices for microservices and cloud readiness

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
│   │   ├── CreateOrderRequest.java
│   │   └── UpdateOrderStatusRequest.java
│   │
│   └── response
│       ├── OrderResponse.java       
│       └── UpdateOrderStatusResponse.java
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
<pre>
<img width="1819" height="957" alt="image" src="https://github.com/user-attachments/assets/f0ea0c44-c8b0-405c-bf50-9c8982587f9a" />
</pre>
Patch : http://localhost:8082/orders/17
<pre>
{ "status": "PROCESSING" } 
Status values:👇
<img width="254" height="220" alt="image" src="https://github.com/user-attachments/assets/2ef82f26-6cb9-408c-a7ef-8a91ddefa5d2" />
<img width="1819" height="926" alt="image" src="https://github.com/user-attachments/assets/f3464126-2f7a-4f34-96a0-8898c9d70eae" />
</pre>

Get : http://localhost:8082/order?id=17
<pre>
<img width="1816" height="923" alt="image" src="https://github.com/user-attachments/assets/cb4674e7-0a76-4fe6-9876-938922c6f0e0" />
</pre>
Delete : http://localhost:8082/order/remove/17
<pre>
<img width="1817" height="927" alt="image" src="https://github.com/user-attachments/assets/f1f90ea2-4939-4ca2-bf9d-dc4bb5d8bc98" />
</pre>




