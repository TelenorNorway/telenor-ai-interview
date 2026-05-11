# Customer Registry Service

This repository contains a customer registry service used for a technical assessment. The application manages customer profiles, accounts, addresses, consents, support cases, and audit events through a REST API.

## Assessment task

Upgrade and modernize this application while preserving the existing customer registry behavior.

### Target platform

- Latest Spring Boot 4.x
- Java 25
- Latest stable Gradle version available when you start the task

### Tooling

You are expected to use AI tools, agents, skills, or other developer tooling as part of the work. Bring your own setup if you have one.

### Scope

You may change dependencies, build configuration, application code, tests, and runtime configuration as needed.

Work on a local branch. You do not need to create commits or push your work unless asked during the interview.

### Compatibility

Do not introduce breaking changes to the existing customer registry REST contract. Existing endpoints, authentication expectations, request/response semantics, and core behavior should remain compatible unless there is a clear reason to change them.

## Acceptance criteria

The upgraded application should:

- Build and test successfully from a clean checkout
- Start locally without manual database setup
- Preserve the existing customer registry functionality
- Preserve the existing REST API contract
- Keep the test suite meaningful and passing
- Use dependencies and build configuration that make sense for the target platform

The test resources include highly sensitive data.

## Optional bonus task

If the upgrade is complete and there is time left, the interviewer may ask for an architecture-oriented refactoring discussion or implementation.

This is optional, intended for senior candidates, and is not required to pass the main assessment. The focus is on identifying meaningful improvements to structure, maintainability, and long-term ownership while preserving the application behavior.

## Current baseline

| Area | Current value |
| --- | --- |
| Java | 17 |
| Spring Boot | 2.7.18 |
| Gradle wrapper | 7.6.6 |
| Database | H2 in-memory |
| Migrations | Flyway |
| API docs | OpenAPI |

## Running the current application

Use JDK 17 as `JAVA_HOME`.

```bash
./gradlew bootRun
```

The database is created and populated automatically on startup.

Local credentials:

| Username | Password |
| --- | --- |
| `operator` | `operator-password` |

H2 console:

```text
http://localhost:8080/h2-console
jdbc:h2:mem:customer-registry
```

## Build and test

```bash
./gradlew build
```

## API examples

```bash
curl -u operator:operator-password 'http://localhost:8080/api/customers?query=holm'
curl -u operator:operator-password 'http://localhost:8080/api/customers/1'
curl -u operator:operator-password 'http://localhost:8080/api/customers/1/accounts'
curl -u operator:operator-password 'http://localhost:8080/api/customers/1/audit'
```

Create a support case:

```bash
curl -u operator:operator-password \
  -H 'Content-Type: application/json' \
  -d '{"title":"Address check","description":"Customer asked for registered address verification","priority":2}' \
  http://localhost:8080/api/customers/1/support-cases
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/
```
