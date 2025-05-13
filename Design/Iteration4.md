# Hotel Pricing System - Iteration 4

This document tracks the progress of the fourth iteration of the architecture design process for the Hotel Pricing System, following the Attribute-Driven Design (ADD) methodology.

## Iteration Goal

According to the Iteration Plan, the goal of Iteration 4 is to:

**Implement hotel and rate management**

## Drivers to Address

The following drivers will be addressed in this iteration:

- HPS-4: Manage Hotels (Primary user story)
- HPS-5: Manage Rates

## ADD Process Steps

### Step 2: Establish goal for the iteration by selecting drivers

The goal and drivers for this iteration have been defined above, focusing on the hotel and rate management functionalities of the system.

Specifically, we need to address:
- HPS-4: The ability for administrators to add, change, or modify hotel information, including tax rates, available rates, and room types.
- HPS-5: The ability for administrators to add, change, or modify rates, including defining the calculation business rules for different rates.

These user stories are fundamental to the system as they provide the foundation for all pricing operations. Without properly managed hotel information and rate definitions, price calculations and queries (addressed in previous iterations) would not be possible. As noted in the Architectural Drivers document, HPS-4 (Manage Hotels) is considered one of the primary user stories because "it establishes a basis for many other user stories."

### Step 3: Choose one or more elements of the system to refine

Based on the container diagram created in Iteration 1 and the focus on hotel and rate management, the following elements will be refined in this iteration:

1. **Hotel Management Service**: This service was initially defined in Iteration 1 but needs to be fully detailed to support the management of hotel information, including tax rates, available rates, and room types.

2. **Rate Management Service**: This service was also initially defined in Iteration 1 and partially referenced in Iteration 2 as a provider of business rules for price calculations. In this iteration, we will fully develop its capabilities for managing rates and defining calculation business rules.

3. **Admin Portal**: The user interface for administrators to manage hotels and rates needs to be refined to provide efficient and user-friendly access to these management functions.

4. **API Gateway**: The API Gateway needs further refinement to support the administrative APIs required for hotel and rate management.

5. **Event Bus**: The Event Bus needs to be refined to handle events related to hotel and rate changes, which will need to be propagated to other services (especially the Price Management and Price Query Services).

### Step 4: Choose one or more design concepts that satisfy the selected drivers

| Selected design concept | Rationale | Discarded Alternatives |
|-------------------------|-----------|------------------------|
| Domain-Driven Design (DDD) | - Aligns service boundaries with business domains<br>- Provides a common language for business and technical stakeholders<br>- Enforces a clear separation of domain logic and infrastructure<br>- Supports complex business rule modeling | - Anemic domain model: Would not sufficiently represent complex business rules<br>- Transaction script pattern: Would lead to scattered business logic |
| Event-Driven Architecture | - Enables loose coupling between services<br>- Facilitates propagation of hotel and rate changes<br>- Supports eventual consistency across the system<br>- Allows dependent services to react to changes | - Request-response only: Would tightly couple services and reduce resilience<br>- Point-to-point integration: Would create a complex web of dependencies |
| Rule Engine | - Provides flexibility for defining and executing calculation business rules<br>- Separates rule definition from rule execution<br>- Supports complex rule composition<br>- Enables business users to understand and verify rule definitions | - Hard-coded calculations: Would require code changes for rule modifications<br>- Database-driven calculations: Would be harder to test and maintain |
| Versioning Strategy | - Supports backward compatibility when hotel or rate structures change<br>- Prevents breaking changes for dependent services<br>- Allows gradual adoption of new features | - No versioning: Would risk disrupting dependent services<br>- Big bang migrations: Would require coordinated deployments |
| Optimistic Concurrency Control | - Prevents data corruption from concurrent modifications<br>- Maintains data integrity<br>- Provides better user experience than pessimistic locking<br>- Reduces database contention | - Pessimistic locking: Would reduce concurrency and user experience<br>- No concurrency control: Would risk data inconsistency |
| Bulk Operations | - Improves efficiency for batch hotel and rate updates<br>- Reduces transaction overhead<br>- Supports efficient large-scale data management | - Single-item operations only: Would create performance issues for mass updates<br>- Manual batch processing: Would be error-prone and less efficient |
| Administrative UI Patterns | - Provides consistent interfaces for management tasks<br>- Improves administrator efficiency<br>- Reduces learning curve<br>- Supports complex data relationships | - General-purpose UI: Would not address specific administrative needs<br>- Command-line interface: Would limit usability for non-technical users |

