# Hotel Pricing System - ADD Iteration 6: Enhance Modularity and Monitoring Capabilities

This document describes the sixth iteration of the Attribute-Driven Design (ADD) process for the Hotel Pricing System. This iteration focuses on enhancing the system's modularity, monitoring capabilities, and testability while ensuring we avoid technical debt.

## Step 2: Establish Goal for the Iteration by Selecting Drivers

The goal of this iteration, as defined in the Iteration Plan, is to enhance modularity and monitoring capabilities. The drivers associated with this goal are:

- **QA-6: Modifiability** - Support for a price query endpoint with a different protocol than REST (e.g. gRPC) is added to the system. The new endpoint does not require changes to be made to the core components of the system.
- **QA-8: Monitorability** - A system operator wishes to measure the performance and reliability of price publication during operation. The system provides a mechanism that allows 100% of these measures to be collected as needed.
- **QA-9: Testability** - 100% of the system and its elements should support integration testing independently of the external systems.
- **CRN-4: Avoid technical debt** - The system should be well-structured, maintainable, and extensible to avoid accumulating technical debt.

These drivers focus on making the system more maintainable, observable, and testable, which are crucial aspects for long-term system health and evolution.

## Step 3: Choose One or More Elements of the System to Refine

Based on the drivers for this iteration, the following elements from the current architecture will be refined:

1. **Price Query Service** - This service needs to be enhanced to support multiple protocols (REST and gRPC) while maintaining a clean separation between protocol-specific code and core business logic. This addresses QA-6 (Modifiability) by allowing new protocols to be added without modifying core components.

2. **Monitoring Infrastructure** - A new monitoring infrastructure needs to be added to collect and analyze system metrics, particularly focusing on price publication performance and reliability. This addresses QA-8 (Monitorability) by providing comprehensive metrics collection and analysis capabilities.

3. **Testing Infrastructure** - A testing infrastructure needs to be established to support integration testing of all system components independently of external systems. This includes:
   - Mock services for external dependencies
   - Test data management
   - Integration test frameworks
   This addresses QA-9 (Testability) by enabling comprehensive testing capabilities.

4. **API Gateway** - The API Gateway needs to be enhanced to support protocol translation and routing for different API protocols (REST and gRPC). This supports QA-6 (Modifiability) by providing a clean way to add new protocols.

5. **Service Templates and Standards** - New templates and standards need to be established for service development to ensure consistent implementation of monitoring, testing, and protocol support across all services. This addresses CRN-4 (Avoid technical debt) by promoting consistent, maintainable code structure.

The current architecture already includes basic versions of these components, but this iteration will refine them with a focus on modularity, monitoring, and testing capabilities. The refinements will ensure that the system is more maintainable, observable, and testable while avoiding technical debt.

## Step 4: Choose Design Concepts That Satisfy the Selected Drivers

|Selected design concept|Rationale|Discarded Alternatives|
|---|---|---|
|Enhanced Layered Architecture|Extends the existing layered architecture with protocol-agnostic service layer to support multiple protocols without modifying core logic. Consistent with the current architecture while enabling protocol flexibility.|Hexagonal Architecture - Discarded to maintain consistency with existing architecture and avoid introducing technical debt through inconsistent architectural patterns.|
|OpenTelemetry|Industry standard for observability that provides consistent instrumentation across services. Supports QA-8 by enabling comprehensive metrics collection and distributed tracing.|Custom monitoring solution - Discarded due to maintenance overhead and lack of ecosystem support.|
|TestContainers|Enables integration testing with real dependencies in containers, providing a consistent testing environment. Supports QA-9 by allowing testing against actual service implementations.|Manual test environment setup - Discarded due to complexity and inconsistency across environments.|
|Protocol Buffers with gRPC|Efficient binary protocol that works well with gRPC for high-performance communication. Supports QA-6 by providing a modern alternative to REST.|GraphQL - Discarded because it would add unnecessary complexity for the current query patterns.|
|Enhanced API Gateway|Centralizes cross-cutting concerns like protocol translation, monitoring, security, and circuit breaking without the complexity of a full service mesh. Supports QA-6 and QA-8 while avoiding unnecessary complexity.|Service Mesh - Discarded due to excessive complexity and operational overhead for the scale of the system; would introduce more technical debt than benefit.|
|Contract Testing|Ensures service compatibility through interface contracts. Supports QA-9 by enabling independent testing of services.|End-to-end testing only - Discarded because it would make testing more brittle and slower.|
|Feature Flags|Enables gradual rollout of new features and A/B testing. Supports CRN-4 by allowing safer changes and rollbacks.|Direct deployment - Discarded because it would make changes riskier and harder to control.|
|Structured Logging|Provides consistent, machine-readable logs across services. Supports QA-8 by enabling better log analysis and correlation.|Unstructured logging - Discarded because it would make log analysis more difficult.|
|Circuit Breaker Pattern|Improves system resilience and provides monitoring points. Supports QA-8 by enabling better failure detection and monitoring.|Direct service calls - Discarded because it would make the system less resilient and harder to monitor.|
|API Versioning Strategy|Enables backward compatibility while evolving APIs. Supports QA-6 by allowing protocol changes without breaking existing clients.|Breaking changes - Discarded because it would disrupt existing integrations.|

## Step 5: Instantiate Architectural Elements, Sketch Views, Allocate Responsibilities, and Define Interfaces

### 5.1 Component Instantiation Decisions

