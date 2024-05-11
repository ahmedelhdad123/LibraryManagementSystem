package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.PatronRepo;
import com.spring.librarymanagementsystem.entity.Patron;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {


    private PatronRepo patronRepo;

    @Autowired
    public PatronService(PatronRepo patronRepo) {
        this.patronRepo = patronRepo;
    }
    public ResponseEntity<List<Patron>> findAll() {
        List<Patron> patrons = patronRepo.findAll();
        return ResponseEntity.ok(patrons);
    }

    public ResponseEntity<Patron> findById(long id) {
        Optional<Patron> patronOptional = patronRepo.findById(id);
        return patronOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<Void> save(Patron patron) {
        patronRepo.save(patron);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<Void> update(long id, Patron updatedPatron) {
        Optional<Patron> oldPatronOptional = patronRepo.findById(id);
        if (oldPatronOptional.isPresent()) {
            Patron oldPatron = oldPatronOptional.get();
            oldPatron.setName(updatedPatron.getName());
            oldPatron.setAddress(updatedPatron.getAddress());
            oldPatron.setPhone(updatedPatron.getPhone());
            patronRepo.save(oldPatron);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<Void> deletePatron(long id) {
        patronRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
