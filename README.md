# 🍽️ Hostel Mess Management System

## 📌 Overview
The **Hostel Mess Management System** is a web-based application built using **Java Spring Boot** and **MySQL**.  
It helps reduce **food wastage in hostels** by allowing students to **pre-book or skip meals** in advance.  

Mess staff can then prepare meals based on actual requirements, saving food, time, and cost.  
Admins can monitor statistics, manage students, and analyze food usage trends.

---

## 🎯 Features

### 👩‍🎓 Student
- Register/Login with hostel ID
- View upcoming meals (Breakfast, Lunch, Dinner)
- Pre-book or skip meals before a cutoff time
- View booking history

### 👨‍🍳 Staff
- Dashboard showing **meal-wise headcount** (e.g., 80 booked, 30 skipped)
- Generate **daily/weekly reports**

### 🛠️ Admin
- Add/remove students
- Set **cutoff time** for bookings
- View **statistics on food saved**

---

## 🛠️ Tech Stack
- **Backend**: Java Spring Boot  
- **Database**: MySQL (JPA/Hibernate)  
- **Authentication**: Spring Security (role-based access: STUDENT, STAFF, ADMIN)  
- **API Documentation**: Swagger/OpenAPI  
- **Build Tool**: Maven  

---

## 📂 Project Structure

Hostel-Mess-Management/
│── src/main/java/com/hostelmess
│ │── controller
│ │ ├── StudentController.java
│ │ ├── StaffController.java
│ │ └── AdminController.java
│ │
│ │── entity
│ │ ├── User.java
│ │ ├── Meal.java
│ │ └── Booking.java
│ │
│ │── repository
│ │ ├── UserRepository.java
│ │ ├── MealRepository.java
│ │ └── BookingRepository.java
│ │
│ │── service
│ │ ├── UserService.java
│ │ ├── MealService.java
│ │ └── BookingService.java
│ │
│ │── dto
│ │ ├── UserDTO.java
│ │ ├── MealDTO.java
│ │ └── BookingDTO.java
│ │
│ │── config
│ │ ├── SecurityConfig.java
│ │ └── SwaggerConfig.java
│ │
│ └── HostelMessApplication.java
│
│── src/main/resources
│ ├── application.properties
│ ├── data.sql (optional seed data)
│ └── schema.sql (optional schema file)
│
│── pom.xml
│── README.md


---

## 📊 Database Schema

### `users`
| Column     | Type       | Description              |
|------------|-----------|--------------------------|
| id         | INT (PK)  | Unique user ID           |
| username   | VARCHAR   | Student/Staff/Admin name |
| password   | VARCHAR   | Hashed password          |
| role       | ENUM      | STUDENT / STAFF / ADMIN  |

### `meals`
| Column     | Type       | Description              |
|------------|-----------|--------------------------|
| id         | INT (PK)  | Meal ID                  |
| meal_type  | ENUM      | BREAKFAST / LUNCH / DINNER |
| meal_date  | DATE      | Date of meal             |
| cutoff_time| DATETIME  | Booking cutoff time      |

### `bookings`
| Column     | Type       | Description              |
|------------|-----------|--------------------------|
| id         | INT (PK)  | Booking ID               |
| user_id    | INT (FK)  | References users table   |
| meal_id    | INT (FK)  | References meals table   |
| status     | ENUM      | BOOKED / SKIPPED         |
| created_at | TIMESTAMP | Time of booking action   |

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository
```bash
git clone https://github.com/your-username/Hostel-Mess-Management.git
cd Hostel-Mess-Management

2️⃣ Configure Database

Create a MySQL database:

CREATE DATABASE hostel_mess_db;


Update src/main/resources/application.properties:

# ===============================
# Database Configuration
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/hostel_mess_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===============================
# JPA / Hibernate
# ===============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# ===============================
# Swagger
# ===============================
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

# ===============================
# Security (default demo user)
# ===============================
spring.security.user.name=admin
spring.security.user.password=admin123
spring.security.user.roles=ADMIN

3️⃣ Build & Run
mvn spring-boot:run

4️⃣ Access Application

Swagger UI: http://localhost:8080/swagger-ui.html

API Base URL: http://localhost:8080/api

📌 Example API Endpoints
Student

POST /api/student/book → Book/skip a meal

GET /api/student/history → View booking history

Staff

GET /api/staff/dashboard → View meal headcount

GET /api/staff/reports → Daily/weekly reports

Admin

POST /api/admin/addUser → Add new student

POST /api/admin/setCutoff → Set cutoff time

📈 Future Enhancements

📱 Mobile app integration (Android/iOS)

🔐 JWT authentication instead of basic login

📊 Detailed analytics dashboards

📷 QR code scanning at mess entry

🔔 Push notifications/reminders for students

💳 Pay-per-meal system with online payments

👨‍💻 Author

Developed by Swastik Singh Chauhan
📧 Contact: swastikchauhan93@gmail.com


---

