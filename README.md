# Decorativka Backend

## Overview

Welcome to the Decorativka Backend project! This is a robust and scalable backend solution designed specifically for stores specializing in building materials such as decorative plaster, paints, wallpapers, and more. Our goal is to provide a seamless and efficient system that handles various functionalities essential for running a modern e-commerce platform.

This project leverages the power of Java 17, the Spring Framework, and Hibernate, ensuring a high level of performance and reliability. The backend is containerized with Docker, making it easy to deploy and manage across different environments. We use PostgreSQL for our database, and the project is built with Maven. Our deployment pipeline is streamlined with GitHub Actions, ensuring continuous integration and delivery to Amazon Web Services (AWS).

## Features

- **Product and Service Access**: Comprehensive endpoints to access a wide range of products and services.
- **Feedback Controller**: Enables users to submit and manage feedback, enhancing customer interaction.
- **Order Controller**: Simplifies the process of creating and managing orders, ensuring a smooth shopping experience.
- **Admin Controller**: Equipped with JWT tokens for secure authorization, allowing administrators to create, update, and view orders, as well as manage product data.
- **Telegram Bot**: Facilitates convenient order management for administrators through an easy-to-use interface.
- **New Post Integration**: Seamlessly integrates with the New Post API to retrieve mission lists and send emails via Java Mail Sender.
- **Swagger Documentation**: Detailed API documentation is available, making it easy to understand and utilize the backend capabilities.
- **Liquibase**: Manages database migrations efficiently, ensuring smooth updates and version control.
- **Global Exception Handler**: Provides a consistent approach to error handling across the application.
- **Search Parameters**: Implemented search functionality for products and offers, allowing users to find what they need quickly and easily.
- **Testing**: Comprehensive test coverage ensures the reliability and robustness of the system.

## Technologies Used

- **Java 17**
- **Spring Framework**
  - Spring Boot
  - Spring Security
- **Hibernate**
- **PostgreSQL**
- **Docker**
- **Maven**
- **JWT Tokens**
- **Java Mail Sender**
- **Liquibase**
- **Swagger**

## Deployment

The project is deployed on Amazon Web Services (AWS) and is publicly accessible. It includes a continuous integration pipeline for automatic deployment using GitHub Actions.

- **Swagger UI**: [API Documentation](http://ec2-13-53-106-198.eu-north-1.compute.amazonaws.com/api/swagger-ui/index.html#/)
- **GitHub Repository**: [Decorativka Backend Java](https://github.com/decor-team-37/decorativka-backend-java)

## Getting Started

To get a local copy of the project up and running, follow these steps.

### Prerequisites

- Docker
- Java 17
- Maven

# API Documentation

## Endpoints

### Product Endpoints
- **GET /products:** Retrieve a list of all products.
- **GET /products/{id}:** Retrieve details of a specific product by ID.
- **POST /products:** Create a new product (Admin only).
- **PUT /products/{id}:** Update an existing product (Admin only).
- **DELETE /products/{id}:** Delete a product (Admin only).

### Offer Endpoints
- **GET /offers:** Retrieve a list of all offers.
- **GET /offers/{id}:** Retrieve details of a specific offer by ID.
- **POST /offers:** Create a new offer (Admin only).
- **PUT /offers/{id}:** Update an existing offer (Admin only).
- **DELETE /offers/{id}:** Delete an offer (Admin only).

### Feedback Endpoints
- **GET /feedback:** Retrieve a list of all feedback.
- **POST /feedback:** Submit new feedback.

### Order Endpoints
- **GET /orders:** Retrieve a list of all orders.
- **GET /orders/{id}:** Retrieve details of a specific order by ID.
- **POST /orders:** Create a new order.
- **PUT /orders/{id}:** Update an existing order.
- **DELETE /orders/{id}:** Delete an order.

### Admin Endpoints
- **POST /admin/login:** Admin login to receive a JWT token.
- **GET /admin/orders:** Retrieve a list of all orders (Admin only).
- **POST /admin/products:** Create a new product (Admin only).
- **PUT /admin/products/{id}:** Update an existing product (Admin only).
- **DELETE /admin/products/{id}:** Delete a product (Admin only).

## Search Parameters

### Product Search
Use query parameters to search for products by:
- Name
- Category
- Price range
- Other relevant attributes

### Offer Search
Use query parameters to search for offers by:
- Discount
- Validity period
- Other relevant attributes

## Continuous Integration

The project is configured with a CI pipeline that includes:

- **Checkstyle:** Ensures code quality and adherence to coding standards.
- **Deployment:** Automatically builds and deploys the project to AWS.


## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/decor-team-37/decorativka-backend-java.git
    ```
2. Navigate to the project directory:
    ```sh
    cd decorativka-backend-java
    ```
3. Create .env file with all required variables.
  ```env
POSTGRESQL_USER=user
POSTGRESQL_ROOT_PASSWORD=password
POSTGRESQL_PASSWORD=password
POSTGRESQL_DATABASE=database_name
POSTGRESQL_LOCAL_PORT=5433
POSTGRESQL_DOCKER_PORT=5432
SPRING_LOCAL_PORT=8081
SPRING_DOCKER_PORT=8080
DEBUG_PORT=5005
JWT_EXPIRATION_TIME=10000000
JWT_SECRET=secretcode
MAIL_USERNAME=mail
MAIL_HOST=mailhost
MAIL_PORT=mailport
MAIL_PASSWORD=mailpassword
TELEGRAM_BOT_USERNAME=botusername
TELEGRAM_BOT_TOKEN=bottoken
NOVA_POSHTA_API_KEY=novaposhtaapikey
  ```
4. Build the Docker image:
```sh
docker build -t decorativka-backend .
```

5. Run the Docker container:
```sh
docker run -p 8080:8080 decorativka-backend
```
Running the Application

Once the Docker container is running, the application will be available at http://localhost:8081.

Usage
Accessing the API
To access the API, use the Swagger UI or send requests directly to the endpoints as documented.

Admin Access
To perform administrative tasks such as creating, updating, and viewing orders, use the admin controller with JWT token authorization.

Telegram Bot
The Telegram bot provides an interface for administrators to manage orders. Details on setting up and using the bot can be found in the project's documentation.

