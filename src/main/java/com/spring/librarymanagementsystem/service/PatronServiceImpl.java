package com.spring.librarymanagementsystem.service;

import com.spring.librarymanagementsystem.dao.PatronRepo;
import com.spring.librarymanagementsystem.dto.PatronDto;
import com.spring.librarymanagementsystem.dto.mapstruct.PatronMapper;
import com.spring.librarymanagementsystem.entity.Patron;
import com.spring.librarymanagementsystem.exception.ResourceNotFound;
import com.spring.librarymanagementsystem.service.service.PatronService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepo patronRepo;
    private final PatronMapper patronMapper;
    private static final Logger logger = LoggerFactory.getLogger(PatronServiceImpl.class);

    @Override
    @Cacheable(value = "patrons", key = "#root.methodName")
    public List<PatronDto> getAllPatrons() {
        logger.info("Fetching all patrons");
        return patronRepo.findAll()
                .stream()
                .map(patronMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "patrons", key = "#id")
    public Optional<PatronDto> getPatronById(long id) {
        logger.info("Fetching patron with ID: {}", id);
        return patronRepo.findById(id)
                .map(patronMapper::toDto)
                .or(() -> {
                    logger.error("Patron not found with ID: {}", id);
                    throw new ResourceNotFound("Patron not found with ID: " + id);
                });
    }

    @Override
    @CacheEvict(value = "patrons", allEntries = true)
    public void savePatron(PatronDto patronDto) {
        Patron patron = patronMapper.toEntity(patronDto);
        logger.info("Saving new patron: {}", patron);
        patronRepo.save(patron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#patronDto.id",allEntries = true)
    public void updatePatron(PatronDto patronDto) {
        Patron existingPatron = patronRepo.findById(patronDto.getId())
                .orElseThrow(() -> {
                    logger.error("Patron not found with ID: {}", patronDto.getId());
                    return new ResourceNotFound("Patron not found with ID: " + patronDto.getId());
                });

        existingPatron.setName(patronDto.getName());
        existingPatron.setEmail(patronDto.getEmail());
        existingPatron.setPhone(patronDto.getPhone());
        existingPatron.setAddress(patronDto.getAddress());

        logger.info("Updating patron with ID: {}", patronDto.getId());
        patronRepo.save(existingPatron);
    }

    @Override
    @CacheEvict(value = "patrons", key = "#id",allEntries = true)
    public void deletePatron(long id) {
        logger.info("Deleting patron with ID: {}", id);
        if (!patronRepo.existsById(id)) {
            logger.error("Patron not found with ID: {}", id);
            throw new ResourceNotFound("Patron not found with ID: " + id);
        }
        patronRepo.deleteById(id);
    }
}