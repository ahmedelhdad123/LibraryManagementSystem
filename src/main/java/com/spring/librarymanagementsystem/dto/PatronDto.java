package com.spring.librarymanagementsystem.dto;

import com.spring.librarymanagementsystem.entity.Borrow;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatronDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 to 15 characters")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    private String address;

    private List<BorrowDto> borrows;
}
