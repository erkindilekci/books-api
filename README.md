# Books API

## Overview

The Books API is a Spring Boot application that provides RESTful endpoints for managing authors and books. It uses Spring Data JPA for data persistence and supports pagination for retrieving lists of authors and books.

## Requirements

- Java 17
- Maven
- PostgreSQL (runtime)
- H2 Database (test)

## Dependencies

- **Spring Boot Starter Web**: For building web applications, including RESTful applications using Spring MVC.
- **Spring Boot Starter Data JPA**: For integrating Spring Data JPA with the application.
- **H2 Database**: In-memory database for testing purposes.
- **PostgreSQL**: Database for runtime.
- **Lombok**: For reducing boilerplate code.
- **ModelMapper**: For object mapping.
- **Spring Boot Starter Test**: For testing Spring Boot applications.

## Endpoints

### Author Endpoints

- **Create Author**
    - **URL**: `/authors`
    - **Method**: `POST`
    - **Request Body**: `AuthorDto`
    - **Response**: `AuthorDto` (Created Author)

- **Get All Authors**
    - **URL**: `/authors`
    - **Method**: `GET`
    - **Response**: `Page<AuthorDto>`

- **Get Author by ID**
    - **URL**: `/authors/{id}`
    - **Method**: `GET`
    - **Response**: `AuthorDto` (if found) or `404 Not Found`

- **Update Author**
    - **URL**: `/authors/{id}`
    - **Method**: `PUT`
    - **Request Body**: `AuthorDto`
    - **Response**: `AuthorDto` (Updated Author) or `404 Not Found`

- **Delete Author**
    - **URL**: `/authors/{id}`
    - **Method**: `DELETE`
    - **Response**: `204 No Content` or `404 Not Found`

### Book Endpoints

- **Create or Update Book**
    - **URL**: `/books/{isbn}`
    - **Method**: `PUT`
    - **Request Body**: `BookDto`
    - **Response**: `BookDto` (Created or Updated Book)

- **Get All Books**
    - **URL**: `/books`
    - **Method**: `GET`
    - **Response**: `Page<BookDto>`

- **Get Book by ISBN**
    - **URL**: `/books/{isbn}`
    - **Method**: `GET`
    - **Response**: `BookDto` (if found) or `404 Not Found`

- **Delete Book**
    - **URL**: `/books/{isbn}`
    - **Method**: `DELETE`
    - **Response**: `204 No Content` or `404 Not Found`

## Running the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Run `mvn spring-boot:run` to start the application.

## Testing

The application uses the H2 in-memory database for testing. To run the tests, use the command:

```sh
mvn test
