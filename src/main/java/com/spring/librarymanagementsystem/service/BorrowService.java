package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.BorrowingRecordRepo;
import com.spring.librarymanagementsystem.entity.Book;
import com.spring.librarymanagementsystem.entity.BorrowingRecord;
import com.spring.librarymanagementsystem.entity.Patron;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class BorrowService {

    private BorrowingRecordRepo borrowingRecordRepo;
    private BookService bookService;
    private PatronService patronService;

    @Autowired
    public BorrowService(BorrowingRecordRepo borrowingRecordRepo, BookService bookService, PatronService patronService) {
        this.borrowingRecordRepo = borrowingRecordRepo;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Transactional
    public ResponseEntity<Void> borrowBook(long bookId, long patronId) {
        ResponseEntity<Book> bookResponse = bookService.findById(bookId);
        ResponseEntity<Patron> patronResponse = patronService.findById(patronId);
        if (bookResponse.getStatusCode() != HttpStatus.OK || patronResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
        {
            Book book = bookResponse.getBody();
            Patron patron = patronResponse.getBody();

            BorrowingRecord borrowingRecord=new BorrowingRecord();
            borrowingRecord.setBook(book);
            borrowingRecord.setPatron(patron);
            borrowingRecord.setBorrowDate(LocalDate.now());
            borrowingRecordRepo.save(borrowingRecord);
            return ResponseEntity.ok().build();
        }
    }

    @Transactional
    public ResponseEntity<Void> returnBook(long bookId, long patronId) {
        ResponseEntity<Book> bookResponse = bookService.findById(bookId);
        ResponseEntity<Patron> patronResponse = patronService.findById(patronId);
        if (bookResponse.getStatusCode() != HttpStatus.OK || patronResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
        {
            BorrowingRecord borrowingRecord=borrowingRecordRepo.findByBookIdAndPatronId(bookId,patronId);
            borrowingRecord.setReturnDate(LocalDate.now());
            borrowingRecordRepo.save(borrowingRecord);
            return ResponseEntity.ok().build();
        }
    }
}
