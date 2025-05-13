# Hotel Pricing System - Iteration 2

This document tracks the progress of the second iteration of the architecture design process for the Hotel Pricing System, following the Attribute-Driven Design (ADD) methodology.

## Iteration Goal

According to the Iteration Plan, the goal of Iteration 2 is to:

**Design core pricing calculation and publication functionality**

## Drivers to Address

The following drivers will be addressed in this iteration:

- HPS-2: Change Prices (Primary user story)
- QA-1: Performance
- QA-2: Reliability
- CON-5: Initial REST API integration with existing systems

## ADD Process Steps

### Step 2: Establish goal for the iteration by selecting drivers

The goal and drivers for this iteration have been defined above, focusing on the core pricing calculation functionality and its reliability and performance, as well as the integration with external systems.

### Step 3: Choose one or more elements of the system to refine

Based on the container diagram created in Iteration 1 and the focus on the price calculation and publication functionality, the following elements will be refined in this iteration:

1. **Price Management Service**: This is the core service responsible for calculating prices based on base rates and business rules, handling price changes and simulations, maintaining price history, and publishing price-related events.

2. **Rate Management Service**: This service manages rate types and business rules, which are essential inputs for the price calculation process.

3. **Event Bus**: This component handles the publishing of price changes to other services and external systems, which is critical for the reliability requirement.

4. **API Gateway**: This component needs refinement to support REST API integration with existing systems, particularly the Channel Management System.

5. **Price Query Service**: While query capabilities will be the focus of Iteration 3, this service needs initial refinement to ensure it can receive and process price updates from the Price Management Service.

### Step 4: Choose one or more design concepts that satisfy the selected drivers

| Selected design concept | Rationale | Discarded Alternatives |
|-------------------------|-----------|------------------------|
| Command Query Responsibility Segregation (CQRS) | - Separates write operations (price changes) from read operations (price queries)<br>- Optimizes performance for both operations (QA-1)<br>- Allows independent scaling of write and read services | - Traditional CRUD approach: Would not provide the same level of performance optimization for both write and read operations |
| Event Sourcing | - Maintains a complete history of price changes<br>- Provides audit capabilities<br>- Enables reliable event publication (QA-2)<br>- Facilitates replay of events for recovery scenarios | - State-based persistence only: Would lose history of changes and make auditing more difficult |
| Outbox Pattern | - Ensures reliable event publication (QA-2)<br>- Guarantees exactly-once delivery semantics<br>- Prevents data inconsistencies | - Direct message publication: Could lead to lost events or inconsistencies between data and events |
| Circuit Breaker Pattern | - Prevents cascading failures when interacting with external systems<br>- Improves system resilience (QA-2)<br>- Enables graceful degradation | - Direct integration without fault tolerance: Would lead to failures when external systems are unavailable |
| Cache-Aside Pattern | - Improves performance of price calculations (QA-1)<br>- Reduces database load<br>- Speeds up frequent operations | - No caching: Would lead to redundant calculations and increased load on databases |
| Asynchronous REST Integration | - Decouples the system from external systems<br>- Improves reliability (QA-2)<br>- Meets the REST API integration requirement (CON-5) | - Synchronous integration: Would create tight coupling and impact reliability |

### Step 5: Instantiate architectural elements, sketch views, allocate responsibilities, and define interfaces

Based on the selected design concepts, we will now refine the architectural elements identified in Step 3 and allocate specific responsibilities to each component.

#### Price Management Service Component Diagram

The Price Management Service is refined into the following components:

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create PriceCommandController component | Provides REST API endpoints for price change operations, including simulation and actual price changes |
| Create PriceCalculationEngine component | Implements the core price calculation logic, applying business rules to base rates to derive prices for all room types and rates |
| Create PriceCommandRepository component | Stores the price change commands and state in an event-sourced manner |
| Create PriceEventStore component | Maintains the complete history of price changes for audit and recovery purposes |
| Create EventPublisher component with Outbox | Ensures reliable publication of price change events to the Event Bus |
| Create CircuitBreaker component | Prevents cascading failures when interacting with the Channel Management System |
| Create PriceCache component | Implements the Cache-Aside pattern to improve performance of frequent price calculations |
| Create ChannelManagementAdapter component | Handles the asynchronous REST integration with the Channel Management System |

#### Component Responsibilities

**PriceCommandController**:
- Handles HTTP requests for price changes
- Validates input data
- Routes requests to the appropriate handlers
- Returns responses to clients

**PriceCalculationEngine**:
- Applies business rules to calculate derived prices
- Ensures calculations complete within 100ms (QA-1)
- Uses cached data when appropriate to improve performance
- Simulates price changes before they are applied

**PriceCommandRepository**:
- Stores price change commands
- Maintains the current state of prices
- Provides transactional guarantees for command processing

**PriceEventStore**:
- Stores all price change events
- Supports event replay for recovery
- Enables audit capabilities

**EventPublisher with Outbox**:
- Ensures atomic updates of domain state and event outbox
- Reliably delivers events to the Event Bus
- Provides exactly-once delivery semantics

**CircuitBreaker**:
- Monitors calls to external systems
- Prevents cascading failures
- Provides fallback mechanisms when external systems are unavailable

