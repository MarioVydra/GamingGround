package com.SemestralnaPraca.GamingGround.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.SemestralnaPraca.GamingGround.repository.AddressRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public void deleteProduct(UUID id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Product does not exists!");
        } else {
            addressRepository.deleteById(id);
        }

    }
}
