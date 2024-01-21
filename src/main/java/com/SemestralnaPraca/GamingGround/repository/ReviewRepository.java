package com.SemestralnaPraca.GamingGround.repository;

import com.SemestralnaPraca.GamingGround.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    boolean existsByUserEmailAndProductId(String userEmail, UUID productId);

    List<Review> findAllByProductId(UUID productId);
}
