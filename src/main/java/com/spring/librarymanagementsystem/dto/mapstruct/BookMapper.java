package com.spring.librarymanagementsystem.dto.mapstruct;

import com.spring.librarymanagementsystem.dto.BookDto;
import com.spring.librarymanagementsystem.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);
}