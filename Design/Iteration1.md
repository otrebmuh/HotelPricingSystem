# Hotel Pricing System - Iteration 1

This document tracks the progress of the first iteration of the architecture design process for the Hotel Pricing System, following the Attribute-Driven Design (ADD) methodology.

## Iteration Goal

According to the Iteration Plan, the goal of Iteration 1 is to:

**Establish overall system structure and deployment model**

## Drivers to Address

The following drivers will be addressed in this iteration:

- CRN-1: Establish an overall initial system structure
- CON-6: Cloud-native approach
- CON-2: Cloud resource hosting and identity service
- CRN-5: Set up continuous deployment infrastructure
- QA-7: Deployability

## ADD Process Steps

### Step 2: Establish goal for the iteration by selecting drivers

The goal and drivers for this iteration have been defined above, focusing on establishing the overall system structure with a cloud-native approach.

### Step 3: Choose one or more elements of the system to refine

Since this is the first iteration of a greenfield development project, the element to refine is the entire Hotel Pricing System. We need to define its high-level structure, identifying the major components and their relationships.

Based on the context diagram in the Architecture document and the constraints, particularly CON-6 (cloud-native approach) and CON-2 (cloud resource hosting and identity service), we need to define:

1. The overall architecture style (e.g., microservices, layered, etc.)
2. The major functional components of the system
3. The deployment model in the cloud environment
4. The integration patterns with external systems

### Step 4: Choose one or more design concepts that satisfy the selected drivers

| Selected design concept | Rationale | Discarded Alternatives |
|-------------------------|-----------|------------------------|
| Microservices Architecture | - Aligns with cloud-native principles (CON-6)<br>- Enables clear separation of concerns (CRN-1)<br>- Supports independent deployment and scaling<br>- Facilitates service boundaries based on business domains | - Monolithic Architecture: Would limit independent deployment and scaling<br>- Layered Architecture: Would not provide sufficient decoupling between components |
| Containerized Deployment with Kubernetes | - Provides consistency across environments (QA-7)<br>- Enables automated deployment and scaling<br>- Supports cloud-native hosting (CON-6)<br>- Works with major cloud providers (CON-2) | - Virtual Machine deployment: More resource-intensive and slower to provision<br>- Serverless only: Doesn't provide the same level of control for all components |
| CI/CD Pipeline with Environment Promotion | - Enables continuous deployment (CRN-5)<br>- Supports automated testing and validation<br>- Ensures consistent deployments (QA-7)<br>- Uses Infrastructure as Code for environment consistency | - Manual deployments: Error-prone and time-consuming<br>- Single environment CI/CD: Would not support proper testing and validation |
| API Gateway Pattern | - Provides a single entry point for external systems<br>- Simplifies client integration<br>- Centralizes cross-cutting concerns<br>- Supports the decoupling of systems | - Direct service-to-service communication: Would create tight coupling and increase complexity |
| Event-Driven Communication | - Decouples services<br>- Supports asynchronous communication<br>- Allows for system evolution<br>- Addresses the need to avoid tight coupling mentioned in business case | - Synchronous communication only: Would create dependencies between services and limit resilience |
| Identity as a Service (IDaaS) | - Leverages cloud provider identity service (CON-2)<br>- Provides secure authentication and authorization<br>- Reduces development effort for security<br>- Supports single sign-on | - Custom authentication system: Would require more development and maintenance<br>- Delegated authentication: Would not fully utilize cloud provider capabilities |

### Step 5: Instantiate architectural elements, sketch views, allocate responsibilities, and define interfaces

Based on the selected design concepts, we have instantiated the architectural elements for the Hotel Pricing System. The container diagram in the Architecture document has been updated to reflect this structure.

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create Web Application and Admin Portal frontend containers | Separate user interfaces for different user roles (commercial users vs. administrators) to support different responsibilities and security concerns |
| Create API Gateway container | Central entry point for external systems and clients to access backend services, supporting decoupling and cross-cutting concerns |
| Create Authentication Service container | Dedicated service for integrating with cloud identity provider and managing authentication/authorization, addressing CON-2 |
| Create Hotel Management Service container | Dedicated service for managing hotel information, supporting the Manage Hotels user story |
| Create Rate Management Service container | Dedicated service for managing rate types and business rules, supporting the Manage Rates user story |
| Create Price Management Service container | Core service for calculating and publishing prices, supporting the Change Prices user story |
| Create Price Query Service container | Dedicated service optimized for high-volume price queries, supporting the Query Prices user story and QA-4 (scalability) |
| Create Event Bus container | Message broker for asynchronous event-driven communication between services |
| Create various databases (one per service) | Following the microservices pattern of data ownership, each service has its own database |
| Deploy all components in Kubernetes clusters | Support containerized deployment and cloud-native approach (CON-6) |
| Set up CI/CD pipeline with four environments | Support consistent deployments across development, integration, staging, and production environments (QA-7 and CRN-5) |

Sequence diagrams have been added to the Architecture document to illustrate how these components collaborate to support key user stories and quality attribute scenarios.

### Step 6: Record design decisions

TBD

### Step 7: Perform analysis of current design and review iteration goal and achievement of design purpose

This step analyzes if the design decisions made during this iteration were sufficient to address the drivers associated with the iteration goal.

| Driver | Analysis Result |
|--------|----------------|
| CRN-1: Establish an overall initial system structure | **Satisfied**. The microservices architecture established in the container diagram provides a clear structure with well-defined responsibilities. The five primary services (Authentication Service, Hotel Management Service, Rate Management Service, Price Management Service, and Price Query Service) along with the supporting components (API Gateway, Event Bus) form a robust foundation for the system. |
| CON-6: Cloud-native approach | **Satisfied**. The architecture embraces cloud-native principles through containerized deployment with Kubernetes, event-driven communication, and API-Gateway pattern. The design supports horizontal scaling, resilience, and elasticity which are fundamental to cloud-native applications. |
| CON-2: Cloud resource hosting and identity service | **Satisfied**. The Authentication Service integrates with the cloud provider's identity service (as shown in the container diagram) for user authentication. The domain model reflects this integration by separating user identity (managed by the cloud provider) from application-specific authorization data. |
| CRN-5: Set up continuous deployment infrastructure | **Satisfied**. The QA-7 sequence diagram outlines a comprehensive CI/CD pipeline with environment promotion that supports automated build, test, and deployment processes. The pipeline includes container registry, infrastructure as code, and Kubernetes deployment, providing the foundation for continuous deployment. |
| QA-7: Deployability | **Satisfied**. The containerization approach combined with Infrastructure as Code and the CI/CD pipeline ensures that the application can be moved between non-production environments without code changes. The sequence diagram for QA-7 demonstrates how deployment flows from development to production, including rollback scenarios. |

Overall, all drivers targeted for Iteration 1 have been satisfied through the design decisions documented in the Architecture document. The container diagram clearly establishes the overall system structure with appropriate separation of concerns and alignment with cloud-native principles. The deployment model has been defined through the containerized approach with Kubernetes, and the CI/CD pipeline has been outlined to support continuous deployment.

The next iteration can now proceed to refine the elements identified in this iteration, focusing on the core functionality related to price management as outlined in the Iteration Plan.