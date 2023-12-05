package com.SemestralnaPraca.GamingGround.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserSaveRequest {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private String dateOfBirth;
    private String street;
    private String number;
    private String zipcode;
    private String country;
}
