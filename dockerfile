# Use OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Pre-download dependencies (optional for caching)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/feature_flag-0.0.1-SNAPSHOT.jar"]
