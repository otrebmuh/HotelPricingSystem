# Hotel Pricing System - ADD Iteration 5: Security and User Management

This document describes the fifth iteration of the Attribute-Driven Design (ADD) process for the Hotel Pricing System. This iteration focuses on security aspects of the system, including user authentication and authorization, as well as ensuring the system supports multiple platforms through a web interface.

## Step 2: Establish Goal for the Iteration by Selecting Drivers

The goal of this iteration, as defined in the Iteration Plan, is to design security and user management capabilities. The drivers associated with this goal are:

- **HPS-1: Log In** - A user (commercial or administrator) provides their credentials in a login window. The system checks these credentials against a user identity service and, if successful, provides access to the system. Once logged in, a user can only make queries and changes to the hotels for which they are authorized.
- **HPS-6: Manage Users** - An administrator changes permissions for a given user.
- **QA-5: Security** - A user logs into the system through the front-end. The credentials of the user are validated against the User Identity Service and, once logged in, they are presented with only the functions that they are authorized to use.
- **CON-1: Multi-platform web interface** - Users must interact with the system through a web browser in different platforms Windows, OSX, and Linux, and different devices.
- **CON-2: Cloud provider identity service** - Manage users through cloud provider identity service and host resources in the cloud.

## Step 3: Choose One or More Elements of the System to Refine

Based on the drivers for this iteration, the following elements from the current architecture will be refined:

1. **Authentication Service** - This element needs to be detailed with components that handle user authentication, token management, and integration with the cloud provider's identity service.

2. **API Gateway** - The security aspects of the API Gateway need to be refined, particularly its role in authorization and access control.

3. **Web Application & Admin Portal** - The multi-platform user interface aspects need to be addressed, particularly responsive design, accessibility, and consistent authentication/authorization behaviors.

4. **User Management Interface** - A new component needs to be defined to allow administrators to manage user permissions.

The current architecture already includes a basic outline of these components, but this iteration will refine them with a focus on security patterns, authentication flows, and authorization models.

## Step 4: Choose Design Concepts That Satisfy the Selected Drivers

|Selected design concept|Rationale|Discarded Alternatives|
|---|---|---|
|OAuth 2.0 / OpenID Connect|Industry standard protocols for authentication and authorization that integrate well with cloud identity providers. Provides secure token-based authentication with well-defined flows for different use cases.|Custom authentication protocol - Discarded due to security risks and maintenance overhead.|
|JWT (JSON Web Tokens)|Enables stateless authentication and secure information exchange. JWTs can store user permissions and are compatible with OAuth 2.0 flows.|Session-based authentication - Discarded due to scalability concerns and complexity in a distributed architecture.|
|RBAC (Role-Based Access Control)|Simplifies permission management by assigning permissions to roles rather than individual users. Well-suited for hotel chain with clear organizational roles.|ABAC (Attribute-Based Access Control) - Discarded due to unnecessary complexity for the current requirements.|
|BFF (Backend for Frontend) Pattern|Provides tailored APIs for different frontend clients, simplifying authentication across platforms and optimizing data transfer.|Generic API - Discarded because it wouldn't optimize for different frontend needs.|
|Responsive Web Design|Allows a single web application to adapt to different screen sizes and devices, supporting the multi-platform requirement.|Native mobile applications - Discarded due to development time constraints and maintenance overhead.|
|API Gateway Security Pattern|Centralizes authentication, authorization, and security controls at the gateway level, providing consistent security enforcement.|Distributed security enforcement - Discarded because it would increase complexity and risk of inconsistent implementation.|
|Single Sign-On (SSO)|Leverages the cloud provider's identity service for authentication, simplifying user experience and centralizing identity management.|Custom identity provider - Discarded due to complexity and conflict with CON-2.|
|User Management Microservice|Separates user management functionality from core business logic while maintaining a clear bounded context.|Embedding user management in each service - Discarded due to duplication, complexity, and maintenance challenges.| 

## Step 5: Instantiate Architectural Elements, Sketch Views, Allocate Responsibilities, and Define Interfaces

### 5.1 Component Instantiation Decisions

