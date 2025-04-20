# Feature Flag Subscription Platform

This project is a Feature Flag management API built with Spring Boot, MySQL, and JPA. It supports client-specific feature flag toggling, dependency handling, and includes unit tests written in a TDD approach.

## Features

- Create and manage feature flags.
- Assign flags to clients.
- Toggle feature flags per client.
- Set parent-child dependencies between feature flags.
- View enabled flags per client.

## Core Entities

- Client
- Feature Flag
- Client Feature Flag
- Feature Flag Dependency
---

## API Endpoints

| Method | Endpoint                                              | Description                            |
|--------|-------------------------------------------------------|----------------------------------------|
| POST   | `/clients?name={name}`                                | Create a client                        |
| GET    | `/clients?id={id}`                                    | Fetch a client                         |
| POST   | `/flags/create?name={name}&description={description}` | Create a new feature flag              |
| POST   | `/flags/dependency?parent={name}&child={name}`        | Add parent-child flag dependency       |
| POST   | `/flags/setclientId={id}&flag={name}&status={status}` | Enable/disable a flag for a client     |
| GET    | `/flags/status?clientId={id}&flag={name}`             | Get status of a specific flag          |
| GET    | `/flags/enabled?clientId={id}`                        | Get all enabled flags for a client     |
| GET    | `/flags/all`                                          | Get all flags in system                |

---
## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- Docker & Docker Compose

## Running the Application

### Option 1: Using Docker Compose (Recommended)

This option runs both the MySQL database and the application in Docker containers:

```bash
# Start both the database and application
docker-compose up -d

# To view logs
docker-compose logs -f

# To stop all containers
docker-compose down
```

When you run `docker-compose up -d`:
- `feature_flag_mysql` - MySQL database container
- `feature_flag_app` - Spring Boot application container

The API will be accessible at: [http://localhost:8080](http://localhost:8080)

### Option 2: Running the Application with Local Maven

If you prefer to run both the application & Database locally:
- Create a schema named `featureflags`
- Run the `CREATE` commands inside `./src/main/resources/db/V1__init.sql`

```bash

# Run the application with Maven
mvn spring-boot:run
```

You can also run the application from your IDE by executing the `FeatureFlagApplication.java` main class.

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run a Specific Test Class

For example, to run only the `FeatureFlagControllerTest`:

```bash
mvn -Dtest=FeatureFlagControllerTest test
```

### Generate and View Test Coverage Report

The project uses JaCoCo for test coverage reporting:

```bash
# Generate test coverage report
mvn verify

# The report is generated at:
target/site/jacoco/index.html
```

Open the HTML file in your browser to view the detailed coverage report.