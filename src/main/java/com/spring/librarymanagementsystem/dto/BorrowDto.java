package com.spring.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDto {

    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @NotNull
    private Long patronId;


    @NotNull
    private Long bookId;

}
