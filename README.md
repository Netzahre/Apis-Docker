# ClienteGraficoApis

ClienteGraficoApis is a JavaFX desktop application that serves as a client for an e-commerce API. It allows users to browse products, make purchases, and perform administrative tasks if authorized.

## Features

-   **User Authentication:** Secure login for users.
-   **Product Browsing:** View a list of available products with details like name, description, and price.
-   **Product Filtering:** Filter products by name and price range to easily find desired items.
-   **Product Purchasing:** Add products to a cart and complete purchases. Purchase information is logged.
-   **Administrative Functions (for authorized users):**
    -   **Create Products:** Add new products to the catalog.
    -   **Modify Products:** Edit existing product details.
    -   **Delete Products:** Remove products from the catalog.
-   **Purchase Logging:** Purchases made by users are recorded via FTP for administrative tracking.

## Project Structure

The project is built using JavaFX for the graphical user interface and follows a Model-View-Controller (MVC) pattern. Key components include:

-   **`pom.xml`**: Manages project dependencies (like JavaFX, Retrofit, Gson) and the build process using Maven.
-   **`src/main/java/org/example/clientegraficoapis/`**: Contains the core Java source code.
    -   **`Main.java`**: The main entry point for the JavaFX application.
    -   **`controller/`**: Contains JavaFX controller classes that handle user interactions and update the view (e.g., `logInController`, `storeController`, `productFormController`).
    -   **`model/`**: Defines the data structures of the application (e.g., `Product`, `User`, `Purchases`).
    -   **`service/`**: Includes services for handling business logic, such as API communication (e.g., `ProductApiService`, `UserApiService`, `RetrofitClient`) and FTP operations (`FTPService`).
    -   **`session/`**: Manages user session information.
-   **`src/main/resources/`**: Contains resources like FXML files for UI layout, CSS for styling, and images.

## Building and Running the Project

To build and run ClienteGraficoApis, you'll need the following:

-   **Java Development Kit (JDK):** Version 22 or later (as specified in `pom.xml`).
-   **Apache Maven:** To manage dependencies and build the project.

### Steps:

1.  **Clone the repository (if you haven't already):**
    ```bash
    git clone <repository-url>
    cd ClienteGraficoApis
    ```

2.  **Build the project:**
    Open a terminal in the project's root directory and run the following Maven command:
    ```bash
    mvn clean install
    ```
    This command will compile the source code, download dependencies, and package the application. You can also use `mvn package` if you only need to create the JAR file without installing it to your local Maven repository.

3.  **Run the application:**
    After a successful build, run the application using the JavaFX Maven plugin:
    ```bash
    mvn clean javafx:run
    ```
    This will start the application, and you should see the login window.

## Key Technologies and Libraries

-   **JavaFX:** For creating the rich client graphical user interface.
-   **Apache Maven:** For project build automation and dependency management.
-   **Retrofit:** A type-safe HTTP client for Java and Android, used for communication with the backend API.
-   **Gson:** A Java library to convert Java Objects into their JSON representation and vice versa.
-   **Apache Commons Net:** Used for network utilities, likely for the FTP functionality (`FTPService`) to log purchases.
