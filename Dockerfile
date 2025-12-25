# Use Java 17 (recommended for Spring Boot)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom first (for better caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Render will provide PORT, fallback to 8082 locally
EXPOSE 8082

# Run the Spring Boot jar
CMD ["java", "-jar", "target/*.jar"]
