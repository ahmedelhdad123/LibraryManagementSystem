package com.spring.librarymanagementsystem.dao;

import com.spring.librarymanagementsystem.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<Borrow, Long> {

     Borrow findByBookIdAndPatronId(Long bookId, Long patronId);
}
