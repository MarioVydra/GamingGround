package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.SemestralnaPraca.GamingGround.repository.AddressRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public void deleteAddress(UUID id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address does not exists!");
        } else {
            addressRepository.deleteById(id);
        }

    }

    public Address getAddress(String email) {
        return addressRepository.findAddressByUser(userRepository.findUserByEmail(email));
    }
}