**PriceCache**:
- Caches frequently accessed pricing data
- Improves response time for price calculations
- Reduces database load

**ChannelManagementAdapter**:
- Translates internal events to external REST API calls
- Handles retries and backoff strategies
- Ensures reliable delivery of price updates to the Channel Management System

#### Interface Definitions

The following interfaces will be defined to support the above components:

1. **PriceCommand API** (REST)
   - `POST /api/prices/simulate` - Simulates price changes without applying them
   - `POST /api/prices/apply` - Applies price changes and publishes them
   - `GET /api/prices/history/{hotelId}` - Retrieves the history of price changes for a hotel

2. **Internal Event Types**
   - `PriceChangeRequested` - Indicates a request to change prices
   - `PriceChangeSimulated` - Contains the results of a price change simulation
   - `PriceChangeApplied` - Indicates that price changes have been successfully applied
   - `PriceChangePublished` - Indicates that price changes have been published to external systems

3. **External Integration API** (REST)
   - `POST /api/channels/prices` - Sends price updates to the Channel Management System

### Step 6: Record design decisions

The following key design decisions were made during this iteration:

| Driver | Decision | Rationale |
|--------|----------|-----------|
| HPS-2 & QA-1 | Implement CQRS pattern for price management | By separating the command (price changes) and query (price retrieval) responsibilities, we can optimize each path independently. The Price Management Service will focus on efficiently processing price changes, while the Price Query Service (to be further refined in Iteration 3) will be optimized for handling high-volume queries. |
| HPS-2 & QA-2 | Use Event Sourcing for price change history | Storing price changes as a sequence of events provides a complete audit trail, supports recovery scenarios, and enables reliable event publication to downstream systems. This is crucial for ensuring that 100% of price changes are successfully published. |
| QA-1 | Implement caching for business rules and current prices | By caching frequently accessed data, particularly business rules from the Rate Management Service and current prices, we can reduce calculation time and ensure that price calculations complete within the required 100ms. |
| QA-2 | Use the Outbox Pattern for reliable event publication | The outbox pattern ensures that domain state changes and event messages are updated atomically, preventing inconsistencies between the system state and published events. This helps meet the reliability requirement that 100% of price changes are successfully published. |
| QA-2 | Implement Circuit Breaker for external system calls | The circuit breaker pattern prevents cascading failures when the Channel Management System is unavailable, improving the overall reliability of the system. It allows the system to continue functioning in degraded mode and automatically recover when the external system becomes available again. |
| CON-5 | Use asynchronous REST for integration with external systems | Asynchronous REST integration decouples the Hotel Pricing System from external systems, allowing price changes to be published reliably without being dependent on the immediate availability of these systems. This addresses both the requirement for REST API integration and the need for high reliability. |
| HPS-2 | Support price change simulation before actual application | The architecture supports simulating price changes before they are applied, allowing users to preview the effects of their changes before committing them. This feature is explicitly mentioned in the HPS-2 user story description and is implemented through the `/api/prices/simulate` endpoint. |

### Step 7: Perform analysis of current design and review iteration goal and achievement of design purpose

This step analyzes if the design decisions made during this iteration were sufficient to address the drivers associated with the iteration goal.

| Driver | Analysis Result |
|--------|----------------|
| HPS-2: Change Prices | **Satisfied**. The refined Price Management Service provides comprehensive support for the Change Prices user story. The architecture supports selecting a hotel, simulating price changes, applying price changes, and publishing the changes to the Channel Management System and other internal services. The component diagram and sequence diagram illustrate the flow of operations, and the interfaces have been defined to support all required functionality. |
| QA-1: Performance | **Satisfied**. The architecture addresses the performance requirement that price calculations complete within 100ms through several design decisions: <br>1. Implementing CQRS to optimize the price change path<br>2. Using the Cache-Aside pattern to reduce calculation time<br>3. Utilizing event sourcing for efficient storage and retrieval of price data<br>The sequence diagram shows that price calculation occurs before storing events, ensuring the critical path is optimized. |
| QA-2: Reliability | **Satisfied**. The architecture ensures that 100% of price changes are published successfully through multiple reliability patterns:<br>1. Using event sourcing to maintain a complete history of all price changes<br>2. Implementing the outbox pattern for reliable event publication<br>3. Using circuit breakers for resilient communication with external systems<br>4. Employing asynchronous REST integration to decouple from external systems<br>These patterns work together to prevent data loss and inconsistencies, even in failure scenarios. |
| CON-5: REST API integration | **Satisfied**. The architecture supports integration with existing systems through REST APIs as required. The Channel Management Adapter component provides the necessary functionality to translate internal events to REST API calls, while the circuit breaker pattern ensures resilient integration. The interface for communicating with the Channel Management System has been defined using REST, meeting this constraint. |

Overall, all drivers targeted for Iteration 2 have been satisfied through the design decisions documented in the Architecture and Iteration documents. The Price Management Service has been refined with components that collectively support the core price calculation and publication functionality, with a focus on performance and reliability. The integration with external systems has been designed to use REST APIs while ensuring resilience and fault tolerance.

The next iteration can now proceed to refine the query capabilities of the system, focusing on the Query Prices user story and addressing availability and scalability requirements as outlined in the Iteration Plan. 