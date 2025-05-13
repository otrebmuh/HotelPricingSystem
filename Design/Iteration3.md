# Hotel Pricing System - Iteration 3

This document tracks the progress of the third iteration of the architecture design process for the Hotel Pricing System, following the Attribute-Driven Design (ADD) methodology.

## Iteration Goal

According to the Iteration Plan, the goal of Iteration 3 is to:

**Design query capabilities and scalability**

## Drivers to Address

The following drivers will be addressed in this iteration:

- HPS-3: Query Prices (Primary user story)
- QA-3: Availability
- QA-4: Scalability

## ADD Process Steps

### Step 2: Establish goal for the iteration by selecting drivers

The goal and drivers for this iteration have been defined above, focusing on the query capabilities of the system and ensuring it meets the availability and scalability requirements. 

Specifically, we need to address:
- HPS-3: The ability for users and external systems to query prices through the user interface or a query API
- QA-3: Ensuring 99.9% uptime for pricing queries outside of maintenance windows
- QA-4: Supporting a minimum of 100,000 price queries per day, with the capability to handle up to 1,000,000 without decreasing average latency by more than 20%

### Step 3: Choose one or more elements of the system to refine

Based on the container diagram created in Iteration 1 and the focus on query capabilities and scalability, the following elements will be refined in this iteration:

1. **Price Query Service**: This service was initially defined in Iteration 1 and partially refined in Iteration 2 to receive price updates. In this iteration, we will fully develop its query capabilities, focusing on optimizing for high volume and ensuring high availability.

2. **API Gateway**: The API Gateway needs to be refined to efficiently route and load balance query requests, both from the web application and from external systems.

3. **Web Application**: The user interface for querying prices needs to be refined to provide efficient and user-friendly access to price information.

4. **Event Bus**: While already established in previous iterations, the event bus needs further refinement to ensure it can handle the load of price updates that need to be propagated to the query service.

5. **Infrastructure Components**: The underlying infrastructure that supports the above services needs to be refined to ensure it meets the scalability and availability requirements.

### Step 4: Choose one or more design concepts that satisfy the selected drivers

| Selected design concept | Rationale | Discarded Alternatives |
|-------------------------|-----------|------------------------|
| Read Model Optimization | - Tailors data model specifically for query operations<br>- Improves query performance by denormalizing data<br>- Supports the high query volume required by QA-4 | - Normalized data models: Would require joins that reduce query performance<br>- Shared database with write model: Would create contention between read and write operations |
| Multi-Region Deployment | - Provides high availability through geographic redundancy (QA-3)<br>- Reduces latency for geographically distributed users<br>- Enables disaster recovery capabilities | - Single-region deployment: Would not provide sufficient availability guarantees<br>- Global database: Higher complexity and potential consistency issues |
| Distributed Caching | - Dramatically reduces database load for frequent queries<br>- Improves response times for common queries<br>- Scales horizontally to handle increasing load (QA-4) | - Local caching only: Would not scale across multiple instances<br>- No caching: Would put excessive load on the database |
| Horizontal Scaling | - Allows adding more instances to handle increased load<br>- Supports the scalability requirement (QA-4)<br>- Enables elastic scaling based on demand | - Vertical scaling: More expensive and has upper limits<br>- Fixed capacity: Would not accommodate traffic growth |
| API Versioning | - Allows evolution of the API without breaking existing clients<br>- Supports long-term maintainability<br>- Enables gradual adoption of API changes | - No versioning: Would risk breaking changes for integrated systems<br>- Separate endpoints: Would increase maintenance burden |
| Rate Limiting | - Protects the system from excessive query loads<br>- Prevents resource exhaustion from malicious or misconfigured clients<br>- Ensures fair resource allocation | - No rate limiting: Would risk system overload from individual clients<br>- IP-based blocking: Too coarse-grained and can block legitimate users |
| Circuit Breaker for Database Access | - Prevents database overload during peak times<br>- Improves system resilience<br>- Enables graceful degradation | - Direct database access: Would risk database failures under high load<br>- Timeout-only strategies: Would not provide proactive protection |

### Step 5: Instantiate architectural elements, sketch views, allocate responsibilities, and define interfaces

Based on the selected design concepts, we will now refine the architectural elements identified in Step 3 and allocate specific responsibilities to each component.

#### Price Query Service Component Diagram

The Price Query Service is refined into the following components:

| Instantiation decision | Rationale |
|------------------------|-----------|
| Create PriceQueryController component | Provides REST API endpoints for price query operations with versioning support |
| Create PriceQueryRepository component | Implements optimized read model for efficient price data retrieval |
| Create DistributedCacheManager component | Manages the distributed cache for frequently accessed price data |
| Create QueryCircuitBreaker component | Implements circuit breaker pattern for database access to prevent overload |
| Create RateLimiter component | Enforces rate limits on API usage to protect the service |
| Create QueryEventHandler component | Processes price change events from the Event Bus to update the read model |
| Create RegionalReplicationManager component | Manages data replication across multiple regions for high availability |

#### API Gateway Refinement

| Instantiation decision | Rationale |
|------------------------|-----------|
| Add QueryLoadBalancer component | Distributes query traffic across multiple instances of the Price Query Service |
| Add ApiVersionManager component | Handles API versioning and routing to appropriate service versions |
| Add RequestThrottling component | Implements global rate limiting at the gateway level |
| Add RegionalRoutingComponent | Routes requests to the nearest available region for lower latency |

#### Infrastructure Refinement

| Instantiation decision | Rationale |
|------------------------|-----------|
| Implement Multi-Region Kubernetes Clusters | Provides infrastructure for high availability across geographic regions |
| Implement Autoscaling for Query Services | Dynamically adjusts the number of instances based on load |
| Implement Cross-Region Data Replication | Ensures data consistency across regions while maintaining performance |
| Implement Distributed Caching Service | Provides a shared caching layer for all instances of the Price Query Service |

#### Component Responsibilities

**PriceQueryController**:
- Handles HTTP requests for price queries
- Supports versioned API endpoints (e.g., /api/v1/prices)
- Validates input parameters
- Returns appropriately formatted responses
- Implements pagination for large result sets

**PriceQueryRepository**:
- Provides optimized read model access
- Implements efficient query patterns for different price query scenarios
- Supports complex filtering and sorting operations
- Uses denormalized data structures for performance

**DistributedCacheManager**:
- Implements cache-aside pattern for price data
- Manages cache invalidation based on price change events
- Handles cache consistency across instances
- Implements tiered caching strategy (memory, distributed cache)

**QueryCircuitBreaker**:
- Monitors database health and response times
- Opens circuit when database is overloaded
- Provides fallback mechanisms for queries when circuit is open
- Manages graceful recovery when database health improves

**RateLimiter**:
- Enforces per-client rate limits
- Supports different limits for authenticated vs. unauthenticated clients
- Provides feedback on rate limit status via headers
- Logs and monitors rate limit violations

**QueryEventHandler**:
- Subscribes to price change events from the Event Bus
- Updates the read model based on price changes
- Maintains consistency between write and read models
- Handles event replay for recovery scenarios

**RegionalReplicationManager**:
- Coordinates data replication across regions
- Monitors replication lag and health
- Implements conflict resolution strategies
- Supports failover and fallback between regions

#### Interface Definitions

The following interfaces will be defined to support the above components:

1. **Price Query API** (REST)
   - `GET /api/v1/prices?hotelId={id}&date={date}&roomTypeId={id}` - Returns prices for a specific hotel, date, and room type
   - `GET /api/v1/prices/hotels/{hotelId}/dates/{fromDate}/{toDate}` - Returns prices for a hotel within a date range
   - `GET /api/v1/prices/rates/{rateId}` - Returns prices for a specific rate across hotels

2. **Internal Event Handling Interfaces**
   - `PriceChangeEventHandler` - Processes price change events to update the read model
   - `CacheInvalidationEventHandler` - Processes events that trigger cache invalidation

3. **Cache Management Interface**
   - `CacheKey` - Structure for cache keys to ensure consistency
   - `CacheValue` - Structure for cached data
   - `CacheControl` - Settings for TTL and invalidation policies

4. **Metrics and Monitoring Interfaces**
   - `QueryMetrics` - Exposes query performance metrics
   - `CacheMetrics` - Exposes cache hit/miss rates and performance
   - `AvailabilityMetrics` - Exposes uptime and availability data

### Step 6: Record design decisions

The following key design decisions were made during this iteration:

| Driver | Decision | Rationale |
|--------|----------|-----------|
| HPS-3 & QA-4 | Implement denormalized read model for price queries | The read model is specifically tailored for price queries, with denormalized data structures that minimize the need for joins. This optimizes query performance to handle the required volume of 100,000 to 1,000,000 queries per day. The model includes pre-aggregated data and indexes optimized for the most common query patterns. |
| QA-3 | Deploy Price Query Service across multiple geographic regions | Multi-region deployment ensures high availability (99.9% uptime) by eliminating single points of failure and providing geographic redundancy. If one region experiences an outage, traffic can be automatically routed to healthy regions. This approach also reduces latency for geographically distributed users by routing them to the nearest available region. |
| QA-4 | Implement a tiered distributed caching strategy | The caching strategy uses in-memory caches for ultra-fast access to the most frequently queried data, backed by a distributed cache service (such as Redis) for sharing cache data across instances. This dramatically reduces the load on the database during peak times and improves response times for common queries, supporting the scalability requirement. |
| QA-3 & QA-4 | Use horizontal autoscaling for Price Query Service | Kubernetes Horizontal Pod Autoscaling is configured to automatically adjust the number of service instances based on CPU utilization, memory usage, and request rates. This allows the system to elastically scale to handle varying loads, from the minimum requirement of 100,000 queries per day up to 1,000,000 queries, ensuring availability even during peak periods. |
| HPS-3 | Design versioned API with comprehensive query capabilities | The Price Query API is designed with versioning from the start (e.g., /api/v1/...) to allow for future evolution without breaking existing clients. The API supports a wide range of query parameters, pagination, and filtering to accommodate different query patterns from both the web application and external systems. |
| QA-3 & QA-4 | Implement rate limiting at multiple levels | Rate limiting is applied both at the API Gateway level (global limits) and at the Price Query Service level (per-client limits). This protects the system from excessive loads that could impact availability and ensures fair resource allocation during peak times. Different rate limits are applied based on client authentication status, prioritizing authenticated over unauthenticated requests. |
| QA-3 | Implement circuit breaker for database access | The circuit breaker prevents database overload by monitoring health and response times, automatically opening the circuit when issues are detected. In degraded mode, the system can still function by serving cached or slightly stale data, maintaining availability while the database recovers. |
| QA-4 | Design asynchronous event-based read model updates | The read model is updated asynchronously based on events from the Price Management Service. This decouples query performance from write operations and allows the read model to be optimized solely for query performance. The QueryEventHandler ensures eventual consistency between the write and read models. |

### Step 7: Perform analysis of current design and review iteration goal and achievement of design purpose

This step analyzes if the design decisions made during this iteration were sufficient to address the drivers associated with the iteration goal.

| Driver | Analysis Result |
|--------|----------------|
| HPS-3: Query Prices | **Satisfied**. The refined Price Query Service provides comprehensive support for the Query Prices user story. The architecture supports querying prices through a well-designed REST API with multiple endpoints tailored for different query patterns. The versioned API design ensures long-term maintainability and compatibility. The sequence diagram illustrates the flow of query operations, including interactions with caching and database layers. The interfaces have been clearly defined to support all required query functionality for both the web application and external systems. |
| QA-3: Availability | **Satisfied**. The architecture addresses the availability requirement of 99.9% uptime through multiple mechanisms: <br>1. Multi-region deployment for geographic redundancy<br>2. Circuit breaker pattern to prevent system overload<br>3. Rate limiting to protect from excessive requests<br>4. Distributed caching to reduce database dependency<br>5. Regional replication for data availability across regions<br>6. Load balancing to distribute traffic across instances<br>These mechanisms work together to ensure that the system remains available even during regional outages, database issues, or traffic spikes. |
| QA-4: Scalability | **Satisfied**. The architecture ensures that the system can handle the required query volume through:<br>1. Horizontal scaling with autoscaling capabilities<br>2. Optimized read model for efficient queries<br>3. Multi-tiered caching strategy to reduce database load<br>4. Asynchronous read model updates to decouple from write operations<br>5. Load balancing across multiple instances<br>These design elements collectively enable the system to scale elastically from 100,000 queries per day up to 1,000,000 without exceeding the 20% latency degradation limit. The denormalized data structures and optimized indexes further enhance query performance at scale. |

Overall, all drivers targeted for Iteration 3 have been satisfied through the design decisions documented in the Architecture and Iteration documents. The Price Query Service has been refined to support efficient and scalable price queries with high availability. The API Gateway has been enhanced to handle load balancing and rate limiting. The infrastructure has been designed to support multi-region deployment and horizontal scaling.

The sequence diagram for HPS-3: Query Prices illustrates how these components work together to provide price information to users and external systems, including scenarios for cache hits, cache misses, and circuit breaker activation. The interface definitions provide clear contracts for interacting with the system, including appropriate rate limiting headers.

The next iteration can now proceed to focus on hotel and rate management functionality as outlined in the Iteration Plan. 