### Step 5: Instantiate architectural elements, sketch views, allocate responsibilities, and define interfaces

Based on the selected design concepts, we will now refine the architectural elements identified in Step 3 and allocate specific responsibilities to each component.

#### Hotel Management Service Component Diagram

The Hotel Management Service is refined into the following components:

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create HotelCommandController component | Provides REST API endpoints for hotel management operations (create, update, delete) |
| Create HotelQueryController component | Provides REST API endpoints for hotel information retrieval operations |
| Create HotelDomainService component | Implements the core business logic for hotel management |
| Create RoomTypeDomainService component | Implements the business logic for managing room types within hotels |
| Create HotelValidator component | Validates hotel data integrity and business rules before persistence |
| Create HotelRepository component | Provides data access methods for hotel entities |
| Create HotelEventPublisher component | Publishes events related to hotel changes to the Event Bus |
| Create BulkOperationHandler component | Processes batch operations on hotels efficiently |

#### Rate Management Service Component Diagram

The Rate Management Service is refined into the following components:

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create RateCommandController component | Provides REST API endpoints for rate management operations |
| Create RateQueryController component | Provides REST API endpoints for rate information retrieval |
| Create RateDomainService component | Implements the core business logic for rate management |
| Create RuleEngineComponent component | Implements a flexible rule engine for defining and executing calculation business rules |
| Create RuleValidator component | Validates rule syntax and semantics before persistence |
| Create RuleTestExecutor component | Allows testing rules with sample data before applying them |
| Create RateRepository component | Provides data access methods for rate entities |
| Create RateEventPublisher component | Publishes events related to rate changes to the Event Bus |

#### Admin Portal UI Components

The Admin Portal is refined to include the following UI components:

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create HotelManagementModule component | Provides UI for managing hotel information |
| Create RoomTypeManagementModule component | Provides UI for managing room types |
| Create RateManagementModule component | Provides UI for managing rates |
| Create RuleEditorModule component | Provides visual rule editor for business rules |
| Create BulkOperationsModule component | Provides UI for batch operations |
| Create ValidationFeedbackComponent component | Provides real-time validation feedback to administrators |
| Create VersionHistoryComponent component | Displays version history of hotels and rates |

#### Component Responsibilities

**HotelCommandController**:
- Handles HTTP requests for hotel creation, update, and deletion
- Validates input data format
- Routes requests to the appropriate domain services
- Returns responses to clients
- Supports optimistic concurrency control

**HotelQueryController**:
- Handles HTTP requests for retrieving hotel information
- Supports filtering, sorting, and pagination
- Returns appropriately formatted responses
- Implements versioned API endpoints

**HotelDomainService**:
- Implements core business logic for hotel management
- Enforces business rules and constraints
- Coordinates with other domain services
- Maintains data consistency

**RoomTypeDomainService**:
- Implements business logic for room type management
- Enforces room type constraints and relationships
- Validates room type data integrity
- Ensures consistency between room types and hotels

**HotelValidator**:
- Validates hotel data against business rules
- Performs schema validation
- Checks for data integrity constraints
- Returns detailed validation errors

**HotelRepository**:
- Provides data access methods for hotel entities
- Implements optimistic concurrency control
- Supports efficient querying
- Manages database transactions

**HotelEventPublisher**:
- Creates and publishes events for hotel changes
- Ensures event data completeness
- Handles event delivery confirmation
- Implements retry logic for failed event publishing

**BulkOperationHandler**:
- Processes batch operations on multiple hotels
- Optimizes database access for bulk operations
- Provides transaction management for batch operations
- Handles partial failures gracefully

