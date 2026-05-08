# Telenor AI Interview

Skeleton repository for an agentic AI interview exercise.

The project intentionally starts as a legacy Spring Boot 2.7 Gradle application with no application code checked in yet. The application code and interview tasks can be defined later, while the baseline tooling is already in place.

## Baseline

| Area | Choice |
| --- | --- |
| Spring Boot | 2.7.18 |
| Gradle wrapper | 7.6.6 |
| Java toolchain | 17 |
| Build DSL | Groovy |

## Requirements

Use JDK 17 as `JAVA_HOME` when running Gradle. This is intentional for the Spring Boot 2.7 baseline; upgrading the wrapper is part of a future Spring Boot 4 migration.

## Commands

```bash
./gradlew build
```

When application code is added, the configured main class is `no.telenor.ai.interview.InterviewApplication`.
