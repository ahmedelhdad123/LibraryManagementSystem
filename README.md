# LibraryManagementSystem

- LibraryManagementSystem is built using Java and Spring Boot, with security, scalability, and ease of maintenance. 
 The backend uses Spring Data JPA to interact with a MySQL database, making it easy to manage and store important entities such as 
 Book,patron,Borrowing

# Security
- The API is secured using basic authentication. To access the API, you will need to user and password  by authenticating

 # Technologies:
- Java 17 or above
- Spring Boot 3.0
- Maven
- MySQL
- Spring Data JPA
- Spring Security
- Aspects

# ER-Diagram
![image](https://github.com/ahmedelhdad123/LibraryManagementSystem/assets/91333530/4814d0f4-9080-4a81-975b-3710c12b6b53)

# API Controllers
## Book
- GET /books: Retrieve all books.
- GET /books/{id}: Retrieve a book by its ID.
- POST /books: Add a new book.
- PUT /books/{id}: Update an existing book.
- DELETE /books/{id}: Delete a book by its ID.
## Parons
- GET /patrons: Retrieve all patrons.
- GET /patrons/{id}: Retrieve a patron by their ID.
- POST /patrons: Add a new patron.
- PUT /patrons/{id}: Update an existing patron.
- DELETE /patrons/{id}: Delete a patron by their ID.
## Borrowing
- POST /borrow/{bookId}/patron/{patronId}: Borrow a book.
- PUT /return/{bookId}/patron/{patronId}: Return a borrowed book

# Thank You



