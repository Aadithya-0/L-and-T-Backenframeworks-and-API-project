# Employee Management System - JDBC Project

## Phase 2: Data & Logic Layer Implementation (Person 2)

This project implements a **Command-Line Employee Management System** using **Java, Spring Boot, JDBC, and MySQL**.

---

## 📋 Project Requirements Completed (Person 2)

### ✅ Step 1: Model Class (`Employee.java`)
- POJO with fields: `id`, `firstName`, `lastName`, `email`
- Constructor, getters, setters, `toString()`, `equals()`, and `hashCode()` implemented

### ✅ Step 2: Service Class (`EmployeeServiceImpl.java`)
- Contains ALL JDBC logic
- Uses `DBConnection.getConnection()` for database connectivity
- Implements `EmployeeService` interface

### ✅ Step 3: Create Operation
- **Method**: `createEmployee(Employee emp)`
- Uses `PreparedStatement` with parameterized query
- SQL: `INSERT INTO employees (first_name, last_name, email) VALUES (?, ?, ?)`
- Prevents SQL injection attacks
- Returns generated employee ID

### ✅ Step 4: Read Operation
- **Method**: `getAllEmployees()`
- Uses `Statement` to execute `SELECT * FROM employees`
- Iterates through `ResultSet`
- Maps each database row to `Employee` object
- Returns `List<Employee>`

---

## ⚠️ Dependencies - Required from Other Team Members

### Person 1 (Phase 1: Database Setup & Connection) - **NOT COMPLETED YET**

**Must implement:**

1. **SQL Script** (`src/main/resources/sql/schema.sql`)
   ```sql
   CREATE DATABASE IF NOT EXISTS EmployeeDB;
   
   CREATE TABLE IF NOT EXISTS employees (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       first_name VARCHAR(100) NOT NULL,
       last_name VARCHAR(100) NOT NULL,
       email VARCHAR(255) NOT NULL UNIQUE
   );
   ```

2. **DBConnection.java** (`src/main/java/.../util/DBConnection.java`)
   - Implement `getConnection()` method with DriverManager
   - Add connection test main() method
   - Currently has placeholder implementation

3. **application.properties**
   - Uncomment and configure database connection properties
   - Currently commented out (placeholders)

**Status:** ⚠️ Placeholders exist - Person 1 needs to implement these before the application can run with a real database.

---

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **MySQL 8.x** (to be configured by Person 1)
- **JDBC** (Java Database Connectivity)
- **Maven** (Build tool)

---

## 📁 Project Structure

```
employee-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lntproject/employee_management_system/
│   │   │       ├── EmployeeManagementSystemApplication.java
│   │   │       ├── model/
│   │   │       │   └── Employee.java                    ✅ Person 2
│   │   │       ├── service/
│   │   │       │   ├── EmployeeService.java (interface)
│   │   │       │   └── impl/
│   │   │       │       └── EmployeeServiceImpl.java     ✅ Person 2
│   │   │       ├── util/
│   │   │       │   └── DBConnection.java                ⚠️ Person 1 (placeholder)
│   │   │       ├── cli/
│   │   │       │   └── EmployeeCliRunner.java
│   │   │       └── config/
│   │   │           └── CliConfiguration.java
│   │   └── resources/
│   │       ├── application.properties                   ⚠️ Person 1 (to configure)
│   │       └── sql/
│   │           └── schema.sql                           ⚠️ Person 1 (placeholder)
│   └── test/
│       └── java/
│           └── com/lntproject/employee_management_system/
│               └── EmployeeManagementSystemApplicationTests.java
└── pom.xml
```

---

## 🚀 Setup Instructions (After Person 1 Completes Phase 1)

### 1. Prerequisites
- **Java 21** installed
- **MySQL 8.x** installed and running
- **Maven** installed

### 2. Person 1 Must Complete First:
- Create MySQL database using their SQL script
- Implement `DBConnection.getConnection()` method
- Configure `application.properties` with database credentials
- Test database connection

