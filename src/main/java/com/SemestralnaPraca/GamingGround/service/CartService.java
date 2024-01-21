package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Cart;
import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.CartRepository;
import com.SemestralnaPraca.GamingGround.repository.ProductRepository;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import com.SemestralnaPraca.GamingGround.response.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addToCart(UUID productId)
    {
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            Cart cart = cartRepository.findByUserEmail(user.getEmail());
            Optional<Product> product = productRepository.findById(productId);

            if (cart != null && product.isPresent()) {
                if (product.get().getQuantity() == 0) {
                    throw new IllegalArgumentException("This item is not in stock.");
                }
                cart.getProducts().add(product.get());
                cartRepository.save(cart);
            } else {
                throw new IllegalArgumentException("Cart or product does not exists.");
            }
        } else {
            throw new IllegalArgumentException("User is not logged in.");
        }
    }

    public CartItemResponse loadItems() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartRepository.findByUserEmail(email);
        double finalPrice = 0.0;
        CartItemResponse response = new CartItemResponse();

        if (cart != null) {
            for (Product product : cart.getProducts()) {
                finalPrice += product.getPrice();
            }
            response.setProducts(new ArrayList<>(cart.getProducts()));
            response.setFinalPrice(finalPrice);
        }
        return response;
    }

    public void removeItemFromCart(UUID productId) {
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            Cart cart = cartRepository.findByUserEmail(user.getEmail());
            Optional<Product> product = productRepository.findById(productId);

            if (cart != null && product.isPresent()) {
                cart.getProducts().remove(product.get());
                cartRepository.save(cart);
            } else {
                throw new IllegalArgumentException("Cart or product does not exists.");
            }
        } else {
            throw new IllegalArgumentException("User is not logged in.");
        }
    }

    public void processOrder() {
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartRepository.findByUserEmail(user.getEmail());
        if (cart != null) {
            for (Product product : cart.getProducts()) {
                if (product.getQuantity() != 0)
                {
                    product.setQuantity(product.getQuantity() - 1);
                    productRepository.save(product);
                }
            }
            cart.getProducts().clear();
            cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Cart does not exist.");
        }
    }
}
