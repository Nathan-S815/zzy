# GaoChen Travel Digital Tourism Platform

GaoChen Travel is a multi-module Java/Spring backend workspace for digital tourism services. The codebase includes service modules for ticketing, hotel services, scenic attraction operations, travel-team workflows, platform administration, and data-oriented support tools.

## Modules

| Module | Purpose |
|---|---|
| `ticket` | Ticket products, mall orders, trade workflows, supplier processes, refund handling, payment callbacks, electronic ticket parsing, and open API endpoints. |
| `hotel` | Hotel service modules using the same layered backend architecture and infrastructure patterns as the wider travel platform. |
| `attract` | Scenic attraction and travel-team workflows, merchant and official account review, customer imports, reward calculation, image upload, and regional data. |
| `discovery` | Scenic-spot discovery modules, client contracts, infrastructure services, generators, and API startup modules. |
| `zeus` | Platform capabilities such as merchant applications, permission and menu management, app pages, materials, SMS, and administration. |
| `ymtdp`, `edw`, `mysqldocument` | Data warehouse and database-documentation support modules. |

## Technical Stack

- Java / Spring Boot
- Spring Cloud Alibaba / Nacos
- OpenFeign service clients
- Maven multi-module projects
- MyBatis-Plus and MySQL
- Redis / Redisson
- Shiro / JWT-style authentication
- Druid connection pool
- Payment-related workflows
- Object storage integrations
- Excel import/export support

## Architecture Pattern

Most service projects follow a layered structure:

| Layer | Description |
|---|---|
| `*-client` | DTOs and Feign API contracts for inter-service communication. |
| `*-app` | Application services, command executors, query handlers, business orchestration, and event handlers. |
| `*-infrastructure` | Database entities, mappers, services, Redis helpers, object storage helpers, enums, and third-party integrations. |
| `*-start` | Spring Boot startup modules and API/controller entry points. |
| `*-generator` | Code generation utilities for service scaffolding and database entities. |

## Notes

Sensitive configuration values such as credentials, access keys, private endpoints, and secrets are represented with placeholders in this public repository.
