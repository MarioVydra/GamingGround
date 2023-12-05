package com.SemestralnaPraca.GamingGround.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckpointController {

    @GetMapping("/checkpoint")
    public String loadRegistration() {
        return "checkpoint";
    }
}