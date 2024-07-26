package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.BookRepo;
import com.spring.librarymanagementsystem.dto.BookDto;
import com.spring.librarymanagementsystem.dto.mapstruct.BookMapper;
import com.spring.librarymanagementsystem.entity.Book;
import com.spring.librarymanagementsystem.exception.ResourceNotFound;
import com.spring.librarymanagementsystem.service.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    @Cacheable(value = "books", key = "#root.methodName")
    public List<BookDto> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepo.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public Optional<BookDto> getBookById(long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepo.findById(id)
                .map(bookMapper::toDto)
                .or(() -> {
                    throw new ResourceNotFound("Book not found with ID: " + id);
                });
    }

    @Override
    @CacheEvict(value = "books", allEntries = true)
    public void saveBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        logger.info("Saving new book: {}", book);
        bookRepo.save(book);
    }

    @Override
    @CacheEvict(value = "books", key = "#bookDto.id",allEntries = true)
    public void updateBook(BookDto bookDto) {
        Book existingBook = bookRepo.findById(bookDto.getId())
                .orElseThrow(() -> new ResourceNotFound("Book not found with ID " + bookDto.getId()));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPublicationYear(bookDto.getPublicationYear());
        existingBook.setDescription(bookDto.getDescription());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setQuantity(bookDto.getQuantity());

        logger.info("Updating book with ID: {}", bookDto.getId());
        bookRepo.save(existingBook);
    }


    @Override
    @CacheEvict(value = "books", key = "#id",allEntries = true)
    public void deleteBook(long id) {
        logger.info("Deleting book with ID: {}", id);
        if (!bookRepo.existsById(id)) {
            throw new ResourceNotFound("Book not found with ID " + id);
        }
        bookRepo.deleteById(id);
    }
}