###### Inprogress
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

## Instruction 
<pre>
Note: 1) Liquibase Integrated just only `orderdb` database creation will be required to start
      2) postgres Username and password should be configured in application.properties /application.yaml 
      file recommended use of AWS secrets manager
</pre>
## Tech Stack:
- Java 17
- Spring Boot
- Maven
- JUnit5
- Liquibase
- sonarqube
- Swagger
- Postman
- Docker & Docker Compose
- GitHub Actions

## Project Structure
<pre>
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

</pre>
## CI/CD Workflow
<img width="990" height="604" alt="image" src="https://github.com/user-attachments/assets/3ca6d4d6-d0b2-4325-908f-528656597ff7" />

## Swagger API documentation
Link: http://localhost:8082/swagger-ui/index.html
<pre>

<img width="1901" height="867" alt="image" src="https://github.com/user-attachments/assets/70819900-adbd-4fec-bbfa-a3ebd5caf365" />

</pre>
## Examples
Post : http://localhost:8082/orderplace
<pre>
<img width="1819" height="957" alt="image" src="https://github.com/user-attachments/assets/f0ea0c44-c8b0-405c-bf50-9c8982587f9a" />
</pre>
Patch : http://localhost:8082/orders/26/status
<pre>
{ "status": "SHIPPED" } 
Status values:👇
<img width="254" height="220" alt="image" src="https://github.com/user-attachments/assets/2ef82f26-6cb9-408c-a7ef-8a91ddefa5d2" />
<img width="1815" height="930" alt="image" src="https://github.com/user-attachments/assets/8d85497b-eb72-4b88-b8dd-eb61bad5fd59" />

</pre>

Get : http://localhost:8082/order?id=17
<pre>
<img width="1816" height="923" alt="image" src="https://github.com/user-attachments/assets/cb4674e7-0a76-4fe6-9876-938922c6f0e0" />
</pre>
Delete : http://localhost:8082/order/remove/17
<pre>
<img width="1817" height="927" alt="image" src="https://github.com/user-attachments/assets/f1f90ea2-4939-4ca2-bf9d-dc4bb5d8bc98" />
</pre>




