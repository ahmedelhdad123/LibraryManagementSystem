package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.dto.BorrowDto;
import com.spring.librarymanagementsystem.service.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/borrows")
@RequiredArgsConstructor
@Validated
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Void> addBorrow(@RequestBody @Valid BorrowDto borrowDto) {
        borrowService.borrowBook(borrowDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{bookId}/{patronId}")
    public ResponseEntity<Void> returnBorrow(@PathVariable long bookId, @PathVariable long patronId) {
        borrowService.returnBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }
}