**RateCommandController**:
- Handles HTTP requests for rate creation, update, and deletion
- Validates input data format
- Routes requests to the appropriate domain services
- Returns responses to clients
- Supports optimistic concurrency control

**RateQueryController**:
- Handles HTTP requests for retrieving rate information
- Supports filtering, sorting, and pagination
- Returns appropriately formatted responses
- Implements versioned API endpoints

**RateDomainService**:
- Implements core business logic for rate management
- Enforces business rules and constraints
- Coordinates with other domain services
- Maintains data consistency

**RuleEngineComponent**:
- Provides DSL (Domain Specific Language) for defining calculation rules
- Compiles rule definitions into executable form
- Executes rules for price calculations
- Manages rule versioning

**RuleValidator**:
- Validates rule syntax and semantics
- Checks for logical errors in rule definitions
- Verifies rule compatibility with existing system
- Returns detailed validation errors

**RuleTestExecutor**:
- Executes rules against sample data
- Compares rule execution results with expected outcomes
- Identifies potential issues in rules
- Provides debugging information

**RateRepository**:
- Provides data access methods for rate entities
- Implements optimistic concurrency control
- Supports efficient querying
- Manages database transactions

**RateEventPublisher**:
- Creates and publishes events for rate changes
- Ensures event data completeness
- Handles event delivery confirmation
- Implements retry logic for failed event publishing

#### Interface Definitions

The following interfaces will be defined to support the above components:

1. **Hotel Management API** (REST)
   - `POST /api/v1/hotels` - Creates a new hotel
   - `PUT /api/v1/hotels/{hotelId}` - Updates an existing hotel
   - `DELETE /api/v1/hotels/{hotelId}` - Deletes a hotel
   - `GET /api/v1/hotels` - Lists hotels with filtering options
   - `GET /api/v1/hotels/{hotelId}` - Gets a specific hotel details
   - `POST /api/v1/hotels/bulk` - Performs bulk operations on hotels

2. **Room Type Management API** (REST)
   - `POST /api/v1/hotels/{hotelId}/room-types` - Creates a new room type for a hotel
   - `PUT /api/v1/hotels/{hotelId}/room-types/{roomTypeId}` - Updates a room type
   - `DELETE /api/v1/hotels/{hotelId}/room-types/{roomTypeId}` - Deletes a room type
   - `GET /api/v1/hotels/{hotelId}/room-types` - Lists room types for a hotel

3. **Rate Management API** (REST)
   - `POST /api/v1/rates` - Creates a new rate
   - `PUT /api/v1/rates/{rateId}` - Updates an existing rate
   - `DELETE /api/v1/rates/{rateId}` - Deletes a rate
   - `GET /api/v1/rates` - Lists rates with filtering options
   - `GET /api/v1/rates/{rateId}` - Gets a specific rate details
   - `GET /api/v1/hotels/{hotelId}/rates` - Lists rates for a specific hotel

4. **Rule Management API** (REST)
   - `POST /api/v1/rates/{rateId}/rules` - Creates a new rule for a rate
   - `PUT /api/v1/rates/{rateId}/rules/{ruleId}` - Updates a rule
   - `DELETE /api/v1/rates/{rateId}/rules/{ruleId}` - Deletes a rule
   - `GET /api/v1/rates/{rateId}/rules` - Lists rules for a rate
   - `POST /api/v1/rates/{rateId}/rules/{ruleId}/test` - Tests a rule with sample data

5. **Hotel Events**
   - `HotelCreated` - Published when a new hotel is created
   - `HotelUpdated` - Published when a hotel is updated
   - `HotelDeleted` - Published when a hotel is deleted
   - `RoomTypeCreated` - Published when a new room type is created
   - `RoomTypeUpdated` - Published when a room type is updated
   - `RoomTypeDeleted` - Published when a room type is deleted

6. **Rate Events**
   - `RateCreated` - Published when a new rate is created
   - `RateUpdated` - Published when a rate is updated
   - `RateDeleted` - Published when a rate is deleted
   - `RuleCreated` - Published when a new rule is created
   - `RuleUpdated` - Published when a rule is updated
   - `RuleDeleted` - Published when a rule is deleted

