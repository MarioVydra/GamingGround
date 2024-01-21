package com.SemestralnaPraca.GamingGround.repository;

import com.SemestralnaPraca.GamingGround.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUserEmail(String email);
}
