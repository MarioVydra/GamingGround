package com.SemestralnaPraca.GamingGround.views;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String loadAbout(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = (authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated());

        model.addAttribute("isActuallyAuthenticated", isAuthenticated);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String substring = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;
        model.addAttribute("username",  substring.toUpperCase());
        return "about";
    }
}