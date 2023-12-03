package com.SemestralnaPraca.GamingGround.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserSaveRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
  //  private OffsetDateTime dateOfBirth;
}
