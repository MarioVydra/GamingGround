package com.SemestralnaPraca.GamingGround.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pouzivatel")
@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();
}