### Step 6: Record design decisions

The design decisions from this iteration have been recorded in the Architecture.md document in Section 9 (Design Decisions), including:

- Implementation of Domain-Driven Design patterns for hotel management
- Separation of command and query controllers
- Implementation of optimistic concurrency control
- Implementation of a rule engine for calculation rules
- Creation of a test execution environment for rules
- Implementation of bulk operation handling
- Implementation of comprehensive validation

The interface specifications for the Hotel Management Service and Rate Management Service have been documented in Sections 8.3 and 8.4 of the Architecture.md document.

### Step 7: Perform analysis of current design and review iteration goal and achievement of design purpose

In this step, we analyze if the design decisions made during the iteration were sufficient to address the drivers associated with the iteration goal. The following table summarizes the analysis:

| Driver | Analysis result |
|--------|----------------|
| HPS-4: Manage Hotels | **Satisfied**: The design of the Hotel Management Service with its Domain-Driven Design approach provides a robust solution for hotel management. The service includes components for hotel and room type management, validation, persistence, and event publication. The Admin Portal includes modules for hotel and room type management with a user-friendly interface. The API supports all required operations including bulk operations for efficiency. Events are published for all hotel changes to ensure consistency across the system. |
| HPS-5: Manage Rates | **Satisfied**: The Rate Management Service with its rule engine provides a flexible and powerful solution for rate management. The rule engine allows business users to define and test calculation rules without requiring code changes. The service supports versioning of rules to ensure backward compatibility. The Admin Portal includes modules for rate management and a visual rule editor. Events are published for all rate changes to ensure consistency across the system. |
| QA-2: Reliability | **Satisfied**: Multiple design decisions contribute to reliability, including optimistic concurrency control to prevent data corruption, comprehensive validation to ensure data integrity, and event publication with retry logic for reliable event delivery. The event-driven architecture ensures eventual consistency across services. |
| QA-6: Modifiability | **Satisfied**: The separation of command and query controllers, the use of Domain-Driven Design, and the implementation of a flexible rule engine all contribute to modifiability. The API versioning strategy ensures backward compatibility when changes are made. |
| QA-9: Testability | **Satisfied**: The rule testing component allows rules to be tested before they are applied, improving testability. The clear separation of concerns and well-defined interfaces make individual components easier to test in isolation. |

#### Overall Iteration Assessment

The iteration successfully addressed the goal of implementing hotel and rate management functionality in the Hotel Pricing System. The design provides:

1. **Comprehensive Hotel Management**: The Hotel Management Service enables administrators to add, update, and delete hotels, including managing tax rates, room types, and available rates. The design supports both individual and bulk operations for efficiency.

2. **Flexible Rate Management**: The Rate Management Service with its rule engine provides a powerful solution for managing rates and defining calculation business rules. The ability to test rules before applying them improves confidence in rule changes.

3. **Integration with Other Services**: The event-driven architecture ensures that changes to hotels and rates are properly propagated to other services, particularly the Price Management Service and Price Query Service.

4. **User-Friendly Administration**: The Admin Portal provides specialized UI components for hotel and rate management, improving administrator efficiency.

5. **Data Integrity and Consistency**: Comprehensive validation, optimistic concurrency control, and event publication mechanisms ensure data integrity and consistency throughout the system.

The design decisions made in this iteration provide a solid foundation for the implementation phase. The clear separation of responsibilities among components and the well-defined interfaces will facilitate development and future maintenance.

As we move forward, attention should be given to:

1. **Detailed Rule Language Definition**: Further refinement of the rule engine's domain-specific language will be needed to support all required calculation scenarios.

2. **Performance Optimization**: Special attention should be paid to the performance of bulk operations and rule execution in the implementation phase.

3. **Integration Testing**: Comprehensive testing of the interaction between the Hotel Management Service, Rate Management Service, and other services will be crucial to ensure the overall system reliability.

The design decisions made in this iteration align well with the architectural principles established in earlier iterations, particularly the microservices architecture, domain-driven design, and event-driven communication patterns. 