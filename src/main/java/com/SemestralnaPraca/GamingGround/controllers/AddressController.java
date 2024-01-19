package com.SemestralnaPraca.GamingGround.controllers;


import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{email}")
    public Address getAddress(@PathVariable String email) {
        return addressService.getAddress(email);
    }
}
