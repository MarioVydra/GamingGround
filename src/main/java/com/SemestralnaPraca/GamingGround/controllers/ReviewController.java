package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.request.ReviewSaveRequest;
import com.SemestralnaPraca.GamingGround.response.ReviewResponse;
import com.SemestralnaPraca.GamingGround.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReview(@RequestBody ReviewSaveRequest request) {
        try {
            reviewService.addReview(request);
            return ResponseEntity.ok().body("Review successfully added.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/reviews/{productId}")
    public List<ReviewResponse> loadReviews(@PathVariable UUID productId) {
        return reviewService.getReviews(productId);
    }
}
