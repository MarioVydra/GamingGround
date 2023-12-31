package com.SemestralnaPraca.GamingGround.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {

    @GetMapping("/terms-and-conditions")
    public String loadRegistration() {
        return "terms-and-conditions";
    }
}
