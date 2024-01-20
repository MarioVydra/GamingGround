package com.SemestralnaPraca.GamingGround.repository;

import com.SemestralnaPraca.GamingGround.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByproductTitleContaining(String title, Pageable pageable);

    Page<Product> findByPriceBetweenAndCategoryIn(Double minPrice, Double maxPrice, Collection<String> category, Pageable pageable);

    Page<Product> findByproductTitleContainingAndPriceBetweenAndCategoryIn(String title, Double minPrice, Double maxPrice, Collection<String> category, Pageable pageable);

    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByproductTitleContainingAndPriceBetween(String title, Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByproductTitleContainingAndCategoryIn(String title, Collection<String> category, Pageable pageable);

    Page<Product> findByCategoryIn(Collection<String> category, Pageable pageable);
}
