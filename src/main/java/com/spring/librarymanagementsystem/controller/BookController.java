package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.entity.Book;
import com.spring.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class BookController {


    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public ResponseEntity<List <Book>> getBooks() {
        return  bookService.findAll();
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @PostMapping("books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
    }

    @PutMapping("books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable long id, @RequestBody Book book) {
        bookService.update(id,book);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

}
