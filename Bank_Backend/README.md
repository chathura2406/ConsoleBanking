# 🏦 NeoBank - Secure & Robust Banking Backend System

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-green?style=for-the-badge&logo=spring-boot)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker)
![Security](https://img.shields.io/badge/Security-JWT-red?style=for-the-badge&logo=spring-security)
![Build](https://img.shields.io/badge/Build-Maven-purple?style=for-the-badge)

## 🚀 Overview

**NeoBank** is an enterprise-grade RESTful API designed for modern banking operations. Built with **Spring Boot 3** and **Java 21**, it focuses on security, transactional consistency, and scalability.

This project demonstrates a full **DevOps & QA lifecycle**, including Containerization (Docker), Automated Testing (JUnit/REST Assured), and API Documentation (Swagger).

---

## 🔥 Key Features

### 🛡️ Security & Authentication
* **JWT Authentication:** Secure stateless authentication using JSON Web Tokens.
* **BCrypt Hashing:** Passwords are encrypted before storage.
* **Role-Based Access:** Infrastructure ready for Admin/User roles.

### 💰 Core Banking Operations
* **Account Management:** Create Savings/Current accounts.
* **Transactional Consistency:** Uses `@Transactional` to ensure ACID properties (Rollback on failure).
* **Real-time Balance Updates:** Accurate deposit and withdrawal processing.

### ⚙️ Advanced Engineering
* **Idempotency Handling:** Prevents duplicate transactions using `Idempotency-Key` headers (Crucial for network reliability).
* **Global Exception Handling:** Centralized `@ControllerAdvice` for clean, standardized JSON error responses.
* **DTO Pattern:** Data Transfer Objects used to decouple the internal database model from the API.

### 🐳 DevOps & Deployment
* **Dockerized:** Fully containerized application using `Dockerfile`.
* **Cloud Ready:** Optimized for deployment on platforms like AWS or Render.

### ✅ Quality Assurance (QA)
* **Unit Testing:** Service layer tested using **JUnit 5** and **Mockito**.
* **Integration Testing:** End-to-End API testing using **REST Assured**.
* **API Documentation:** Integrated **Swagger UI** for interactive API testing.

---

## 🛠️ Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.3.0 |
| **Database** | MySQL |
| **ORM** | Spring Data JPA (Hibernate) |
| **Security** | Spring Security 6, JWT (jjwt) |
| **DevOps** | Docker, Maven, GitHub Actions (CI) |
| **Testing** | JUnit 5, Mockito, REST Assured |
| **Tools** | Lombok, Postman, Swagger UI |

---

## 🚀 Getting Started

### Prerequisites
* Java 21 SDK
* Maven
* MySQL Server
* Docker (Optional)

### 1️⃣ Clone the Repository
```bash
git clone [https://github.com/yourusername/neobank-backend.git](https://github.com/yourusername/neobank-backend.git)
cd neobank-backend