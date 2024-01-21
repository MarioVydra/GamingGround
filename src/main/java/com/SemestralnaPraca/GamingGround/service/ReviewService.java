package com.SemestralnaPraca.GamingGround.service;

import com.SemestralnaPraca.GamingGround.entity.Product;
import com.SemestralnaPraca.GamingGround.entity.Review;
import com.SemestralnaPraca.GamingGround.entity.User;
import com.SemestralnaPraca.GamingGround.repository.ProductRepository;
import com.SemestralnaPraca.GamingGround.repository.ReviewRepository;
import com.SemestralnaPraca.GamingGround.repository.UserRepository;
import com.SemestralnaPraca.GamingGround.request.ReviewSaveRequest;
import com.SemestralnaPraca.GamingGround.request.ReviewUpdateRequest;
import com.SemestralnaPraca.GamingGround.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addReview(ReviewSaveRequest request) {
        Optional<Product> product = productRepository.findById(request.getProductId());
        User user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null && product.isPresent())
        {
            boolean alreadyReviewed = reviewRepository.existsByUserEmailAndProductId(user.getEmail(), product.get().getId());

            if (alreadyReviewed) {
                throw  new IllegalArgumentException("User already reviewed this product.");
            }
            Review review = new Review();

            review.setContent(request.getContent());
            review.setRating(request.getRating());
            review.setDate(LocalDateTime.now());
            review.setUser(user);
            review.setProduct(product.get());
            reviewRepository.save(review);
            user.getReviews().add(review);
            userRepository.save(user);
            product.get().getReviews().add(review);
            product.get().setAverageRating(this.calculateProductRating(product.get().getId()));
            productRepository.save(product.get());
        } else {
            throw new IllegalArgumentException("User or product does not exist.");
        }
    }

    public List<ReviewResponse> getReviews(UUID productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviews.stream().map(review -> convertToResponse(review)).collect(Collectors.toList());
    }

    private ReviewResponse convertToResponse(Review review)
    {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setContent(review.getContent());
        response.setRating(review.getRating());
        response.setDate(review.getDate());
        response.setNameAndSurname(review.getUser().getName() + " " + review.getUser().getSurname());
        response.setCanEdit(review.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()));
        return response;
    }

    public void deleteReview(UUID reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("Review does not exist.");
        } else {
            Optional<Review> review = reviewRepository.findById(reviewId);

            if (review.isPresent()) {
                Product product = review.get().getProduct();
                reviewRepository.deleteById(reviewId);
                product.setAverageRating(this.calculateProductRating(product.getId()));
                productRepository.save(product);
            }

        }
    }

    public void updateReview(UUID reviewId, ReviewUpdateRequest request) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("Review does not exist.");
        } else {
            Optional<Review> review = reviewRepository.findById(reviewId);

            if (review.isPresent())
            {
                review.get().setContent(request.getContent());
                review.get().setDate(LocalDateTime.now());
                reviewRepository.save(review.get());
            }
        }
    }

    private double calculateProductRating(UUID productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        double sum = 0.0;
        int count = 0;

        for (Review review: reviews) {
            sum += review.getRating();
            count++;
        }

        double averageRating = 0.0;
        if (count > 0) {
            averageRating = sum / count;
        }
        return Math.round(averageRating * 100.0) / 100.0;
    }
}
