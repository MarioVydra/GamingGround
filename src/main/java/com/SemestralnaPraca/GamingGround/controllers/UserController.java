package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.request.UserLoginRequest;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import com.SemestralnaPraca.GamingGround.request.UserUpdateRequest;
import com.SemestralnaPraca.GamingGround.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserSaveRequest request, BindingResult  bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + ": " + errorMessage);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errorMessages));
        }

        try {
            String userEmail = userService.saveUser(request);
            return ResponseEntity.ok(userEmail);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PutMapping("/update/{email}")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest request, BindingResult  bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + ": " + errorMessage);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errorMessages));
        }

        try {
            String token = userService.loginUser(request);
            Cookie authCookie = new Cookie("jwtToken", token);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(true);
            authCookie.setPath("/");
            response.addCookie(authCookie);
            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().body("User logged out successfully.");
    }
}
