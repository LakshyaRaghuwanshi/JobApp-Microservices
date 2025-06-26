# - JobApp Microservices

A Spring Boot-based **Job Management System** built using **Microservices Architecture**. This project allows users to view job listings, company details, and user reviews — each handled by its own dedicated service.

---

## - Microservices Included

| Service       | Description                                  | Port  |
|--------------|----------------------------------------------|-------|
| `job-ms`     | Manages jobs & aggregates company/review data| 8082  |
| `company-ms` | Handles company-related data                 | 8081  |
| `review-ms`  | Stores and retrieves company reviews         | 8083  |
| `eureka-server` | Service discovery via Netflix Eureka       | 8761  |

---

## ️- Tech Stack

- Java 17
- Spring Boot
- Spring Cloud Netflix Eureka
- Spring Data JPA
- PostgreSQL
- REST APIs
- Docker (for DB services)
- Git & GitHub (Mono-repo)

---

## 🐳 Docker Setup for PostgreSQL & pgAdmin

To start PostgreSQL and pgAdmin locally using Docker:

### 1. Run Docker Compose

```bash
docker-compose up -d
```

### 2. Default Credentials
   #### PostgreSQL : 

- Username: embarkx

- Password: embarkx

#### pgAdmin :
- URL: http://localhost:5050

- Email: admin@admin.com

- Password: admin

### 3. Add Server in pgAdmin:
- Host name/address: postgres (container name)

- Port: 5432

- Username: embarkx

- Password: embarkx

## - Getting Started (Dev)
### Prerequisites

- Java 17+

- Maven

- Docker

### Steps
#### 1. Clone the repository:

```bash
git clone https://github.com/LakshyaRaghuwanshi/JobApp-Microservices.git
cd JobApp-Microservices
````

#### 2. Start Docker containers:

```bash
docker-compose up -d
```

#### 3. Start services (via IntelliJ or terminal):

- Start eureka-server first.

- Then start: company-ms, review-ms, and job-ms.

- Visit Eureka dashboard at: http://localhost:8761


## 📁 Project Structure
```bash
sb-JobApp-Microservices/
├── job-ms/          # Job service (aggregates data)
├── company-ms/      # Company service
├── review-ms/       # Review service
├── eureka-server/   # Eureka Discovery Server
└── docker-compose.yml
```


## Current Features
- Add/view job listings

- Fetch company info from company-ms

- Fetch company reviews from review-ms

- Service discovery via Eureka

- Dockerized PostgreSQL and pgAdmin

- REST-based communication (via RestTemplate)

## In Progress / Upcoming
- Switch to Feign Client or Spring Cloud Gateway

- Add JWT-based authentication & authorization

- Add Swagger/OpenAPI documentation

- Dockerize each service

- CI/CD pipeline
