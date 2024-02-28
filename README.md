# Product Review App

REST api service for managing products and product reviews.

## Prerequisites

Before you begin, ensure you have met the following requirements:
• Java 17
• Maven for project dependency management and build
• IntelliJ IDEA or your preferred IDE with support for Spring Boot and Maven

# Setup and Installation

## 1. Clone the repository

First, clone the repository to your local machine using Git:
git clone https://github.com/ijeremic89/product-review.git

## 2. Database Configuration

This project uses an H2 in-memory database for development purposes, so no additional setup is required for the database. However, ensure the
application.properties file is correctly configured as follows:

spring.datasource.url=jdbc:h2:mem:productReview;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=none
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.flyway.enabled=true

## 3. Running the Application

You can run the application directly from your IDE by running the main application class, found at
src/main/java/tis/productReview/ProductReviewApplication.java.
Alternatively, you can use Maven from the command line:
mvn spring-boot:run

## 4.Accessing the H2 Database Console

To access the H2 Database Console:
• Navigate to http://localhost:8080/h2-console in your web browser.
• Use the JDBC URL jdbc:h2:mem:productReview, with username sa and an empty password to log in.

## 5. Running Flyway Migrations

Flyway migrations should run automatically upon application startup. However, if you need to manually trigger migrations or manage the database
schema, you can use the following Maven command:
mvn flyway:migrate
Ensure your src/main/resources/db/migration directory contains the correct SQL migration scripts.

# Usage
After starting the application, you can access the REST API endpoints provided by your application at http://localhost:8080.

# Tests

To run tests locally run command mvn test