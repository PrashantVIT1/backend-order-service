# Backend Order Service 
![Java](https://img.shields.io/badge/Java-17-blue)  ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)  ![Build](https://img.shields.io/github/actions/workflow/status/PrashantVIT1/backend-order-service/maven.yml)  [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) ![Docker](https://img.shields.io/badge/Docker-enabled-blue)

A production-ready **Spring Boot microservice** designed to manage the lifecycle of orders in a distributed microservices architecture.  
The service exposes RESTful APIs for creating, updating, retrieving, and deleting orders, and is fully **containerized using Docker** to ensure consistent behavior across development, testing, and deployment environments.

The application follows **industry-standard layered architecture** (Controller, Service, Repository) and is designed to be easily extensible for database integration, security, and cloud deployment. CI pipelines are configured using **GitHub Actions** to automate builds and ensure code quality.

## Features
- RESTful APIs built using Spring Boot
- JWT-based stateless authentication
- Role-Based Access Control (RBAC)
- OAuth2 authentication
  * Google Login
  * GitHub Login
- Spring Security integration
- User-specific order access
- Admin-specific order management
- PostgreSQL database
- Liquibase database migrations
- Docker containerization
- Unit and integration testing
- Testcontainers for database integration tests
- OpenAPI / Swagger documentation
- CI pipeline using GitHub Actions

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [System Design Considerations](#system-design-considerations)
- [Key Highlights](#key-highlights)
- [Local Setup Instructions](#local-setup-instructions)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [CI/CD Workflow](#cicd-workflow)
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

- **Security Layer**
  Handles authentication and authorization using:
  - Spring Security
  - JWT
  - Role-Based Access Control
  - OAuth2

- **Controller Layer**  
  Handles incoming HTTP requests and delegates processing to the service layer.

- **DTO Layer**  
  Defines request and response objects used to transfer data between the API layer and business layer.

- **Service Layer**  
  Contains the core business logic and orchestrates application workflows.

- **Repository Layer**  
  Manages persistence and database interaction using Spring Data JPA.
  
    
## Security Architecture
The application supports multiple authentication mechanisms.

```mermaid
graph TD

    Client[Client]

    Client -->|Username & Password| Login[/auth/login/]
    Login --> AuthService[Authentication Service]
    AuthService --> JWT[Generate JWT Token]
    JWT --> Client

    Client -->|Bearer Token| ProtectedAPI[Protected APIs]
    ProtectedAPI --> JwtFilter[JWT Authentication Filter]
    JwtFilter --> SecurityContext[Spring Security Context]

    Client -->|OAuth2 Login| OAuth2[Google / GitHub]
    OAuth2 --> OAuthSuccess[OAuth2 Success Handler]
    OAuthSuccess --> JWT
```

The authentication mechanisms include:

- Username and password authentication
- JWT-based authentication
- Role-based authorization
- OAuth2 authentication using Google
- OAuth2 authentication using GitHub

#### JWT Authentication

The application uses JSON Web Tokens (JWT) for stateless authentication. After successful login, the server generates a JWT token.

Login
Endpoint
POST /auth/login

Request
```
{
  "username": "testuser",
  "password": "password123"
}
```

Response
```
{
  "token": "<JWT_TOKEN>"
}
```

The client must include the token in subsequent requests.

Authorization: Bearer <JWT_TOKEN>

  
#### JWT Authentication Flow
```mermaid

sequenceDiagram
    participant Client
    participant AuthController
    participant AuthService
    participant JwtUtil
    participant JwtAuthFilter
    participant ProtectedAPI

    Client->>AuthController: POST /auth/login
    AuthController->>AuthService: Authenticate credentials
    AuthService->>JwtUtil: Generate JWT
    JwtUtil-->>AuthService: JWT token
    AuthService-->>AuthController: Authentication response
    AuthController-->>Client: Return JWT token

    Client->>JwtAuthFilter: Request with Bearer token
    JwtAuthFilter->>JwtUtil: Validate JWT
    JwtUtil-->>JwtAuthFilter: Token valid

    JwtAuthFilter->>JwtAuthFilter: Set SecurityContext
    JwtAuthFilter->>ProtectedAPI: Continue request
    ProtectedAPI-->>Client: Return response

```

#### Role-Based Authorization

The application implements Role-Based Access Control (RBAC) to control access to protected resources.

Different API endpoints are accessible based on the authenticated user's role.
- User APIs

    Endpoints under:
    ```
    /user/**
    ```
    
    are intended for authenticated users. Users can manage their own orders.
    
    Examples:
    ```
    POST   /user/orders
    GET    /user/orders
    GET    /user/orders/{id}
    PATCH  /user/orders/{id}/status
    DELETE /user/orders/{id}
    ```

- Admin APIs
    Endpoints under:
    ```
    /admin/**
    ```
    are restricted to users with administrative privileges.
    
    Examples:
    ```
    GET    /admin/orders
    GET    /admin/orders/{id}
    PATCH  /admin/orders/{id}/status
    DELETE /admin/orders/{id}
    ```
#### OAuth2 Authentication

The application supports OAuth2 authentication through external providers.
Currently supported providers:

* Google
* GitHub
  
OAuth2 allows users to authenticate using an existing account instead of creating a traditional username and password.

#### OAuth2 Flow

```mermaid
sequenceDiagram
    participant User
    participant Application
    participant Provider as Google/GitHub
    participant OAuthHandler as OAuth2 Success Handler
    participant JwtUtil

    User->>Application: Start OAuth2 login
    Application->>Provider: Redirect to provider

    User->>Provider: Authenticate
    Provider-->>Application: OAuth2 callback

    Application->>OAuthHandler: OAuth2 authentication successful
    OAuthHandler->>JwtUtil: Generate JWT token
    JwtUtil-->>OAuthHandler: JWT token

    OAuthHandler-->>User: Authentication successful with JWT
```
#### OAuth2 Provider Selection Flow
Users can authenticate using their Google or GitHub account but with can be extended to other provider as well with minimal changes.

```mermaid
flowchart LR
    A[Client] --> B{Choose Provider}

    B -->|Google| C["Google Login<br/>/oauth2/authorization/google"]
    B -->|GitHub| D["GitHub Login<br/>/oauth2/authorization/github"]

    C --> E[Spring Security OAuth2]
    D --> E

    E --> F[OAuth2 Success Handler]
    F --> G[Generate JWT Token]
    G --> H[Client Receives JWT]
```
#### Signup
Endpoint
POST /auth/signup

Request
```
{
  "username": "testuser",
  "password": "password123"
}
```
Success Response
```
{
  "id": 1,
  "username": "testuser"
}
```

#### Login
Endpoint
POST /auth/login

Request
```
{
  "username": "testuser",
  "password": "password123"
}
```
Success Response
```
{
  "token": "<JWT_TOKEN>"
}
```

Use the token for protected endpoints:
Authorization: Bearer <JWT_TOKEN>

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
- JWT based authentication and authorization using Spring Security

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
Update your application.properties file:
```properties
    spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/testdb}
    spring.datasource.username=${DB_USER:root}
    spring.datasource.password=${DB_PASSWORD:password}
    jwt.secret=${JWT_SECRET:dev-secret}
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
backend-order-service/
└── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.prashant.backendorderservice/
│   │   │       ├── auth/
│   │   │       │   ├── config/
│   │   │       │   │   ├── swagger/
│   │   │       │   │   │   └── AuthControllerDocs.java
│   │   │       │   │   ├── AppConfig.java
│   │   │       │   │   ├── OAuth2SuccessHandler.java
│   │   │       │   │   └── WebSecurityConfig.java
│   │   │       │   ├── controller/
│   │   │       │   │   └── AuthController.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── request/
│   │   │       │   │   │   └── LoginRequest.java
│   │   │       │   │   └── response/
│   │   │       │   │       ├── LoginResponse.java
│   │   │       │   │       └── SignupResponse.java
│   │   │       │   ├── entity/
│   │   │       │   │   ├── type/
│   │   │       │   │   │   └── AuthProvider.java
│   │   │       │   │   └── User.java
│   │   │       │   ├── exception/
│   │   │       │   │   ├── AuthExceptionHandler.java
│   │   │       │   │   ├── CustomAuthEntryPoint.java
│   │   │       │   │   ├── InvalidCredentialsException.java
│   │   │       │   │   └── UserAlreadyExistsException.java
│   │   │       │   ├── filter/
│   │   │       │   │   └── JwtAuthFilter.java
│   │   │       │   ├── repository/
│   │   │       │   │   └── UserRepository.java
│   │   │       │   ├── service/
│   │   │       │   │   ├── AuthService.java
│   │   │       │   │   └── CustomUserDetailsService.java
│   │   │       │   └── util/
│   │   │       │       └── AuthUtil.java
│   │   │       ├── orders/
│   │   │       │   ├── config/
│   │   │       │   │   └── swagger/
│   │   │       │   │       ├── OrderControllerDocs.java
│   │   │       │   │       └── UserOrderControllerDocs.java
│   │   │       │   ├── controller/
│   │   │       │   │   ├── OrderController.java
│   │   │       │   │   └── UserOrderController.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── request/
│   │   │       │   │   │   ├── CreateOrderRequest.java
│   │   │       │   │   │   └── UpdateOrderStatusRequest.java
│   │   │       │   │   └── response/
│   │   │       │   │       ├── OrderResponse.java
│   │   │       │   │       └── UpdateOrderStatusResponse.java
│   │   │       │   ├── entity/
│   │   │       │   │   ├── Order.java
│   │   │       │   │   └── OrderStatus.java          (enum)
│   │   │       │   ├── exception/
│   │   │       │   │   ├── custom/
│   │   │       │   │   │   ├── BusinessException.java
│   │   │       │   │   │   ├── OrderNotFoundException.java
│   │   │       │   │   │   └── OrderStatusInvalidException.java
│   │   │       │   │   └── OrdersExceptionHandler.java
│   │   │       │   ├── repository/
│   │   │       │   │   └── OrderRepository.java
│   │   │       │   ├── serializer/
│   │   │       │   │   └── OrderStatusDeserializer.java
│   │   │       │   └── service/
│   │   │       │       ├── OrderService.java
│   │   │       │       ├── OrderServiceOperations.java
│   │   │       │       ├── UsersOrderService.java
│   │   │       │       └── UsersOrderServiceOperations.java
│   │   │       ├── shared/
│   │   │       │   ├── config/
│   │   │       │   │   ├── exception/
│   │   │       │   │   │   └── ExceptionHandlerOrder.java
│   │   │       │   │   └── swagger/
│   │   │       │   │       ├── GlobalExceptionHandlerDocs.java
│   │   │       │   │       └── OpenApiConfig.java
│   │   │       │   ├── dto/
│   │   │       │   │   └── ErrorResponse.java
│   │   │       │   └── exception/
│   │   │       │       ├── GlobalExceptionHandler.java
│   │   │       │       └── RequestExceptionHandler.java
│   │   │       └── BackendOrderServiceApplication.java
│   │   └── resources/
│   │       ├── db.changelog/
│   │       │   ├── changes/
│   │       │   │   ├── 001-create-app-user-table.yaml
│   │       │   │   └── 001-create-orders-table.yaml
│   │       │   └── db.changelog-master.yaml
│   │       ├── application.yml
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com.prashant.backendorderservice/
│               ├── auth/
│               │   └── support/
│               │       ├── AuthenticatedE2ETest.java
│               │       └── SecuredControllerTest.java
│               ├── orders/
│               │   ├── controller/
│               │   │   ├── OrderControllerTest.java
│               │   │   └── UsersOrderControllerTest.java
│               │   ├── integration/
│               │   │   ├── OrderE2ETest.java
│               │   │   └── OrderRepositoryIntegrationTest.java
│               │   ├── repository/
│               │   │   └── OrderRepositoryTest.java
│               │   └── service/
│               │       ├── OrderServiceTest.java
│               │       └── UsersOrderServiceTest.java
│               └── BackendOrderServiceApplicationTests.java
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


<img width="900" height = "600" alt="Swagger API documentation" src="https://github.com/user-attachments/assets/c445ff46-869b-45ab-bb43-5d33801b051f" />

Instructions to use JWT Token in swagger 

1. Create user by using 

`POST`  http://localhost:8082/auth/signup

<img width="900" height="865" alt="image" src="https://github.com/user-attachments/assets/1b40786d-5c4f-4465-ad95-bafa314ba6bd" />

2. Go to

`POST`  http://localhost:8082/auth/login

<img width="900" height="802" alt="image" src="https://github.com/user-attachments/assets/47fbfd5a-37a9-4638-9a5b-8cae66f216de" />

then copy this JWT token

<img width="900" height="171" alt="image" src="https://github.com/user-attachments/assets/5ee8224d-3a1a-493a-a962-22ba13254789" />

3. Now click on Authorize Button in the topleft corner
   
<img width="900" height="227" alt="image" src="https://github.com/user-attachments/assets/1e553021-62fa-4d9e-9e97-eb0bb46ff6c0" />

A popup will open. Paste JWT token here.

<img width="440" height="150" alt="image" src="https://github.com/user-attachments/assets/77bba871-df17-4101-8247-1b65bfbe0dd9" />


4. Then click on Authorize
   
<img width="440" height="150" alt="image" src="https://github.com/user-attachments/assets/538eec43-f53d-4830-ab16-7c300462acf2" />

5. Then this popup will open click on close

<img width="440" height="150" alt="image" src="https://github.com/user-attachments/assets/1acb8044-ea29-4afa-8be0-c9550a50e762" />

6. Now all endpoints can be used.
  
7. You can also logout. Click on Authorize and a popup opens. Then click here.
   
<img width="440" height="150" alt="image" src="https://github.com/user-attachments/assets/26575fc1-2827-4a96-9b53-07e9158eb2c0" />

   
## Endpoints
### Error Response Format

All error responses follow a consistent structure for example:
```json
{
  "timestamp": "2026-03-09T10:15:30",
  "status": 400,
  "error": "INVALID_ENUM_VALUE",
  "message": "Valid Order Status : CREATED, PROCESSING, SHIPPED, COMPLETED, CANCELLED",
  "path": "/orders/15/status"
}
```
### In Detail

#### Authentication
| Method | Endpoint            | Status Code | Description              |
|--------|---------------------|:-----------:|--------------------------|
| POST   | /auth/signup        | 201         | Create a new user        |
| POST   | /auth/login         | 200         | Login an existing user   |

`POST`  http://localhost:8082/auth/signup

Request Body :
```json
{
    "username":"praj12345",
    "password":"best_password_ever123"
}   
```

| Status Code | Reason |
|:-----------:|--------|
| `201` | User created successfully |
| `400` | Empty Username/Password field |
| `409` | Use Different Username |
| `500` | INTERNAL SERVER ERROR |



Response Body :
```json
{
    "id": 25,
    "username": "praj12345"
}  
```
Example Reference:
<p align="center">
  
<img width="1431" height="933" alt="image" src="https://github.com/user-attachments/assets/0a4ab8fe-b8ba-46bd-9957-fb6088f25e5f" />

</p>

`POST`  http://localhost:8082/auth/login

Request Body :
```json
{
  "username": "praj12345",
  "password": "best_password_ever123"
}   
```
| Status Code | Reason |
|:-----------:|--------|
| `200` | User login successfully |
| `400` | Empty Username/Password field |
| `401` | Invalid username or password |
| `500` | INTERNAL SERVER ERROR |


Response Body :
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFqMTIzNDUiLCJ1c2VySWQiOiIyNSIsImlhdCI6MTc3MzY4MDIzNiwiZXhwIjoxNzczNjgwODM2fQ.8AW4aWZA1TcawmlieHuPdW8qgnjMcTuHJHMCpAvTgFN9SVwGWIo4UdHvJ6H7npkuXjueioksKpAVhHSsJsRulg"
} 
```

Example Reference:
<p align="center">
  
<img width="1431" height="935" alt="image" src="https://github.com/user-attachments/assets/42be65e4-71ee-4214-8fb1-81ed7b7cf5be" />

</p>
> Note: All the protected endpoints needs access. To get the access authentication with JWT token is required

> Steps to get the access

>  i) Login and copy the jwt token from response body

> ii) Go to Header add JWT token in the given format below

>     Authorization : Bearer <token>
```text
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmFqMTIzNDUiLCJ1c2VySWQiOiIyIiwiaWF0IjoxNzc0MTkzNzE3LCJleHAiOjE3NzQxOTQzMTd9.oi1EOu-L7AlMg7xC2pytzAwWai7K_As4BA-XD7QAG74uDKs5Q0oWcqWjEZoC7ZbqnGdsK6kucJyl8XiG66cacw
```

#### Orders

| Method | Endpoint                                | Status Code | Description              |
|--------|-----------------------------------------|:-----------:|--------------------------|
| POST   | /user/orders                            | 201         | Create a new order       |
| PATCH  | /user/orders/{id}/status                | 200         | Update order status      |
| GET    | /user/orders                            | 200         | Retrieve all the orders  |
| GET    | /user/orders/{id}                       | 200         | Retrieve an order by ID  |
| DELETE | /user/orders/{id}                       | 204         | Delete an order          |
| PATCH  | /admin/orders/{id}/status               | 200         | Update order status      |
| GET    | /admin/orders                           | 200         | Retrieve all the orders  |
| GET    | /admin/orders/{id}                      | 200         | Retrieve an order by ID  |
| DELETE | /admin/orders/{id}                      | 204         | Delete an order          |


`POST`  http://localhost:8082/user/orders

Request Body :
```json
{
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}   
```
| Status Code | Reason |
|:-----------:|--------|
| `201` | Order created successfully |
| `400` | Invalid request body / missing required fields |

Response Body :
```json
{
  "id": 15,
  "customerId": 14,
  "description": "Medicine and Hospital Equipment",
  "status": "CREATED"
}    
```
> `status` is optional. Defaults to `CREATED` if not provided.

Example Reference:
<p align="center">
  <img width="1000" alt="POST method Postman" src="https://github.com/user-attachments/assets/42b6f1c5-fd61-438d-971c-404f043a2255" />
</p>

`PATCH` http://localhost:8082/admin/orders/{id}/status

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
| Status Code | Reason |
|:-----------:|--------|
| `200` | Status updated successfully |
| `400` | Invalid status value |
| `404` | Order not found with given ID |

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

`GET`  http://localhost:8082/admin/orders

| Status Code | Reason |
|:-----------:|--------|
| `200` | Order retrieved successfully |

Response Body :
```json
[
    {
      "id": 15,
      "customerId": 14,
      "description": "Medicine and Hospital Equipment",
      "status": "CREATED"
    }
]   
```

Example Reference:

<p align="center">
    <img width="1000" alt="image" src="https://github.com/user-attachments/assets/011aa93b-4130-46c7-90d0-80e898b932f5" />
</p>

`GET`  http://localhost:8082/admin/orders/{id}

| Status Code | Reason |
|:-----------:|--------|
| `200` | Order retrieved successfully |
| `404` | Order not found with given ID |

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

`DELETE` http://localhost:8082/admin/orders/{id}

Request Body :

| Status Code | Reason |
|:-----------:|--------|
| `204` | Order deleted successfully |
| `404` | Order not found with given ID |

Response Body :
```json
      
```
> `Response Body` is empty if no error.

Example Reference:

<p align="center">
  <img width="1000" alt="DELETE method Postman" src="https://github.com/user-attachments/assets/99987431-092c-4687-adc7-ed6f3b5a8d75" />
</p>

## Security Highlights
The security implementation demonstrates several important Spring Security concepts:

- Stateless authentication using JWT
- Custom JWT authentication filter
- Spring Security filter chain
- Role-based authorization
- Protected API endpoints
- OAuth2 client integration
- Google OAuth2 login
- GitHub OAuth2 login
- Custom OAuth2 authentication success handling
- Centralized authentication exception handling
- Secure password authentication

## Future Improvements

- Introduce distributed tracing using OpenTelemetry
- Implement event-driven communication using Kafka
- Deploy using Kubernetes for scalable container orchestration
- Add caching using Redis for improved performance

## License

MIT © Prashant Raj
