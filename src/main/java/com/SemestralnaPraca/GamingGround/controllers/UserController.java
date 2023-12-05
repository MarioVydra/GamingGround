package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import com.SemestralnaPraca.GamingGround.request.UserUpdateRequest;
import com.SemestralnaPraca.GamingGround.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    @Transactional
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @PostMapping("/save")
    @Transactional
    public String saveUser(@RequestBody UserSaveRequest request) {
        return userService.saveUser(request);
    }

    @DeleteMapping("/delete/{email}")
    @Transactional
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PutMapping("/update/{email}")
    @Transactional
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }
}
