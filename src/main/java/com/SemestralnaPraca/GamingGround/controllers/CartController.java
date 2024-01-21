package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.response.CartItemResponse;
import com.SemestralnaPraca.GamingGround.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable UUID productId) {
        try {
            cartService.addToCart(productId);
            return ResponseEntity.ok().body("Item successfully added to the cart.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/items")
    public CartItemResponse loadItems() {
        return cartService.loadItems();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable UUID productId) {
        try {
            cartService.removeItemFromCart(productId);
            return ResponseEntity.ok().body("Item successfully removed from the cart.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> processOrder() {
        try {
            cartService.processOrder();
            return ResponseEntity.ok().body("Order processed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
