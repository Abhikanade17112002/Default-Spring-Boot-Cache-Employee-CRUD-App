# 🚀 Default Spring Boot Cache Employee CRUD App

A production-style RESTful Employee Management API built using Spring Boot with integrated caching support for improved performance and reduced database load.

This project demonstrates:

- CRUD Operations
- Spring Cache Abstraction
- Cacheable / CachePut / CacheEvict
- REST API Design
- Layered Architecture
- MySQL Integration
- Spring Data JPA

---

# 📌 Features

- Create Employee
- Get Employee By ID
- Get All Employees
- Update Employee
- Delete Employee
- ResponseEntity-based APIs
- MySQL Database Integration
- In-Memory Caching
- Automatic Cache Eviction
- Clean Layered Architecture

---

# 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Core Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | ORM & Database Operations |
| MySQL | Relational Database |
| Maven | Dependency Management |
| Postman | API Testing |

---

# 📂 Project Structure

```bash
src
 └── main
     ├── java
     │    └── com.example.employeecrud
     │          ├── controller
     │          ├── service
     │          ├── repository
     │          ├── entity
     │          └── config
     │
     └── resources
          ├── application.properties
          └── data.sql
```

---

# ⚡ Caching Implementation

This project uses Spring Cache to optimize repeated database calls.

## Example

```java
@Cacheable(value = "allEmployees", key = "'all'")
public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
}
```

### Benefits

- Faster API responses
- Reduced DB load
- Better scalability
- Improved performance

---

# 🔄 Cache Eviction Example

```java
@CacheEvict(value = "allEmployees", allEntries = true)
```

Whenever employee data changes, the cache is automatically cleared to avoid stale data.

---

# 🗄️ Employee Entity

```java
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
```

---

# 🔌 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/employees` | Create Employee |
| GET | `/employees` | Get All Employees |
| GET | `/employees/{id}` | Get Employee By ID |
| PUT | `/employees/{id}` | Update Employee |
| DELETE | `/employees/{id}` | Delete Employee |

---

# 📮 Sample Request

## Create Employee

```json
{
    "firstName": "Abhishek",
    "lastName": "Kanade",
    "email": "abhishek@example.com"
}
```

---

# ⚙️ Configuration

## application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.cache.type=simple
```

---

# ▶️ How To Run

## 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/Default-Spring-Boot-Cache-Employee-CRUD-App.git
```

## 2️⃣ Navigate To Project

```bash
cd Default-Spring-Boot-Cache-Employee-CRUD-App
```

## 3️⃣ Configure Database

Create a MySQL database:

```sql
CREATE DATABASE employeedb;
```

Update `application.properties` with your credentials.

---

## 4️⃣ Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```bash
http://localhost:8080
```

---

# 🧪 Testing APIs

You can test APIs using:

- Postman
- Swagger
- cURL

---

# 📈 Future Improvements

- Redis Cache Integration
- Pagination & Sorting
- JWT Authentication
- Docker Support
- Global Exception Handling
- Unit & Integration Testing
- Swagger/OpenAPI Documentation

---

# 👨‍💻 Author

## Abhishek Kanade

MERN Stack & Java Full Stack Developer  
Passionate about Backend Development, System Design, and Scalable Applications.

---

# ⭐ Support

If you like this project, give it a ⭐ on GitHub and support the repository.
