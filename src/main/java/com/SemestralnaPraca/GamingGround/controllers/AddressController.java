package com.SemestralnaPraca.GamingGround.controllers;


import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{email}")
    public Address getUser(@PathVariable String email) {
        return addressService.getAddress(email);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }
}
