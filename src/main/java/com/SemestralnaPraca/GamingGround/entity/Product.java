package com.SemestralnaPraca.GamingGround.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Product")
@Getter
@Setter
@RequiredArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productTitle;
    private String description;
    private double price;

//  private Img productImage;
}
