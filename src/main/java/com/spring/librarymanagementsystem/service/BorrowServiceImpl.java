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
    public void borrowBook(BorrowDto borrowDto) {
        Book book = bookRepo.findById(borrowDto.getBookId())
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", borrowDto.getBookId());
                    return new ResourceNotFound("Book not found with ID: " + borrowDto.getBookId());
                });

        if (!book.canBeBorrowed()) {
            logger.error("Book with ID: {} cannot be borrowed", borrowDto.getBookId());
            throw new ResourceNotFound("Book cannot be borrowed");
        }

        Patron patron = patronRepo.findById(borrowDto.getPatronId())
                .orElseThrow(() -> {
                    logger.error("Patron not found with ID: {}", borrowDto.getPatronId());
                    return new ResourceNotFound("Patron not found with ID: " + borrowDto.getPatronId());
                });

        book.markAsBorrowed();
        bookRepo.save(book);

        Borrow borrow = borrowMapper.toEntity(borrowDto);
        borrow.setBook(book);
        borrow.setPatron(patron);
        borrow.setBorrowDate(LocalDate.now());

        borrowingRecordRepo.save(borrow);
        logger.info("Borrowed book: {}", borrowDto);
    }

    @Override
    public void returnBook(long bookId, long patronId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", bookId);
                    return new ResourceNotFound("Book not found with ID: " + bookId);
                });

        Patron patron = patronRepo.findById(patronId)
                .orElseThrow(() -> {
                    logger.error("Patron not found with ID: {}", patronId);
                    return new ResourceNotFound("Patron not found with ID: " + patronId);
                });

        Borrow borrow = borrowingRecordRepo.findByBookIdAndPatronId(bookId, patronId);

        borrow.setReturnDate(LocalDate.now());
        borrowingRecordRepo.save(borrow);

        book.markAsReturned();
        bookRepo.save(book);

        logger.info("Book with ID: {} has been returned by Patron with ID: {}", bookId, patronId);
    }
}