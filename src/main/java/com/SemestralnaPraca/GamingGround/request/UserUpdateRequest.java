package com.SemestralnaPraca.GamingGround.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String email;
    private String attribute;
    private String value;
}
