package com.SemestralnaPraca.GamingGround.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewSaveRequest {
    @NotEmpty(message = "Review content is required.")
    private String content;

    @NotNull(message = "Rating is required.")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be no more than 5")
    private int rating;

    @NotNull(message = "Product ID is required.")
    private UUID productId;
}
