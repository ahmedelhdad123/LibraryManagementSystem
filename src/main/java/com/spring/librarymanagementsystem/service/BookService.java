package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.BookRepo;
import com.spring.librarymanagementsystem.entity.Book;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    private final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookRepo.findAll();
        return ResponseEntity.ok(books);
    }

    public ResponseEntity<Book> findById(long id) {
        Optional<Book> book = bookRepo.findById(id);
        return book.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transient
    public ResponseEntity<Book> save(Book book) {
        Book savedBook = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Transactional
    public ResponseEntity<Book> update(long id, Book updatedBook) {
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isPresent()) {
            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setAuthor(updatedBook.getAuthor());
            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setDescription(updatedBook.getDescription());
            bookToUpdate.setIsbn(updatedBook.getIsbn());
            Book updated = bookRepo.save(bookToUpdate);
            return ResponseEntity.ok(updated);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ID: " + id);
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteById(long id) {
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
