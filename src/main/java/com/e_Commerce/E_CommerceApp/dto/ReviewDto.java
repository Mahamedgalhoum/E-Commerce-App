package com.e_Commerce.E_CommerceApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String reviewText;
    private double rating;
    private ProductDto product;
}
