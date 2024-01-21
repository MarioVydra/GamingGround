package com.SemestralnaPraca.GamingGround.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
    private double averageRating = 0.0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
