package com.spring.librarymanagementsystem.service.service;

import com.spring.librarymanagementsystem.dto.BorrowDto;

public interface BorrowService {

    void borrowBook(BorrowDto borrowDto);
    void returnBook(long bookId, long patronId);
}
