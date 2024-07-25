package com.spring.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
    private boolean isAvailable;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Borrow> borrows = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    public boolean canBeBorrowed() {
        return this.isAvailable;
    }


    public void markAsBorrowed() {
        if (!canBeBorrowed()) {
            throw new IllegalStateException("Book cannot be borrowed because it is not available.");
        }
        this.isAvailable = false;
    }

    public void markAsReturned() {
        this.isAvailable = true;
    }


}