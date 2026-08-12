\# TicketRush 🎟️



TicketRush is a full-stack movie ticket booking application built with \*\*Spring Boot\*\*, \*\*React\*\*, \*\*PostgreSQL\*\*, \*\*JWT authentication\*\*, and \*\*Docker\*\*.



The application provides a realistic cinema booking workflow where users can discover movies, view showtimes, select seats, and complete bookings.



\## 🚀 Features



\### Authentication

\- User signup and login

\- JWT-based authentication

\- BCrypt password hashing

\- Stateless Spring Security

\- Role-based authorization



\### Movies

\- Browse movies

\- View movie details

\- View showtimes for a movie



\### Theatres \& Shows

\- Theatre and screen management

\- Create movie shows

\- Prevent overlapping shows on the same screen

\- Automatically create show seats

\- Theatre-owner and admin authorization



\### Seat Selection

\- View seats for a show

\- Display available and booked seats

\- Select multiple seats

\- Calculate total price dynamically



\### Booking

\- Create ticket bookings

\- Reserve selected seats

\- Persist bookings in PostgreSQL

\- Booking confirmation

\- Display booking ID, seats, status and total amount



\### API Documentation

\- OpenAPI 3

\- Swagger UI

\- Controller-level API documentation

\- Endpoint descriptions and response documentation



\---



\## 🛠️ Tech Stack



\### Backend



\- Java

\- Spring Boot

\- Spring Security

\- Spring Data JPA

\- Hibernate

\- JWT

\- Bean Validation

\- Lombok

\- OpenAPI / Swagger

\- Maven



\### Frontend



\- React

\- Vite

\- React Router

\- Axios

\- JavaScript

\- CSS



\### Database \& Infrastructure



\- PostgreSQL

\- Docker

\- Docker Compose



\---



\## 🏗️ Architecture



```text

&#x20;                   ┌─────────────────────┐

&#x20;                   │      React UI       │

&#x20;                   │    Vite + Axios     │

&#x20;                   └──────────┬──────────┘

&#x20;                              │

&#x20;                              │ REST API

&#x20;                              ▼

&#x20;                   ┌─────────────────────┐

&#x20;                   │   Spring Boot API   │

&#x20;                   │ Controllers/Services│

&#x20;                   └──────────┬──────────┘

&#x20;                              │

&#x20;            ┌─────────────────┼─────────────────┐

&#x20;            │                 │                 │

&#x20;            ▼                 ▼                 ▼

&#x20;      Spring Security      JPA/Hibernate       JWT

&#x20;            │                 │

&#x20;            │                 ▼

&#x20;            │        ┌─────────────────┐

&#x20;            └───────►│   PostgreSQL    │

&#x20;                     └─────────────────┘

