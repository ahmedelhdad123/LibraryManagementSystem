package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.dto.PatronDto;
import com.spring.librarymanagementsystem.service.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patrons")
@RequiredArgsConstructor
@Validated
public class PatronController {

    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<List<PatronDto>> getAllPatrons() {
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable long id) {
        return patronService.getPatronById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createPatron(@RequestBody @Valid PatronDto patronDto) {
        patronService.savePatron(patronDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePatron(@PathVariable long id, @RequestBody @Valid PatronDto patronDto) {
        patronDto.setId(id);
        patronService.updatePatron(patronDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}