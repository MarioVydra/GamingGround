package com.SemestralnaPraca.GamingGround.controller;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.request.UserRegisterRequest;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import com.SemestralnaPraca.GamingGround.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public UUID saveUser(@RequestBody UserSaveRequest request) {
        return userService.saveUser(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable UUID id) {
        userService.updateUser(id);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterRequest request) { userService.registerUser(request); }
}
