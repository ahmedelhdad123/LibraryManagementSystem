package com.spring.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    private String author;

    @NotNull(message = "Publication year cannot be null")
    @Pattern(regexp = "\\d{4}", message = "Publication year must be a 4-digit number")
    private String publicationYear;

    @Column(length = 500)
    private String description;

    @NotNull(message = "ISBN cannot be null")
    @Column(unique = true)
    private String isbn;

    @Column
    private long quantity;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Borrow> borrows = new ArrayList<>();

    public void decreaseQuantity() {
        this.quantity--;
    }

    public void increaseQuantity() {
        this.quantity++;
    }
}
