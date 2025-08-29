# 🐐 Encourtner

A robust and feature-rich URL shortening service built with Spring Boot and MongoDB. This project provides a RESTful API
for creating and managing short URLs, with a clean redirection flow for end-users.

## 🛠️ Tech Stack

- Backend:
    - Java 17
    - Spring Boot 3
    - Spring Data MongoDB
    - Lombok
- Database:
    - MongoDB
- Testing:
    - JUnit 5
    - Mockito
    - MockMvc (Spring Test)
- API Documentation:
    - Springdoc OpenAPI v3 (Swagger UI)

### 📑 API Documentation

This project includes an interactive Swagger UI for exploring the API endpoints. Once the application is running, you
can access it at:

```
http://localhost:8080/swagger-ui.html
```

## 🚀 Running the Project

Follow these instructions to get the project up and running on your local machine.

### ⚠️ Prerequisites:

- Java (JDK) 17 or newer.
- Apache Maven
- MongoDB running locally or on a server/Docker container.

### Installation and setup

1. Clone the repository

```bash
git clone git@github.com:YuriBrunetto/encourtner.git
cd encourtner/
```

2. Configure the database connection
    - Open the `src/main/resources/application.properties` file and add your MongoDB connection details.

```bash
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/url-shortener-db

# Disable default error trace in API responses
server.error.include-stacktrace=never
```

3. Build and run the application

```bash
mvn spring-boot:run
```

## ✅ Running the tests

To run the full suite of unit and integration tests, execute the following Maven command from the project root:

```bash
mvn test
```