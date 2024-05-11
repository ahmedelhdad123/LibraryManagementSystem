package com.spring.librarymanagementsystem.dao;

import com.spring.librarymanagementsystem.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {

     BorrowingRecord findByBookIdAndPatronId(Long bookId, Long patronId);
}
