package com.SemestralnaPraca.GamingGround.controller;


import com.SemestralnaPraca.GamingGround.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        addressService.deleteProduct(id);
    }
}
