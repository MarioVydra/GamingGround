package com.SemestralnaPraca.GamingGround.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyController {

    @GetMapping("/privacy-policy")
    public String loadRegistration() {
        return "privacy-policy";
    }
}
