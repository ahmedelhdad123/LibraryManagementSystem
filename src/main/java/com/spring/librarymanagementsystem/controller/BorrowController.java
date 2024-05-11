package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class BorrowController {

    private BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrow(@PathVariable long bookId, @PathVariable long patronId) {
        borrowService.borrowBook(bookId,patronId);
        return ResponseEntity.ok("Borrowed successfully");
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable long bookId, @PathVariable long patronId) {
        borrowService.returnBook(bookId,patronId);
        return ResponseEntity.ok("Returned successfully");
    }
}
