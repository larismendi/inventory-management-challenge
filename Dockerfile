FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/inventory-management-0.0.1-SNAPSHOT.jar /app/inventory-management.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080
CMD ["/app/wait-for-it.sh", "mysql-db:3306", "--", "java", "-jar", "/app/inventory-management.jar"]