### 3. Build the Project

```powershell
mvnw clean install
```

### 4. Run the Application

```powershell
mvnw spring-boot:run
```

---

## 💻 Using the CLI (After Setup Complete)

Once the application starts, you'll see:

```
========================================
      Employee Management System CLI
========================================

1. Add Employee
2. View Employees
3. Update Employee
4. Delete Employee
5. Exit

Choose an option:
```

### Example Usage:

**Add an Employee (Step 3 - Person 2's Implementation):**
```
Choose an option: 1
First name: John
Last name: Doe
Email: john.doe@example.com
Employee created with id: 1
```

**View All Employees (Step 4 - Person 2's Implementation):**
```
Choose an option: 2
Registered employees:
- [id=1, John Doe, email=john.doe@example.com]
```

---

## 🔍 Key JDBC Concepts Demonstrated

### 1. **DBConnection.getConnection()**
```java
public static Connection getConnection() throws SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
}
```
- Establishes connection to MySQL
- Loads JDBC driver
- Returns `Connection` object

### 2. **PreparedStatement (Secure Insert)**
```java
String sql = "INSERT INTO employees (first_name, last_name, email) VALUES (?, ?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
pstmt.setString(1, employee.getFirstName());
pstmt.setString(2, employee.getLastName());
pstmt.setString(3, employee.getEmail());
pstmt.executeUpdate();
```
- Prevents SQL injection
- Parameters are safely bound using `?` placeholders
- Returns generated keys (auto-increment ID)

### 3. **Statement & ResultSet (Select & Map)**
```java
String sql = "SELECT * FROM employees";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

while (rs.next()) {
    Employee employee = new Employee();
    employee.setId(rs.getLong("id"));
    employee.setFirstName(rs.getString("first_name"));
    employee.setLastName(rs.getString("last_name"));
    employee.setEmail(rs.getString("email"));
    employees.add(employee);
}
```
- Executes SELECT query
- Iterates through result rows
- Maps database columns to Java object fields

### 4. **Resource Management (try-with-resources)**
```java
try (Connection conn = DBConnection.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // ...
} catch (SQLException e) {
    throw new RuntimeException("Error: " + e.getMessage(), e);
}
```
- Automatically closes database resources
- Prevents resource leaks
- Proper exception handling

---

## 🧪 Testing

Run tests:
```powershell
mvnw test
```

The tests use an in-memory implementation (no database required).

---

## 📊 Database Schema

```sql
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
```

---

## 🎯 Learning Objectives Achieved

- ✅ JDBC connectivity using `DriverManager`
- ✅ CRUD operations (Create, Read, Update, Delete)
- ✅ `PreparedStatement` for secure SQL queries
- ✅ `Statement` and `ResultSet` for data retrieval
- ✅ Exception handling with `SQLException`
- ✅ Resource management with try-with-resources
- ✅ Object-Relational mapping (ResultSet → Java Object)
- ✅ Command-line interface design

---

## 🐛 Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"
- Update password in `application.properties` or `DBConnection.java`

### Error: "Unknown database 'EmployeeDB'"
- Run: `CREATE DATABASE EmployeeDB;` in MySQL

### Error: "Communications link failure"
- Make sure MySQL server is running
- Check the port (default: 3306)

### Error: "The driver has not received any packets from the server"
- Update `application.properties`:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/EmployeeDB?allowPublicKeyRetrieval=true&useSSL=false
  ```

---

## 👥 Team Members

- **Person 2**: Data Model & CRUD (Create/Read) - **YOU**
  - ✅ `Employee.java` - POJO model
  - ✅ `EmployeeServiceImpl.java` - JDBC service
  - ✅ `createEmployee()` - INSERT with PreparedStatement
  - ✅ `getAllEmployees()` - SELECT with Statement/ResultSet

---

## 📝 License

This is an educational project for L&T Backend Frameworks and API training.

---

## 📧 Support

For issues or questions, contact your project team or instructor.