|Instantiation decision|Rationale|
|---|---|
|Implement Authentication Service with OIDC Client and Token Service components|Provides a clean separation of concerns with dedicated components for interacting with the cloud identity provider and for managing security tokens.|
|Implement User Management Service with dedicated components for user authorization management|Separates the management of external identities from application-specific authorization data, maintaining a clear bounded context.|
|Refine API Gateway with Authorization Validation and Permission Enforcement components|Centralizes the security enforcement at the gateway level, ensuring consistent application of security policies across all services.|
|Implement BFF layers for Web Application and Admin Portal|Optimizes API interactions for different client needs, improving performance and simplifying client-side development.|
|Implement Responsive UI components with role-based menu rendering|Ensures users only see functionality they are authorized to use while providing a consistent experience across devices.|

Note: The detailed component diagrams, sequence diagrams, and interface definitions have been added to the Architecture.md document.

## Step 6: Record Design Decisions

The design decisions from this iteration have been recorded in the Architecture.md document including:

1. New component diagrams:
   - Authentication Service with detailed components (Section 6.5)
   - API Gateway Security Components (Section 6.6)

2. Updated and new sequence diagrams:
   - Updated HPS-1: Log In sequence diagram
   - New HPS-6: Manage Users sequence diagram
   - New QA-5: Security enforcement sequence diagram

3. New interface definitions:
   - Authentication Service API (Section 8.5)
   - User Management API (Section 8.6)
   - Frontend BFF APIs for both Web Application and Admin Portal

4. Design decisions added to Section 10 (Design Decisions):
   - Implementation of OAuth 2.0/OpenID Connect
   - JWT-based token management
   - Role-Based Access Control (RBAC)
   - Responsive Web Design for multi-platform support
   - Centralized permission management
   - Security Audit Logging
   - API Gateway Security Enforcement 

## Step 7: Perform Analysis of Current Design and Review Iteration Goal Achievement

This section analyzes whether the design decisions made during Iteration 5 were sufficient to address the drivers associated with the iteration goal.

|Driver|Analysis result|
|---|---|
|HPS-1: Log In|**Satisfied**. The refined Authentication Service with OAuth 2.0/OIDC integration provides a secure and industry-standard approach to user authentication. The updated sequence diagram demonstrates the authentication flow with the cloud identity provider, and the JWT token generation ensures secure access to protected resources.|
|HPS-6: Manage Users|**Satisfied**. The User Management interface added to the Admin Portal, along with the User Authorization Manager component in the Authentication Service, provides administrators with the necessary capabilities to manage user permissions. The sequence diagram for HPS-6 illustrates the complete user management flow.|
|QA-5: Security|**Satisfied**. The security controls implemented at the API Gateway level (Token Validator and Permission Enforcer) ensure that users can only access the functions they are authorized to use. The RBAC model provides a clear mechanism for defining and enforcing permissions based on user roles. The QA-5 sequence diagram shows how authorization is enforced for protected resources.|
|CON-1: Multi-platform web interface|**Satisfied**. The adoption of Responsive Web Design ensures that users can interact with the system through web browsers on different platforms (Windows, OSX, Linux) and devices. The BFF pattern optimizes API interactions for different client needs, improving the user experience across platforms.|
|CON-2: Cloud provider identity service|**Satisfied**. The Authentication Service's OIDC Client component integrates directly with the cloud provider's identity service, meeting the requirement to manage users through this service. The sequence diagram for HPS-1 shows how the system delegates authentication to the cloud identity service.|

### Overall Achievement

All drivers associated with the iteration goal have been satisfied. The architecture now includes a comprehensive security model that addresses authentication, authorization, and user management requirements. The design leverages industry-standard security protocols and practices while maintaining the ability to support multiple platforms through a responsive web interface.

The refined Authentication Service and API Gateway components, along with the new User Management functionality, form a cohesive security framework that protects system resources while providing appropriate access to authorized users. The implementation of RBAC simplifies permission management, and the integration with the cloud provider's identity service fulfills the specified constraint.

The detailed component diagrams, sequence diagrams, and interface definitions provide a solid foundation for implementation, and the design decisions reflect careful consideration of security best practices, performance requirements, and maintainability concerns. 