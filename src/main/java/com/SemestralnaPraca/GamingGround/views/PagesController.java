package com.SemestralnaPraca.GamingGround.views;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/about")
    public String loadAbout(Model model) {
        addAttributes(model);
        return "about";
    }

    @GetMapping("/cart")
    public String loadCart(Model model) {
        addAttributes(model);
        return "cart";
    }

    @GetMapping("/contact")
    public String loadContact(Model model) {
        addAttributes(model);
        return "contact";
    }

    @GetMapping("/")
    public String loadIndex(Model model) {
        addAttributes(model);
        return "index";
    }

    @GetMapping("/login")
    public String loadLogin() {
        return "login";
    }

    @GetMapping("/privacy-policy")
    public String loadPrivacy(Model model) {
        addAttributes(model);
        return "privacy-policy";
    }

    @GetMapping("/profile")
    public String loadProfile(Model model) {
        addAttributes(model);
        return "profile";
    }

    @GetMapping("/registration")
    public String loadRegistration() {
        return "registration";
    }

    @GetMapping("/shop")
    public String loadShop(Model model) {
        addAttributes(model);
        return "shop";
    }

    @GetMapping("/terms-and-conditions")
    public String loadTerms(Model model) {
        addAttributes(model);
        return "terms-and-conditions";
    }

    @GetMapping("/users-page")
    public String loadUsersPage(Model model) {
        addAttributes(model);
        return "users-page";
    }

    @GetMapping("/product-details/{id}")
    public String loadProductDetail(Model model) {
        addAttributes(model);
        return "product-details";
    }

    private void addAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = (authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated());
        model.addAttribute("isActuallyAuthenticated", isAuthenticated);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String substring = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;

        model.addAttribute("username",  substring.toUpperCase());
        model.addAttribute("email",  email);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdminAuthenticated", isAdmin);
    }
}
