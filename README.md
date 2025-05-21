# Project Overview

This project consists of two main microservices:

*   **Client API (`api_clientes`)**: Handles user management, including user creation and retrieval.
*   **Product API (`api_productos`)**: Manages products and purchases, allowing for product listing, creation, updates, deletion, and purchase recording.

Both services are built using Spring Boot and utilize MySQL databases.

## Client API (`api_clientes`)

This service is responsible for managing user accounts.

### Endpoints

*   `GET /user`
    *   **Description**: Retrieves an existing user's details.
    *   **Request Parameters**:
        *   `username` (string): The username of the user.
        *   `password` (string): The user's password (Base64 encoded).
    *   **Responses**:
        *   `200 OK`: Returns the user object if credentials are correct.
        *   `404 Not Found`: If the user does not exist or the password is incorrect.

*   `POST /login`
    *   **Description**: Registers a new user. (Note: Despite the name, this endpoint is for user creation/signup, not login).
    *   **Request Body**: `User` object (JSON)
        ```json
        {
          "username": "testuser",
          "password": "encodedPassword", // Base64 encoded
          "email": "testuser@example.com"
        }
        ```
    *   **Responses**:
        *   `201 Created`: Returns the created user object.
        *   `400 Bad Request`: If a user with the given username already exists.

### Database

This service uses a MySQL database. The schema initialization script can be found in `api_clientes/init-db.sql`.

### How to Build and Run

1.  **Navigate to the service directory**:
    ```bash
    cd api_clientes
    ```
2.  **Build with Maven**:
    ```bash
    ./mvnw clean package
    ```
3.  **Run using Docker Compose** (this will also start the required MySQL database):
    ```bash
    docker-compose up --build
    ```
    Alternatively, you can run the Spring Boot application directly using your IDE or via `./mvnw spring-boot:run` after setting up the database manually.

## Product API (`api_productos`)

This service is responsible for managing products and recording purchases.

### Product Endpoints (`/product`)

*   `GET /product`
    *   **Description**: Retrieves a list of products. Can be filtered by name and/or price range.
    *   **Request Parameters (optional)**:
        *   `name` (string): Filter by product name (contains match).
        *   `minPrice` (double): Filter by minimum price.
        *   `maxPrice` (double): Filter by maximum price.
    *   **Responses**:
        *   `200 OK`: Returns a list of products.

*   `POST /product`
    *   **Description**: Creates a new product.
    *   **Request Body**: `Product` object (JSON)
        ```json
        {
          "name": "Example Product",
          "description": "This is a great product.",
          "price": 29.99
        }
        ```
    *   **Responses**:
        *   `201 Created`: Returns the created product object.

*   `DELETE /product/{id}`
    *   **Description**: Deletes a product by its ID.
    *   **Path Variable**: `id` (integer): The ID of the product to delete.
    *   **Responses**:
        *   `200 OK`: Confirmation message.
        *   `404 Not Found`: If the product with the given ID does not exist.

*   `PUT /product/{id}`
    *   **Description**: Updates an existing product's details.
    *   **Path Variable**: `id` (integer): The ID of the product to update.
    *   **Request Body**: `Product` object (JSON) with fields to update.
    *   **Responses**:
        *   `200 OK`: Returns the updated product object.
        *   `404 Not Found`: If the product with the given ID does not exist.

### Purchase Endpoints (`/purchase`)

*   `POST /purchase`
    *   **Description**: Records a new purchase.
    *   **Request Body**: `Purchases` object (JSON)
        ```json
        {
          "userId": 1,
          "productId": 5,
          "quantity": 2,
          "purchaseDate": "2024-07-26T10:00:00.000+00:00" // Example date
        }
        ```
    *   **Responses**:
        *   `201 Created`: Returns the created purchase object.

### Database

This service uses a MySQL database. The schema initialization script can be found in `api_productos/init-db.sql`.

### How to Build and Run

1.  **Navigate to the service directory**:
    ```bash
    cd api_productos
    ```
2.  **Build with Maven**:
    ```bash
    ./mvnw clean package
    ```
3.  **Run using Docker Compose** (this will also start the required MySQL database):
    ```bash
    docker-compose up --build
    ```
    Alternatively, you can run the Spring Boot application directly using your IDE or via `./mvnw spring-boot:run` after setting up the database manually.

## Running the Entire Project

To run the entire project, you need to start both the `api_clientes` and `api_productos` services. Each service has its own Docker Compose configuration.

1.  **Start `api_clientes`**:
    *   Open a terminal, navigate to the `api_clientes` directory.
    *   Run `docker-compose up --build`.

2.  **Start `api_productos`**:
    *   Open another terminal, navigate to the `api_productos` directory.
    *   Run `docker-compose up --build`.

Make sure you have Docker and Docker Compose installed on your system. The services will be available on the ports configured within their respective `docker-compose.yaml` and Spring Boot properties (usually port 8080 by default, but this might differ if explicitly set).

## Prerequisites

*   **Java JDK 17** (or newer)
*   **Maven** (for building the projects outside of Docker)
*   **Docker** and **Docker Compose** (for running the services with their databases)

## Database Setup

Each service (`api_clientes` and `api_productos`) includes an `init-db.sql` file in its root directory. These scripts are used by the `docker-compose.yaml` configurations to initialize the respective MySQL databases when the services are first started with Docker Compose. 

If you intend to run the services outside of Docker (e.g., directly from your IDE or using `./mvnw spring-boot:run`), you will need to set up corresponding MySQL databases manually and ensure the connection properties in `application.properties` (found in `src/main/resources` for each service) are correctly configured to point to your database instances. The `init-db.sql` scripts can be used to create the necessary tables and initial data.
