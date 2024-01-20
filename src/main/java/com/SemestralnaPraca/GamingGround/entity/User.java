package com.SemestralnaPraca.GamingGround.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pouzivatel")
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "email")
    private String email;

    private String name;
    private String surname;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private boolean newsletter;
    private String roles;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Address address;
}
