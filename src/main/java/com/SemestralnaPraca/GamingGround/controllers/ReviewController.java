package com.SemestralnaPraca.GamingGround.controllers;

import com.SemestralnaPraca.GamingGround.request.ReviewSaveRequest;
import com.SemestralnaPraca.GamingGround.request.ReviewUpdateRequest;
import com.SemestralnaPraca.GamingGround.response.ReviewResponse;
import com.SemestralnaPraca.GamingGround.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReview(@Valid @RequestBody ReviewSaveRequest request, BindingResult bindingResult) {
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;

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

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable UUID reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok().body("Review successfully deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable UUID reviewId, @Valid @RequestBody ReviewUpdateRequest request, BindingResult bindingResult ) {
        ResponseEntity<?> errorMessages = getResponseEntity(bindingResult);
        if (errorMessages != null) return errorMessages;

        try {
            reviewService.updateReview(reviewId, request);
            return ResponseEntity.ok().body("Review successfully updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + ": " + errorMessage);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors", errorMessages));
        }
        return null;
    }
}
