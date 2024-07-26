package com.spring.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Borrow> borrows = new ArrayList<>();

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
        borrow.setPatron(this);
    }

    public void removeBorrow(Borrow borrow) {
        borrows.remove(borrow);
        borrow.setPatron(null);
    }
}