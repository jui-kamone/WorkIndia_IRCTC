# Application Documentation

Welcome to the IRCTC application documentation! It is a Spring Boot application designed to manage train schedules and ticket bookings. This documentation provides detailed information on how to use the application, including installation instructions, API endpoints, and more.

## Overview

The Train Booking System allows users to book train tickets and manage bookings. It provides APIs for creating users, authenticating users, adding trains, and booking tickets.

## Features
### User Features
- User Registration: Create a new account.
- Login: Authenticate and access user-specific features.
- Train Search: Search trains between two stations.
- Seat Availability: View real-time seat availability between stations.
- Book Seat: Book available seats with simultaneous booking conflict handling.
- Booking Details: Fetch specific booking information.

### Admin Features
- Add Trains: Add new trains with source and destination details.
- Update Train Information: Modify seat availability or other train details.
- Protected Admin Endpoints: Accessed only using a secure API key.

### Security
- JWT Authentication: Secure login with JSON Web Tokens (JWT).
- Role-Based Access Control: Separate privileges for Admin and Users.
- Admin API Key Middleware: Protect sensitive admin endpoints with an API key.
## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT Authentication
- Maven

## Prerequisites 
Before installing the AnakinSDE application, ensure you have the following prerequisites installed:

- Java 17+
- Maven
- MySQL Server
- IDE (IntelliJ IDEA / Eclipse)

## Steps to Setup

To run the application locally, follow these steps:

1. Clone the repository:

   https://github.com/jui-kamone/WorkIndia_IRCTC

2. Navigate to the project directory:

   cd SDE-IRCTC

3. Update the application.properties file with your MySQL database configuration.

```bash
 spring.datasource.url = jdbc:mysql://localhost:3306/your_database_name
 spring.datasource.username = your_sql_username
 spring.datasource.password = your_sql_password
```

4. Build the application:
```bash
 mvn clean install
```

5. Run the application


6. Access the application at http://localhost:8889.

## Tech Stack

- Java
- Springboot
- MySql

## API Endpoints

### Authentication

- POST /auth/login: Login to the system and obtain a JWT token.
- POST /auth/createUser: Create a new user account.
- GET /auth/users: See all registered users


## Mandatory Requirements

1. Protected the admin API with a hardcoded API Key so that no user can access the APIs.
2. For the SQL Queries I have used the "@Entity" annotation of the Springboot Framework to perform the SQL Queries.

## Tables
### Users

- id: Unique identifier
- username: User's login name
- password: Encrypted password
- role: User role (ADMIN or USER)

### Trains

- id: Train ID
- name: Train name
- source: Starting station
- destination: Destination station
- totalSeats: Total number of seats in the train
- availableSeats: Available seats for booking

### Bookings

- id: Booking ID.
- userId: ID of the user who made the booking.
- trainId: Train ID.
-seatCount: Number of seats booked


### Database Tables

#### User Table

| Column Name  | Data Type        | Constraints            | Description                     |
|--------------|------------------|------------------------|---------------------------------|
| `id`         | INT             | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for the user.|
| `username`   | VARCHAR(255)    | NOT NULL, UNIQUE       | Username of the user.          |
| `password`   | VARCHAR(255)    | NOT NULL               | Encrypted password of the user.|
| `role`       | ENUM(`ADMIN`, `USER`) | NOT NULL         | Role of the user (Admin or User).|

---

#### Booking Table

| Column Name     | Data Type        | Constraints                   | Description                                    |
|------------------|------------------|-------------------------------|------------------------------------------------|
| `id`            | INT             | PRIMARY KEY, AUTO_INCREMENT   | Unique identifier for the booking.            |
| `user_id`       | INT             | FOREIGN KEY (`users.id`)      | User who made the booking.                    |
| `train_id`      | INT             | FOREIGN KEY (`trains.id`)     | Train for which the booking is made.          |
| `source`        | VARCHAR(255)    | NOT NULL                      | Source station of the journey.                |
| `destination`   | VARCHAR(255)    | NOT NULL                      | Destination station of the journey.           |
| `seats_booked`  | INT             | NOT NULL                      | Number of seats booked in the booking.        |
| `booking_time`  | TIMESTAMP       | DEFAULT CURRENT_TIMESTAMP     | Time when the booking was made.               |

---

#### Train Table

| Column Name         | Data Type        | Constraints                | Description                                     |
|----------------------|------------------|----------------------------|------------------------------------------------|
| `id`                | INT             | PRIMARY KEY, AUTO_INCREMENT| Unique identifier for the train.               |
| `train_name`        | VARCHAR(255)    | NOT NULL                   | Name of the train.                             |
| `source`            | VARCHAR(255)    | NOT NULL                   | Starting station of the train.                 |
| `source_time`       | TIME            | NOT NULL                   | Departure time from the source station.        |
| `destination`       | VARCHAR(255)    | NOT NULL                   | Ending station of the train.                   |
| `destination_time`  | TIME            | NOT NULL                   | Arrival time at the destination station.       |
| `seats`             | INT             | NOT NULL                   | Total number of seats available on the train.  |
| `available_seats`   | INT             | NOT NULL                   | Seats currently available for booking.         |


