package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.request.*;
import com.SemestralnaPraca.GamingGround.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;

        try {
            String userEmail = userService.saveUser(request);
            return ResponseEntity.ok(userEmail);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeleteRequest request, BindingResult  bindingResult,  HttpServletResponse response) {
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;

        try {
            userService.deleteUser(request);
            response.addCookie(deleteCookie());
            return ResponseEntity.ok().body("Account successfully deleted.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest request, BindingResult  bindingResult) {
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;
        try {
            userService.updateUser(request);
            return ResponseEntity.ok().body("Profile information successfully updated.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest request, BindingResult  bindingResult, HttpServletResponse response) {
       ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
       if (errorMessages != null) return errorMessages;

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
        response.addCookie(deleteCookie());
        return ResponseEntity.ok().body("User logged out successfully.");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserPasswordChangeRequest request, BindingResult  bindingResult, HttpServletResponse response) {
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;

        try {
            userService.changePassword(request);
            Cookie cookie = new Cookie("jwtToken", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return ResponseEntity.ok().body("Password changed successfully.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/subscribeNewsletter/{email}")
    public ResponseEntity<?> subscribeNewsletter(@PathVariable String email) {
        try {
            userService.subscribeNewsletter(email);
            return ResponseEntity.ok().body("You have successfully subscribed to the newsletter.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Cookie deleteCookie() {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        return cookie;
    }

    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
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
        return null;
    }
}
