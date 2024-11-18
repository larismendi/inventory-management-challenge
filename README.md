# Inventory Management Application

This project is a Spring Boot-based application designed for managing product inventory. It includes REST APIs for CRUD
operations and integrates with a MySQL database.

---

## **Features**

- RESTful APIs for product management.
- Profiles for development (`dev`) and testing (`test`).
- Integration with MySQL for development and H2 in-memory database for testing.
- Dockerized setup for easy deployment.

---

## **Technologies Used**

- Java 17
- Spring Boot
- MySQL
- H2 Database (for testing)
- Docker & Docker Compose
- Maven

---

## **Getting Started**

### **Prerequisites**

Ensure you have the following installed:

- Docker & Docker Compose
- Java 17
- Maven (if you need to build locally)

---

### **Running Locally with Docker**

1. Clone the repository:
   ```bash
   git clone https://github.com/larismendi/inventory-management-challenge.git
   cd inventory-management

2. Build and start the application using Docker Compose:
    ```bash
   docker-compose up --build

3. The application will be available at:

    - API: http://localhost:8080
    - MySQL Database: localhost:3306

4. Credentials for MySQL (default development configuration):

    - Database: inventory_management
    - Username: user
    - Password: password

---

### **Running Locally Without Docker**

1. Install MySQL and create a database:

    ```sql
    CREATE DATABASE inventory_management;
    CREATE USER 'user'@'%' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON inventory_management.* TO 'user'@'%';
    FLUSH PRIVILEGES;

2. Update your application-dev.properties to point to your local MySQL instance.

3. Build the application:

    ```bash
    mvn clean install

4. Run the application:

    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev

---

## **Running Tests**

- The application uses the test profile by default for running tests with the H2 database. To execute the tests:

  ```bash
  mvn test

---

## **Environment Profiles**

- Development (dev): Connects to a MySQL database.
- Testing (test): Uses an H2 in-memory database.
- By default, the application runs with the test profile. To switch profiles, set the environment variable:**

    ```bash
    export SPRING_PROFILES_ACTIVE=dev

- Or pass it as a JVM argument:

    ```bash
    -Dspring.profiles.active=dev

---

## **Project Structure**

    src
    ├── main
    │ ├── java/com/example/inventorymanagement # Application source code
    │ └── resources # Application configuration files
    │     ├── application.properties # Default configuration (dev profile)
    │     ├── application-dev.properties # Development configuration
    │     ├── application-test.properties # Test configuration
    └── test # Unit and integration tests

---

## **Endpoints**

| Method | Endpoint           | Description                |
|--------|--------------------|----------------------------|
| GET    | /api/products      | Get all products           |
| GET    | /api/products/{id} | Get a product by ID        |
| POST   | /api/products      | Create a new product       |
| PUT    | /api/products/{id} | Update an existing product |
| DELETE | /api/products/{id} | Delete a product           |

---

## **Known Issues**

1. Ensure the MySQL container is fully initialized before the application connects.
2. Always use the correct application-dev.properties configuration for development.
