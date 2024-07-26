package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.BookRepo;
import com.spring.librarymanagementsystem.dao.BorrowingRecordRepo;
import com.spring.librarymanagementsystem.dao.PatronRepo;
import com.spring.librarymanagementsystem.dto.BorrowDto;
import com.spring.librarymanagementsystem.dto.mapstruct.BorrowMapper;
import com.spring.librarymanagementsystem.entity.Book;
import com.spring.librarymanagementsystem.entity.Borrow;
import com.spring.librarymanagementsystem.entity.Patron;
import com.spring.librarymanagementsystem.exception.ResourceNotFound;
import com.spring.librarymanagementsystem.service.service.BorrowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowingRecordRepo borrowingRecordRepo;
    private final BorrowMapper borrowMapper;
    private final PatronRepo patronRepo;
    private final BookRepo bookRepo;
    private static final Logger logger = LoggerFactory.getLogger(BorrowServiceImpl.class);

    @Override
    @CacheEvict(value = {"patrons", "books"}, allEntries = true)
    public void borrowBook(BorrowDto borrowDto) {
        Book book = validateAndGetBook(borrowDto.getBookId());
        Patron patron = validateAndGetPatron(borrowDto.getPatronId());
        book.decreaseQuantity();
        bookRepo.save(book);
        Borrow borrow = borrowMapper.toEntity(borrowDto);
        borrow.setBook(book);
        borrow.setPatron(patron);
        borrow.setBorrowDate(LocalDate.now());
        patron.addBorrow(borrow);
        borrowingRecordRepo.save(borrow);
        logger.info("Borrowed book with ID: {} by Patron with ID: {}", borrowDto.getBookId(), borrowDto.getPatronId());
    }

    private Book validateAndGetBook(Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book not found with ID: " + bookId));
        if (book.getQuantity() <= 0) {
            throw new ResourceNotFound("No copies available for book with ID: " + bookId);
        }
        return book;
    }

    private Patron validateAndGetPatron(Long patronId) {
        return patronRepo.findById(patronId)
                .orElseThrow(() -> new ResourceNotFound("Patron not found with ID: " + patronId));
    }

    @Override
    @CacheEvict(value = {"patrons","books"},allEntries = true)
    public void returnBook(long bookId, long patronId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book not found with ID: " + bookId));
        Patron patron = patronRepo.findById(patronId)
                .orElseThrow(() -> new ResourceNotFound("Patron not found with ID: " + patronId));
        Borrow borrow = borrowingRecordRepo.findByBookIdAndPatronId(bookId, patronId);
        if (borrow == null) {
            throw new ResourceNotFound("Borrow record not found");
        }
        borrow.setReturnDate(LocalDate.now());
        borrowingRecordRepo.save(borrow);
        book.increaseQuantity();
        patron.removeBorrow(borrow);
        bookRepo.save(book);
        logger.info("Book with ID: {} has been returned by Patron with ID: {}", bookId, patronId);
    }

}