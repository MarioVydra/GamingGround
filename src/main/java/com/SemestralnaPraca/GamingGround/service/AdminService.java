package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllByRoles("ROLE_USER");
    }

    @Transactional
    public void deleteUsers(List<String> emails) {
        for (String email : emails) {
            if (!userRepository.existsByEmail(email)) {
                throw new BadCredentialsException("User does not exists with email: " + email);
            } else {
                userRepository.deleteByEmail(email);
            }
        }
    }
}
