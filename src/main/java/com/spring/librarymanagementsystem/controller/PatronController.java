package com.spring.librarymanagementsystem.controller;

import com.spring.librarymanagementsystem.entity.Patron;
import com.spring.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class PatronController {

    private PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping("patrons")
    public ResponseEntity<List<Patron>> getPatrons() {
        return ResponseEntity.ok(patronService.findAll().getBody());
    }

    @GetMapping("patrons/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable long id) {
        Patron patron = patronService.findById(id).getBody();
        if (patron != null) {
            return ResponseEntity.ok(patron);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("patrons")
    public ResponseEntity<String> createPatron(@RequestBody Patron patron) {
        patronService.save(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patron created");
    }

    @PutMapping("patrons/{id}")
    public ResponseEntity<String> updatePatron(@PathVariable long id, @RequestBody Patron patron) {
        patronService.update(id,patron);
        return ResponseEntity.ok("Patron updated");
    }

    @DeleteMapping("patrons/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok("Patron deleted");
    }
}