|Instantiation decision|Rationale|
|---|---|
|Enhance Price Query Service with Protocol Adapter layer|Separates protocol handling from core business logic, allowing the service to support both REST and gRPC without duplicating business logic. Maintains consistency with existing layered architecture while enabling protocol flexibility.|
|Add Protocol Translation components to API Gateway|Enables the API Gateway to support multiple protocols (REST and gRPC) and perform protocol translation, reducing the burden on individual services to handle multiple protocols directly.|
|Add Monitoring Service with OpenTelemetry collectors|Centralized monitoring infrastructure that collects and analyzes metrics from all services with minimal intrusion to existing code. Provides a unified view of system performance and reliability.|
|Implement Circuit Breaker monitoring in all external service calls|Improves system resilience while providing valuable monitoring data about external service interactions, particularly for critical operations like price publication.|
|Add Mock Service Infrastructure for testing|Creates lightweight mock implementations of all external dependencies to support integration testing without actual external dependencies. Includes mock implementations of the Channel Management System, Property Management System, and User Identity Service.|
|Implement Feature Flag Service|Provides centralized management of feature flags across services, allowing gradual rollout of new features and A/B testing capabilities without code changes. Supports easier maintenance and experimentation.|
|Add Structured Logging with correlation IDs|Enhances all services with structured logging that includes correlation IDs to track requests across multiple services. Improves diagnostics and monitoring capabilities.|
|Implement Protocol Buffers for both REST and gRPC interfaces|Defines service contracts using Protocol Buffers that can be serialized to both JSON (for REST) and binary (for gRPC), ensuring consistent API definitions across protocols.|
|Add Contract Testing Infrastructure|Implements a testing framework that verifies service compatibility through defined contracts, ensuring that services can evolve independently without breaking existing interactions.|
|Implement version-aware API routing in the API Gateway|Routes requests to the appropriate API version based on client requests, allowing services to evolve their APIs without breaking existing clients.|

Note: The detailed component diagrams, sequence diagrams, and interface definitions are added to the Architecture.md document. 

## Step 6: Record Design Decisions

The design decisions for this iteration have been documented in the Architecture.md document in section 10 (Design Decisions). The key design decisions added in this iteration include:

1. **Enhanced Layered Architecture with Protocol Adapters** - Used to provide protocol flexibility while maintaining architectural consistency with the rest of the system.
2. **OpenTelemetry for comprehensive monitoring** - Implemented to support the monitorability requirements with an industry-standard observability framework.
3. **TestContainers for integration testing** - Used to enable realistic testing with containerized dependencies, providing consistent and isolated test environments.
4. **Protocol Buffers with gRPC** - Implemented to support multiple protocols through schema-driven contracts and high-performance serialization.
5. **Enhanced API Gateway with Protocol Translation** - Used to centralize cross-cutting concerns and simplify backend services.
6. **Contract Testing** - Implemented to ensure API compatibility between service producers and consumers, helping to avoid technical debt.
7. **Feature Flags** - Used to enable gradual rollout of new features and reduce risk when introducing changes.
8. **Structured Logging with correlation IDs** - Implemented to improve log analysis and traceability across services.
9. **Circuit Breaker Pattern** - Used to prevent cascading failures and improve system resilience.
10. **API Versioning Strategy** - Implemented to allow evolution of APIs without breaking existing clients.

## Step 7: Perform Analysis of Current Design

This analysis evaluates how well the design decisions made during this iteration address the drivers that were selected for the iteration goal.

| Driver | Analysis Result |
|--------|----------------|
| **QA-6: Modifiability** - Support for different protocols | **Satisfied** - The Enhanced Layered Architecture with Protocol Adapters, Protocol Buffers with gRPC, and API Gateway with Protocol Translation together provide a comprehensive solution for supporting different protocols. The protocol-agnostic service layer allows new protocols to be added without modifying core components, meeting the requirement that "the new endpoint does not require changes to be made to the core components of the system." |
| **QA-8: Monitorability** - Measure performance and reliability | **Satisfied** - The implementation of OpenTelemetry, Structured Logging with correlation IDs, and Circuit Breaker Pattern provides comprehensive monitoring capabilities. These components enable collecting 100% of the performance and reliability measures as required, with end-to-end tracing, metrics collection, and anomaly detection. |
| **QA-9: Testability** - Integration testing independent of external systems | **Satisfied** - The TestContainers approach, Mock Service Implementations, and Contract Testing framework provide a robust solution for integration testing without actual external dependencies. These components enable 100% of the system to be tested independently of external systems as required. |
| **CRN-4: Avoid technical debt** - Well-structured, maintainable, and extensible system | **Satisfied** - Multiple design decisions contribute to avoiding technical debt: Contract Testing prevents API compatibility issues, Feature Flags enable safer feature deployment, API Versioning Strategy allows API evolution without breaking changes, and the Enhanced Layered Architecture maintains consistency with the existing system. Together, these decisions ensure the system remains maintainable and extensible. |

The architecture now has comprehensive support for protocol flexibility, monitoring capabilities, and testability while maintaining architectural consistency and avoiding technical debt. All drivers for this iteration have been satisfied through the design decisions made.

The design decisions from this iteration have been integrated into the overall architecture, with detailed component designs, interface definitions, and implementation approaches documented in the Architecture.md document. The chosen approaches balance current requirements with future extensibility, ensuring the system can evolve to meet changing needs while maintaining a coherent architecture. 