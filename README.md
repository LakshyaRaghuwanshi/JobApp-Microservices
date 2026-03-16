# JobApp – Microservices Backend

A Spring Boot-based **Job Management System** built with **Microservices Architecture**.
Allows users to manage job listings, company details, and reviews — each handled by
a dedicated, independently deployable service.

---

## Microservices

| Service          | Description                                      | Port  |
|-----------------|--------------------------------------------------|-------|
| `api-gateway`   | Single entry point, routes to all services       | 8080  |
| `eureka-server` | Service discovery via Netflix Eureka             | 8761  |
| `config-server` | Centralized configuration management             | 8888  |
| `job-ms`        | Manages job listings, aggregates company/review  | 8082  |
| `company-ms`    | Handles company data, consumes review events     | 8081  |
| `review-ms`     | Stores reviews, publishes events via RabbitMQ    | 8083  |

---

## Tech Stack

- **Java 17**, Spring Boot, Spring Cloud
- **API Gateway** – Spring Cloud Gateway
- **Service Discovery** – Netflix Eureka
- **Inter-service Communication** – OpenFeign
- **Async Messaging** – RabbitMQ (producer/consumer)
- **Fault Tolerance** – Resilience4j (Circuit Breaker, Retry, Rate Limiter)
- **Distributed Tracing** – Zipkin + Spring Actuator
- **Database** – PostgreSQL (database-per-service pattern)
- **Containerization** – Docker, Docker Compose

---

## Run Locally

### Prerequisites
- Java 17+, Maven, Docker

### Steps

1. Clone the repository:
git clone https://github.com/LakshyaRaghuwanshi/JobApp-Microservices.git

    cd JobApp-Microservices/sb-JobApp-Microservices


2. Start all services with Docker Compose:

```bash
docker-compose up -d
```

3. Visit Eureka dashboard: http://localhost:8761

---

## Architecture Highlights

- **Database-per-service** pattern — each microservice owns its PostgreSQL schema
- **Eventual consistency** via RabbitMQ async messaging between review-ms and company-ms
- **Resilience4j** circuit breaker prevents cascading failures across services
- **Zipkin** distributed tracing for end-to-end request observability
