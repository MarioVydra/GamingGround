package com.SemestralnaPraca.GamingGround.response;

import com.SemestralnaPraca.GamingGround.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItemResponse {
    private List<Product> products;
    private double finalPrice;
}
