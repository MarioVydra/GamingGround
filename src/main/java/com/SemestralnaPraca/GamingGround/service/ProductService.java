package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.repository.ProductRepository;
import com.SemestralnaPraca.GamingGround.request.ProductSaveRequest;
import com.SemestralnaPraca.GamingGround.request.ProductUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProduct(UUID id) {
        return productRepository.findById(id).get();
    }

    public Page<Product> getProducts(int page, int size, Double minPrice, Double maxPrice, List<String> category, String title, String sortBy) {

        Pageable pageable = switch (sortBy) {
            case ("priceAsc") -> PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "price"));
            case ("priceDesc") -> PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "price"));
            default -> PageRequest.of(page, size, Sort.by(sortBy));
        };

        boolean titleProvided = !title.isEmpty();
        boolean priceRangeProvided = minPrice >= 0 || maxPrice < Double.MAX_VALUE;
        boolean categoryProvided = !category.get(0).isEmpty();

        if (titleProvided && priceRangeProvided && categoryProvided) {
            return productRepository.findByproductTitleContainingAndPriceBetweenAndCategoryIn(title, minPrice, maxPrice, category, pageable);
        } else if (titleProvided && priceRangeProvided) {
            return productRepository.findByproductTitleContainingAndPriceBetween(title, minPrice, maxPrice, pageable);
        } else if (priceRangeProvided && categoryProvided) {
            return productRepository.findByPriceBetweenAndCategoryIn(minPrice, maxPrice, category, pageable);
        } else if (titleProvided && categoryProvided) {
            return productRepository.findByproductTitleContainingAndCategoryIn(title, category, pageable);
        } else if (titleProvided) {
            return productRepository.findByproductTitleContaining(title, pageable);
        } else if (priceRangeProvided) {
            return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (categoryProvided) {
            return productRepository.findByCategoryIn(category, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public UUID saveProduct(ProductSaveRequest request) {
        Product product = new Product();
        product.setProductTitle(request.getProductTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setQuantity(request.getQuantity());
        return productRepository.save(product).getId();
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product does not exists!");
        } else {
            productRepository.deleteById(id);
        }

    }

    public void updateProduct(ProductUpdateRequest request) {
        UUID id = UUID.fromString(request.getId());
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product does not exists!");
        } else {
            Product product = productRepository.findById(id).get();

            String attribute = request.getAttribute();
            String value = request.getValue();
            switch (attribute.toLowerCase()) {
                case "producttitle":
                    product.setProductTitle(value);
                    break;
                case "description":
                    product.setDescription(value);
                    break;
                case "price":
                    double price = Double.parseDouble(value);
                    product.setPrice(price);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute " + attribute);
            }
            productRepository.save(product);
        }
    }

    public void handleFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = objectMapper.readValue(file.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

        productRepository.saveAll(products);
    }
}
