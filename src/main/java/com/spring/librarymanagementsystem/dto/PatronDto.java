package com.spring.librarymanagementsystem.dto;

import com.spring.librarymanagementsystem.entity.Borrow;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatronDto {

    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    List<Borrow> borrows;
}