# Feature Flag Subscription Platform

This project is a Feature Flag management API built with Spring Boot, MySQL, Flyway, and JPA. It supports client-specific feature flag toggling, dependency handling, and includes unit tests written in a TDD approach.

## Features

- Create and manage feature flags.
- Assign flags to clients.
- Toggle feature flags per client.
- Set parent-child dependencies between feature flags.
- View enabled flags per client.
- TDD-compliant test coverage with positive and negative test cases.

## Core Entities

- Client
- Feature Flag
- Client Feature Flag
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

---

## Running the Application

### 1. Start MySQL using Docker

```
docker-compose up -d
```

MySQL will run on `localhost:3306` with user `root` and password `root`.

### 2. Run the Application

```
mvn spring-boot:run
```

Or using IntelliJ/VSCode, run `FeatureFlagApplication.java`.

The API will be accessible at: [http://localhost:8080](http://localhost:8080)

---

## Running Tests

### Run all tests:

```
mvn test
```

### Run specific test class:

Example if you want to run `FeatureFlagControllerTest.java`
```
mvn -Dtest=FeatureFlagControllerTest test
```


### Generate Coverage Report

Run the command: ```mvn verify``` and open the report:
```
target/site/jacoco/index.html
```
