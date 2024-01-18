package com.SemestralnaPraca.GamingGround.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordChangeRequest {

    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotEmpty(message = "Old password is required.")
    @Size(min = 10, message = "Invalid old password.")
    private String oldPassword;

    @NotEmpty(message = "New password is required.")
    @Size(min = 10, message = "New password must have at least 10 characters.")
    private String password;
}
