package com.spring.librarymanagementsystem.dto.mapstruct;

import com.spring.librarymanagementsystem.dto.BorrowDto;
import com.spring.librarymanagementsystem.entity.Borrow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BorrowMapper {


    BorrowDto toDto(Borrow borrow);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "patron", ignore = true)
    Borrow toEntity(BorrowDto borrowDto);
}
