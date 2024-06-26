# ContactsApp

## Overview

ContactsApp is a simple full-stack application for managing contacts. It includes a backend service built with Java, Quarkus, and PostgreSQL.

## Features

- List all contacts with optional filtering
- View contact details
- Create new contacts
- Update existing contacts
- Delete contacts
- Simple user authentication

## Technologies Used

### Backend

- **Language:** Java 17
- **Framework:** Quarkus
- **Database:** PostgreSQL
- **ORM:** Hibernate ORM with Panache
- **Security:** Quarkus Elytron Security
- **Build Tool:** Gradle

## Setup and Running the Application

### Prerequisites

- Java 17
- PostgreSQL

### Backend

1. **Clone the repository:**

   ```bash
   git clone https://github.com/PavelDimitrov954/contact-app-backend.git
   cd ContactsApp

   
2. **Set up the database:**

Ensure PostgreSQL is running and create a database named contacts_db.

3. **Configure database credentials:**

Update the application.properties file with your database credentials:

properties

quarkus.datasource.db-kind=postgresql

quarkus.datasource.username=myuser

quarkus.datasource.password=pass

quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/contacts_db

quarkus.hibernate-orm.database.generation=update

4. **Build and run the backend:**

bash
./gradlew quarkusDev
