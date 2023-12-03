package com.SemestralnaPraca.GamingGround.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
@RequiredArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;
    private String number;
    private String zipcode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

