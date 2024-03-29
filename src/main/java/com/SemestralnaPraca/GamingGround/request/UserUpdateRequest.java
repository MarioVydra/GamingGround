package com.SemestralnaPraca.GamingGround.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message =  "Surname is required.")
    private String surname;

    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "^\\+[0-9]{1,12}$", message = "Invalid phone number format.")
    private String phoneNumber;

    @NotEmpty(message = "Date of birth is required.")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$", message = "Invalid date of birth format.")
    private String dateOfBirth;

    @NotEmpty(message = "City is required.")
    private String city;

    @NotEmpty(message = "Street is required.")
    private String street;

    @NotEmpty(message = "Number is required.")
    private String number;

    @NotEmpty(message = "Zipcode is required.")
    @Pattern(regexp = "^\\d{3} \\d{2}$", message = "Invalid ZIP code format.")
    private String zipcode;

    @NotEmpty(message = "Country is required.")
    private String country;
}
