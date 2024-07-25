package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.dto.BookDto;
import com.spring.librarymanagementsystem.service.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtoList = bookService.getAllBooks();
        return ResponseEntity.ok(bookDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid BookDto bookDto) {
        bookService.saveBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable long id, @RequestBody @Valid BookDto bookDto) {
        bookDto.setId(id);
        bookService.updateBook(bookDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}