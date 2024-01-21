package com.SemestralnaPraca.GamingGround.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewSaveRequest {
    @NotEmpty(message = "Review content is required.")
    private String content;

    @NotEmpty(message = "Rating is required.")
    private int rating;

    @NotEmpty(message = "Product ID is required.")
    private UUID productId;
}
