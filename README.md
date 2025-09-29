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
