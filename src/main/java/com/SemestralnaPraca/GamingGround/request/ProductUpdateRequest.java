package com.SemestralnaPraca.GamingGround.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private String id;
    private String attribute;
    private String value;
}
