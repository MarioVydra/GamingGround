package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.config.JwtUtil;
import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.AddressRepository;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import com.SemestralnaPraca.GamingGround.request.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;

    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public String saveUser(UserSaveRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email is already registered.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        user.setPhoneNumber(request.getPhoneNumber());
        if (request.getEmail().endsWith("@gamingground.com")) {
            user.setRoles("ROLE_ADMIN,ROLE_USER");
        } else {
            user.setRoles("ROLE_USER");
        }

        String dateString = request.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, formatter);
            user.setDateOfBirth(date);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format. Expected format: dd.MM.yyyy");
        }

        Address address = new Address();
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setZipcode(request.getZipcode());
        address.setCountry(request.getCountry());
        address.setUser(user);

        user.setAddress(address);

        return userRepository.save(user).getEmail();
    }

    @Transactional
    public void deleteUser(UserDeleteRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException("User does not exists!");
        } else {
            if (passwordEncoder.matches(request.getPassword2(), userRepository.findUserByEmail(request.getEmail()).getPassword())) {
                userRepository.deleteByEmail(request.getEmail());
            } else {
                throw new BadCredentialsException("Invalid password.");
            }
        }
    }

    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());

        if (user != null) {
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setPhoneNumber(request.getPhoneNumber());

            String dateString = request.getDateOfBirth();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date;
            try {
                date = LocalDate.parse(dateString, formatter);
                user.setDateOfBirth(date);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Invalid date format. Expected format: dd.MM.yyyy");
            }

            Address address = addressRepository.findAddressByUser(user);
            address.setCity(request.getCity());
            address.setStreet(request.getStreet());
            address.setNumber(request.getNumber());
            address.setZipcode(request.getZipcode());
            address.setCountry(request.getCountry());
            address.setUser(user);

            userRepository.save(user);
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Invalid email.");
        }

    }

    public String loginUser(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return JwtUtil.generateToken(request.getEmail());
    }

    public void changePassword(UserPasswordChangeRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());
        if (user != null) {
            if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                String hashedNewPassword = passwordEncoder.encode(request.getPassword());
                user.setPassword(hashedNewPassword);
                userRepository.save(user);
            } else {
                throw new BadCredentialsException("Invalid old password.");
            }
        } else {
            throw new BadCredentialsException("Invalid email address.");
        }
    }

    public void subscribeNewsletter(String email) {
        if (!userRepository.existsByEmail(email))
        {
            throw new RuntimeException("Invalid email address.");
        } else {
            User user = userRepository.findUserByEmail(email);
            user.setNewsletter(true);
            userRepository.save(user);
        }
    }
}
