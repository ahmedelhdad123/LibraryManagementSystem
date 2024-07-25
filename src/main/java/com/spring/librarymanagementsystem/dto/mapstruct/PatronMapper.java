package com.spring.librarymanagementsystem.dto.mapstruct;

import com.spring.librarymanagementsystem.dto.PatronDto;
import com.spring.librarymanagementsystem.entity.Patron;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatronMapper {

    PatronDto toDto(Patron patron);
    Patron toEntity(PatronDto patronDto);
}