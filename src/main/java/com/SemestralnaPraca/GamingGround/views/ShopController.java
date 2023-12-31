package com.SemestralnaPraca.GamingGround.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping("/shop")
    public String loadRegistration() {
        return "shop";
    }
}
