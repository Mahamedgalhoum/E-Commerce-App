package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.ReviewDto;
import com.e_Commerce.E_CommerceApp.request.AddReviewRequest;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.review.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    @PostMapping
    public ResponseEntity<ApiResponse> addReview(@Valid @RequestBody AddReviewRequest request){
        ReviewDto reviewDto = reviewService.addReview(request);
        return ResponseEntity.ok(new ApiResponse("Review Added successfully", reviewDto));
    }

}
