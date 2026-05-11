# Customer Registry Service

Small customer registry service used for technical assessment work. It manages customer profiles, accounts, addresses, consents, support cases, and audit events through a REST API.

## Stack

| Area | Choice |
| --- | --- |
| Java | 17 |
| Spring Boot | 2.7.18 |
| Gradle wrapper | 7.6.6 |
| Database | H2 in-memory |
| Migrations | Flyway |
| API docs | OpenAPI |

## Running locally

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
http://localhost:8080/swagger-ui.html
```

## Data handling

The test resources include sensitive data.
