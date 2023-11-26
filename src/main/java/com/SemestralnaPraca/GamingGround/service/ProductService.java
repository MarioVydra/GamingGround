package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.repository.ProductRepository;
import com.SemestralnaPraca.GamingGround.request.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(UUID id) {
        return productRepository.findById(id).get();
    }

    public UUID saveProduct(ProductSaveRequest request) {
        Product product = new Product();
        product.setProductTitle(request.getProductTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        return productRepository.save(product).getId();
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product does not exists!");
        } else {
            productRepository.deleteById(id);
        }

    }

    public void updateProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product does not exists!");
        } else {
            // TODO
        }
    }
}
