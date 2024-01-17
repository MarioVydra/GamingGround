package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.request.ProductSaveRequest;
import com.SemestralnaPraca.GamingGround.request.ProductUpdateRequest;
import com.SemestralnaPraca.GamingGround.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProduct(@RequestBody ProductSaveRequest request) {
        try {
            UUID id =  productService.saveProduct(request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@RequestBody ProductUpdateRequest request) {
        productService.updateProduct(request);
    }

}