[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-BR.md)

_Read this in other languages: [Português](README.pt-BR.md)_

---

# 🏦 Banking Transaction

A full-stack web application for scheduling financial transfers with **automatic dynamic fee calculation**.
This project was developed as part of a technical challenge for a job application, with the goal of demonstrating my software development skills, business logic implementation, and application of programming best practices.

---

## 📌 Project Overview

The application allows users to schedule financial transfers by providing the source account, destination account, transfer amount, and schedule a transfer date. The system automatically calculates the applicable fee based on the number of days between the scheduling date and the transfer date.

## 🛠️ Technologies

### Backend

- **Java 11** & **Spring Boot 2.7.18** (The assignment required Java 11)
- **Spring Data JPA / Hibernate**
- **H2 Database** (In-memory database required by the assignment)
- **Spring Validation**
- **JUnit 5 & Mockito**
- **OpenAPI / Swagger UI**

### Frontend

- **Vue.js 3**
- **Vite**
- **jQuery**
- **Bootstrap**

---

## 📐 Architecture

1. **Layered Architecture (Backend):**
   - The application follows a layered architectural pattern (Controller, Service, Repository, Model), ensuring low coupling and high cohesion.
   - Complex business rules, such as validating identical source and destination accounts and calculating fees based on date ranges, are isolated in the Service layer, enabling efficient unit testing and protecting the application from direct API calls that bypass business logic.
2. **Global Exception Handling:**
   - Uses @ControllerAdvice to handle validation exceptions (MethodArgumentNotValidException) and business exceptions (BusinessException), returning user-friendly error messages and standardized HTTP status codes (400 Bad Request).
3. **Modular Architecture (Frontend):**
   - The Vue.js frontend is built using Single File Components (SFCs) organized into modular directories (components/, services/), separating the API communication layer (transferService.js, using jQuery) from the visual components.
4. **Unit Testing:**
   - The test suite covers key fee calculation scenarios and business validations using JUnit and Mockito, ensuring software reliability.

---

## 🚀 Running the Project

### Prerequisites

- **Java Development Kit (JDK) 11**
- **Node.js**
- **Maven**

---

### 1️⃣ Backend (Spring Boot)

1. Navigate to the backend directory:

```bash
cd backend
```

2. Run the application using the Maven Wrapper:

- Linux / macOS (Terminal):

```bash
./mvnw spring-boot:run
```

- Windows (PowerShell):

```bash
.\mvnw spring-boot:run
```

- Windows (Command Prompt):

```bash
mvnw spring-boot:run
```

3. The API will be available at:

```
http://localhost:8080
```

4. Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### 2️⃣ Frontend (Vue.js)

1. Open a new terminal and navigate to the frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm run dev
```

4. Open your browser at:

```
http://localhost:5173
```

## 🧪 Running the Tests

From the backend directory, run:

```bash
./mvnw test
```

## 📄 License

This project was developed as part of a Full-Stack technical assessment.
