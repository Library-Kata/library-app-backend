
# Library App Backend

Library App Backend is a Spring Boot-based RESTful API designed to manage library operations. The application provides functionalities like user management, book borrowing, and returning.

## **Features**
- User authentication and role-based authorization.
- Manage books (add, list, update, delete).
- Borrow and return books.
- View borrowing history.
- Integrated Swagger for API documentation.
- Docker support for containerized deployment.
- Predefined superadmin user for testing.

---

## **Setup Instructions**

### **Prerequisites**
1. Java 21 (JDK 21).
2. Maven 3.8+.
3. Docker (if running with containers).

### **Commands to Initialize the Project**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Library-Kata/library-app-backend.git
   cd library-app-backend
   ```

2. **Build the project:**
   ```bash
   mvn clean package
   ```

3. **Run the application:**
   ```bash
   java -jar target/library-app-backend-0.0.1.jar
   ```

---

## **Docker Integration**

### **Build and Run with Docker**

1. **Run the application with Docker Compose:**
   ```bash
   ./start.sh
   ```

2. **Alternatively, run these commands manually:**
   ```bash
    mvn clean package
    docker-compose build
    docker-compose up
   ```

3. Access the application at:
    - **API Base URL:** `http://localhost:8080`
    - **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## **Swagger API Documentation**

Swagger is integrated for easy exploration of API endpoints.

1. After starting the application, open the following URL in your browser:
    - [Swagger UI](http://localhost:8080/swagger-ui.html)

2. Use Swagger to test endpoints or explore API documentation.

---

## **Superadmin Credentials**

For testing purposes, a predefined superadmin user is included:

- **Username:** `admin`
- **Password:** `admin123`

---

## **Application Functionalities**

### **User Management**
- Add new users (admin-only).
- View all users (admin-only).
- Delete users (admin-only).

### **Book Management**
- Add, update, or delete books (admin-only).
- List all available books.

### **Borrowing Operations**
- Borrow a book (user-only).
- Return a book (user-only).
- View borrowing history (user-only).

---

## **Development Notes**

### **Run the application in development mode:**
```bash
mvn spring-boot:run
```

### **Test the application:**
```bash
mvn test
```

### **Preloaded Data**
The application starts with some test data for users and books in the H2 database.

- **Access H2 Console**:  
  URL: `http://localhost:8080/h2-console`  
  JDBC URL: `jdbc:h2:mem:testdb`  
  Username: `sa`  
  Password: *(leave blank)*

---

## **Contributing**

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push the branch:
   ```bash
   git push origin feature-name
   ```
5. Create a pull request.

