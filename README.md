# Hotel Pricing System Documentation

This repository contains the comprehensive architectural documentation for the Hotel Pricing System, a solution designed for AD&D Hotels to manage hotel pricing across multiple channels. The documentation follows the Attribute-Driven Design process and implements the C4 model for visualizing software architecture.

## Project Structure

```
hotel-pricing-system/
├── Design/                 # Architectural design documentation
│   ├── Architecture.md     # Main architecture document with detailed designs
│   ├── DomainModel.md      # Domain model definition
│   ├── Iteration1.md       # First ADD iteration documentation
│   ├── Iteration2.md       # Second ADD iteration documentation
│   ├── Iteration3.md       # Third ADD iteration documentation
│   ├── Iteration4.md       # Fourth ADD iteration documentation
│   ├── Iteration5.md       # Fifth ADD iteration documentation
│   ├── Iteration6.md       # Sixth ADD iteration documentation
│   └── IterationPlan.md    # Planning document for ADD iterations
├── Process/                # Process documentation
│   └── AttributeDrivenDesign.md  # ADD process description
└── Requirements/           # System requirements
    └── ArchitecturalDrivers.md   # Architectural drivers documentation
```

## Documentation Overview

### Requirements

- **ArchitecturalDrivers.md**: Defines the requirements, constraints, and quality attributes that drive the architectural decisions for the Hotel Pricing System. Includes user stories, quality attribute scenarios, constraints, and architectural concerns.

### Process

- **AttributeDrivenDesign.md**: Describes the Attribute-Driven Design (ADD) methodology used to develop the architecture, including the steps involved in each iteration.

### Design

- **Architecture.md**: The main architectural document that provides comprehensive documentation of the Hotel Pricing System, including:
  - Context and container diagrams
  - Component diagrams for each service
  - Sequence diagrams for key scenarios
  - Interface definitions
  - Event definitions
  - Design decisions with rationales

- **DomainModel.md**: Defines the core domain model for the Hotel Pricing System, including entities, relationships, and their descriptions.

- **Iteration1.md through Iteration6.md**: Document the progression of the architecture through six iterations of the ADD process, focusing on different aspects:
  - Iteration 1: Initial system decomposition and structure
  - Iteration 2: Security architecture
  - Iteration 3: Performance and reliability
  - Iteration 4: Availability and scalability
  - Iteration 5: Deployability
  - Iteration 6: Modifiability, monitorability, and testability

- **IterationPlan.md**: Outlines the plan for the ADD iterations, mapping architectural drivers to specific iterations.

## Key Features of the Architecture

The architecture documentation covers several aspects of the Hotel Pricing System:

1. **Microservices Architecture**: Defines a domain-driven microservices approach with clear service boundaries.

2. **Cloud-Native Design**: Specifies a cloud-native implementation leveraging containerization and managed services.

3. **API Gateway Pattern**: Details an API Gateway for routing, protocol translation, and security enforcement.

4. **Event-Driven Communication**: Describes event-based communication between services for data consistency.

5. **CQRS Pattern**: Implements Command Query Responsibility Segregation for optimized read and write operations.

6. **Security Architecture**: Specifies OAuth 2.0/OIDC integration with cloud identity services.

7. **Monitoring and Testing Infrastructure**: Defines comprehensive monitoring with OpenTelemetry and testing with TestContainers.

## How to Use This Documentation

- Start with the **ArchitecturalDrivers.md** to understand the requirements driving the architecture.
- Review the **AttributeDrivenDesign.md** to understand the design process.
- Explore **Architecture.md** for the comprehensive view of the system design.
- Review the iteration documents (**Iteration1.md** through **Iteration6.md**) to understand how the architecture evolved.

## About This Project

This is a documentation-only project created to demonstrate the application of Attribute-Driven Design in developing a comprehensive software architecture. The documentation includes detailed diagrams in Mermaid syntax, interface definitions, and design decisions with rationales.

The Hotel Pricing System is designed for AD&D Hotels to manage the pricing of hotel rooms across multiple distribution channels, supporting operations like price changes, price queries, hotel management, rate management, and user management. 