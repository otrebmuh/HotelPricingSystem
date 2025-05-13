# Hotel Pricing System - Iteration Plan

This document outlines the planned iterations for the design of the Hotel Pricing System, following the Attribute-Driven Design process. The plan is structured to address high-priority requirements early, with a focus on business-critical functionality.

## Iteration Plan Overview

| Iteration | Goal | Drivers to Address |
|-----------|------|-------------------|
| 1 | Establish overall system structure and deployment model | CRN-1: Establish an overall initial system structure<br>CON-6: Cloud-native approach<br>CON-2: Cloud resource hosting and identity service<br>CRN-5: Set up continuous deployment infrastructure<br>QA-7: Deployability |
| 2 | Design core pricing calculation and publication functionality | HPS-2: Change Prices (Primary user story)<br>QA-1: Performance<br>QA-2: Reliability<br>CON-5: Initial REST API integration with existing systems |
| 3 | Design query capabilities and scalability | HPS-3: Query Prices (Primary user story)<br>QA-3: Availability<br>QA-4: Scalability |
| 4 | Implement hotel and rate management | HPS-4: Manage Hotels (Primary user story)<br>HPS-5: Manage Rates |
| 5 | Design security and user management | HPS-1: Log In<br>HPS-6: Manage Users<br>QA-5: Security<br>CON-1: Multi-platform web interface |
| 6 | Enhance modularity and monitoring capabilities | QA-6: Modifiability<br>QA-8: Monitorability<br>QA-9: Testability<br>CRN-4: Avoid technical debt |

## Detailed Iteration Goals

### Iteration 1: Establish Overall System Structure
The first iteration focuses on defining the overall architecture and deployment model for the system. This includes identifying the major system components, their relationships, and how they will be deployed in a cloud environment. This iteration addresses the fundamental architectural concern of establishing an initial system structure (CRN-1) while considering cloud-native approaches (CON-6) and continuous deployment infrastructure (CRN-5).

### Iteration 2: Design Core Pricing Calculation and Publication
The second iteration focuses on the core business functionality of the system - changing prices (HPS-2). This includes designing components that handle price calculation and publication to other systems. The design will address performance requirements (QA-1) to ensure price calculations are completed within 100ms and reliability requirements (QA-2) to ensure 100% of price changes are successfully published.

### Iteration 3: Design Query Capabilities and Scalability
The third iteration focuses on designing the query capabilities of the system (HPS-3), which is another core business functionality. This iteration addresses availability requirements (QA-3) to achieve 99.9% uptime for pricing queries and scalability requirements (QA-4) to handle up to 1,000,000 queries per day.

### Iteration 4: Implement Hotel and Rate Management
The fourth iteration focuses on designing the functionality to manage hotels (HPS-4) and rates (HPS-5). This iteration addresses the requirement to add, change, or modify hotel information, including tax rates, available rates, and room types, as well as the requirement to define calculation business rules for different rates.

### Iteration 5: Design Security and User Management
The fifth iteration focuses on security aspects (QA-5) of the system, including user authentication (HPS-1) and authorization (HPS-6). This iteration also addresses the constraint of supporting multiple platforms through a web interface (CON-1).

### Iteration 6: Enhance Modularity and Monitoring Capabilities
The final iteration focuses on enhancing the system's modularity (QA-6), monitoring capabilities (QA-8), and testability (QA-9). This iteration also addresses the concern of avoiding technical debt (CRN-4) by ensuring the system is well-structured, maintainable, and extensible.

## Implementation Timeline

This iteration plan supports the constraint (CON-4) that the initial release must be delivered in 6 months, with an MVP demonstrated in 2 months:

- Iterations 1 and 2 (first 2 months): Focus on establishing the system structure and implementing core pricing functionality, which will serve as the MVP for internal stakeholders.
- Iterations 3-6 (next 4 months): Gradually enhance the system with additional functionality and quality attributes to complete the full initial release in 6 months.

## Alignment with Development Team

The iteration plan takes into account the team's knowledge about Java technologies and the Angular framework (CRN-2), with work being allocated to team members based on their expertise (CRN-3). 