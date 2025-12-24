FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Give execute permission to mvnw
RUN chmod +x ./mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

EXPOSE 8082

CMD ["java", "-jar", "target/*.jar"]
