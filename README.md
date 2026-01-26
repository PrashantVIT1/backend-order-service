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
src/main/java/com/company/order
├── controller   # REST API controllers
├── service      # Business logic
├── model        # Domain models / entities
├── repository   # Data access layer
├── config       # Application configuration
</pre>
