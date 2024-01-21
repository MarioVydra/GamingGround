package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.request.ProductSaveRequest;
import com.SemestralnaPraca.GamingGround.request.ProductUpdateRequest;
import com.SemestralnaPraca.GamingGround.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @GetMapping("/products")
    public Page<Product> showProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "-1") Double minPrice,
                                      @RequestParam(defaultValue = "Double.MAX_VALUE") Double maxPrice,
                                      @RequestParam(required = false) List<String> category,
                                      @RequestParam(defaultValue = "") String title,
                                      @RequestParam(defaultValue = "id") String sortBy)
    {
        return productService.getProducts(page, size, minPrice, maxPrice, category, title, sortBy);
    }

    @GetMapping("/indexProducts")
    public Page<Product> showProducts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size)
    {
        return productService.getIndexProducts(page, size);
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

    @PostMapping("/upload")
    public ResponseEntity<?> handleFile(@RequestParam("file")MultipartFile file)
    {
        try {
            productService.handleFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}