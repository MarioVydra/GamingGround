package com.SemestralnaPraca.GamingGround.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReviewResponse {
    private UUID id;
    private String content;
    private int rating;
    private LocalDateTime date;
    private String nameAndSurname;
    private boolean canEdit;


}
