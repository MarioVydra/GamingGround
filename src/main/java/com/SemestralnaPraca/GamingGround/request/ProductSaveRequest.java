package com.SemestralnaPraca.GamingGround.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaveRequest {
    private String productTitle;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private int quantity;
}
