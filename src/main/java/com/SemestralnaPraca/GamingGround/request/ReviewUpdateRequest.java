package com.SemestralnaPraca.GamingGround.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {

    @NotEmpty(message = "Content is required.")
    String content;
}
