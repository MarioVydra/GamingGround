package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import com.SemestralnaPraca.GamingGround.request.UserSaveRequest;
import com.SemestralnaPraca.GamingGround.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public String saveUser(UserSaveRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());

        String dateString = request.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, formatter);
            user.setDateOfBirth(date);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setZipcode(request.getZipcode());
        address.setCountry(request.getCountry());
        address.setUser(user);

        user.getAddresses().add(address);

        return userRepository.save(user).getEmail();
    }

    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User does not exists!");
        } else {
            userRepository.deleteByEmail(email);
        }

    }

    public void updateUser(UserUpdateRequest request) {
        String email = request.getEmail();
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User does not exists!");
        } else {
            User user = userRepository.findUserByEmail(email);

            if (user == null) {
                throw new RuntimeException("User not found!");
            }

            String attribute = request.getAttribute();
            String value = request.getValue();
            switch (attribute.toLowerCase()) {
                case "name":
                    user.setName(value);
                    break;
                case "surname":
                    user.setSurname(value);
                    break;
                case "dateofbirth":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date = LocalDate.parse(value);
                    user.setDateOfBirth(date);
                    break;
                case "phonenumber":
                    user.setPhoneNumber(value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attribute " + attribute);
            }
            userRepository.save(user);

        }
    }
}
