package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(UUID id) {
        return userRepository.findById(id).get();
    }

    public UUID saveUser(UserSaveRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        return userRepository.save(user).getId();
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User does not exists!");
        } else {
            userRepository.deleteById(id);
        }

    }

    public void updateUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User does not exists!");
        } else {
            // TODO
        }
    }
}
