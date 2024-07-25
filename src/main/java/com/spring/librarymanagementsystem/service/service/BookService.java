package com.spring.librarymanagementsystem.service.service;

import com.spring.librarymanagementsystem.dto.BookDto;
import com.spring.librarymanagementsystem.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks();
    Optional<BookDto> getBookById(long id);
    void saveBook(BookDto bookDto);
    void deleteBook(long id);
    void updateBook(BookDto bookDto);
}
