package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import com.SemestralnaPraca.GamingGround.request.UserUpdateRequest;
import com.SemestralnaPraca.GamingGround.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserSaveRequest request, BindingResult  bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");
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
}
