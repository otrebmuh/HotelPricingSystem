# Hotel Pricing System

A comprehensive hotel pricing system with a CQRS architecture for high performance and scalability.

## Project Overview

The Hotel Pricing System (HPS) is designed to manage hotel room prices with a focus on performance and scalability. It allows for:

- Efficient price changes with derived price calculations
- High-volume price queries
- Integration with external systems like Channel Management Systems and Booking Engines

## Architecture

This project implements a CQRS (Command Query Responsibility Segregation) pattern within a Hexagonal Architecture:

- **Command Side**: Handles write operations (price changes)
- **Query Side**: Handles read operations (price queries)
- **Common**: Shared domain model
- **Infrastructure**: Shared infrastructure components
- **Integration**: External system adapters

## Technology Stack

- Java 17
- Spring Boot 2.7.3
- Spring Data JPA
- PostgreSQL (Write Database)
- AWS DynamoDB (Read Database)
- Redis (Caching)
- AWS EventBridge (Events)
- Maven

## Project Setup

### Prerequisites

- JDK 17
- Maven
- Docker (for local database setup)
- AWS CLI (for DynamoDB local)

### Database Setup

For local development, you can use Docker to run PostgreSQL and Redis:

```bash
# PostgreSQL
docker run --name hps-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=hps -p 5432:5432 -d postgres

# Redis
docker run --name hps-redis -p 6379:6379 -d redis

# DynamoDB Local
docker run --name hps-dynamodb -p 8000:8000 -d amazon/dynamodb-local
```

### Building the Project

```bash
mvn clean install
```

### Running the Applications

```bash
# Command Side
mvn spring-boot:run -pl command-side

# Query Side
mvn spring-boot:run -pl query-side
```

## Project Structure

```
hotel-pricing-system/
├── common/                          # Shared domain model, DTOs
│   ├── domain/                      # Core domain entities, value objects
│   └── dto/                         # Data transfer objects
├── command-side/                    # Write operations
│   ├── api/                         # Command API endpoints
│   ├── application/                 # Command handlers, services
│   ├── domain/                      # Domain services 
│   └── infrastructure/              # Repository implementations
├── query-side/                      # Read operations
│   ├── api/                         # Query API endpoints
│   ├── application/                 # Query handlers, services
│   ├── readmodel/                   # Read model DTOs
│   └── infrastructure/              # Read repositories, cache
├── infrastructure/                  # Shared infrastructure
│   ├── config/                      # Configuration classes
│   ├── persistence/                 # Shared persistence utilities
│   └── messaging/                   # Event publishing
└── integration/                     # External system adapters
    ├── cms/                         # Channel Management System integration
    └── booking/                     # Booking Engine integration
```

## Development Guidelines

- Use domain-driven design principles for the core domain model
- Follow the hexagonal architecture pattern with clear boundaries
- Write unit tests for all domain logic
- Document important architectural decisions

## License

This project is proprietary software.

## Contact

For questions, contact the project maintainers. 