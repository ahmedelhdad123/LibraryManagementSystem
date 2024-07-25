package com.spring.librarymanagementsystem.service.service;

import com.spring.librarymanagementsystem.dto.PatronDto;

import java.util.List;
import java.util.Optional;

public interface PatronService {

    List<PatronDto> getAllPatrons();
    Optional<PatronDto> getPatronById(long id);
    void savePatron(PatronDto patronDto);
    void updatePatron(PatronDto patronDto);
    void deletePatron(long id);
}