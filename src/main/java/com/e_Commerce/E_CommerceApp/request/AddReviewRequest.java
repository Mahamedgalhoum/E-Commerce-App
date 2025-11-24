package com.e_Commerce.E_CommerceApp.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddReviewRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Product ID is required")
    private Long productID;

    @NotBlank(message = "Review text cannot be empty")
    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String reviewText;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private int rating;
}
