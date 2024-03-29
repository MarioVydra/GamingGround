package com.SemestralnaPraca.GamingGround.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "address")
@RequiredArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String city;
    private String street;
    private String number;
    private String zipcode;
    private String country;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

