package com.SemestralnaPraca.GamingGround.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Cart cart;

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
