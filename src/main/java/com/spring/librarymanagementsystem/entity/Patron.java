package com.spring.librarymanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private String phone;

    @Email
    @Column(unique = true, nullable = false)
    private String email;



    private String address;

    @OneToMany(mappedBy = "patron",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<BorrowingRecord> borrowingRecords=new ArrayList<>();

}
