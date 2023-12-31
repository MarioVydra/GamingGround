package com.SemestralnaPraca.GamingGround.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String loadRegistration() {
        return "about";
    }
}