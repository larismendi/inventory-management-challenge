FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/inventory-management-0.0.1-SNAPSHOT.jar /app/inventory-management.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/inventory-management.jar"]
