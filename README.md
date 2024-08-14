# Food Delivery System API

![Alt text](./background-img.jpg)
[![wakatime](https://wakatime.com/badge/user/2009fdf2-8d6f-4d87-9435-7ec784a9054f/project/19fd3401-4956-4af5-837c-f15740a06187.svg)](https://wakatime.com/badge/user/2009fdf2-8d6f-4d87-9435-7ec784a9054f/project/19fd3401-4956-4af5-837c-f15740a06187)

## 📖 Table of Contents

- [🚀 Overview](#-overview)
- [✨ Features](#-features)
- [🛠 Technologies \& Tools Used](#-technologies--tools-used)
- [🏁 Getting Started](#-getting-started)
- [📚 API Documentation](#-api-documentation)
- [💾 Database Schema (ERD)](#-database-schema-erd)

## 🚀 Overview

A comprehensive Food Delivery Service application built with Spring Boot and MySQL, designed to connect customers, restaurants, and couriers. The system offers a rich set of features including:

- User authentication and authorization for customers, admins, and couriers
- Restaurant browsing, searching, and filtering
- Dynamic menu management
- Order placement and tracking
- Flexible promotion system
- Region-based delivery fee calculation
- Post-delivery rating and feedback

The application utilizes JWT for secure authentication, implements complex business logic for order management and promotions, and employs scheduled jobs for performance optimization. This robust backend architecture is designed to handle the intricate workflows and data relationships of a modern food delivery service, ensuring scalability and maintainability.

## ✨ Features

Head to [Features](./FEATURES.md) to see the full features of the system.

## 🛠 Technologies & Tools Used

- Java 21
- Spring Boot 3.3.1
- Spring Data JPA (Hibernate ORM)
- Spring Security
- MySQL (8.0.3+)
- Flyway for database migrations
- JWT for authentication and authorization
- Lombok for reducing boilerplate code
- MapStruct for object mapping
- OpenAPI (Swagger) for API documentation
- Maven for dependency management and build - automation
- Validation
- Spring Boot Actuator for application monitoring and management
- Spring Boot DevTools for development
- JUnit and Spring Boot Test for testing
- Jira for managing the project tasks

## 🏁 Getting Started

1. Clone the repository
2. Navigate to the project directory via `cd ./food-delivery-system`
3. Build the project `\.mvnw.cmd clean install`
4. Run the application `.\mvnw.cmd spring-boot:run`
5. Access the application at `http://localhost:8080`

## 📚 API Documentation

API documentation is available via Swagger UI. After running the application, visit `http://localhost:8080/swagger-ui.html` to explore the API endpoints.

Also a Postman collection can be accessed from [here](./Food%20Delivery.postman_collection.json) here. All you need to do is to import the collection.

## 💾 Database Schema (ERD)

Entity Relationship Diagram can be seen from [here](./Food%20Delivery%20System%20ERD.png) as an image or view it on [Lucidchart](https://lucid.app/lucidchart/3eec23cd-6dba-4e48-a20b-729ceb320002/edit?viewport_loc=-7403%2C1662%2C8000%2C3700%2C0_0&invitationId=inv_d259579f-0d97-46f0-b80c-753d5e68a15a) if you have an account.
