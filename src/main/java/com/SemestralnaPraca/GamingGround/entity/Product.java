package com.SemestralnaPraca.GamingGround.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "product")
@RequiredArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productTitle;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private int quantity;
}
