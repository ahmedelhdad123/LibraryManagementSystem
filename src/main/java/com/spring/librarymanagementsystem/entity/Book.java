package com.spring.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String publicationYear;

    @Column(length = 500)
    private String description;

    private String isbn;

    @JsonIgnore
    @OneToMany(mappedBy = "book",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<BorrowingRecord> borrowingRecords=new ArrayList<>();
}
