# Company Management Microservices — Beginner Code Reading Guide

This copy of the project contains educational comments throughout the backend source code. The comments are intentionally detailed so a developer who is new to Spring Boot can follow the responsibility of each layer and the purpose of important annotations, methods, dependencies, database operations, remote calls, security configuration, and application properties.

## Read each service in this order

1. **Application class** — starts Spring Boot and enables infrastructure features.
2. **Entity** — shows the database model.
3. **DTO** — shows what the API accepts and returns.
4. **Repository** — shows database access.
5. **Mapper** — converts Entity ↔ DTO.
6. **Service** — contains business rules and coordinates database/remote calls.
7. **Controller** — exposes HTTP endpoints and delegates to the service.
8. **Client/Gateway** — communicates with another microservice and adds resilience where applicable.
9. **Exception package** — turns failures into meaningful API errors.
10. **application.properties** — controls ports, database, Eureka, Config, security, gateway, and resilience settings.

## Microservices in this project

- `employee-service` — employee CRUD and department validation.
- `department-service` — department CRUD.
- `project-service` — projects, assignments, cross-service project details, and resilience patterns.
- `company-management-auth-service-phase5` — registration/login, password security, JWT, and JWK support.
- `api-gateway` — single entry point/routing layer.
- `discovery-server` — Eureka service registry.
- `config-server` — centralized configuration server.

> Educational comments do not intentionally change business logic. They explain the existing code so you can remove or shorten them later when you are comfortable with the project.
