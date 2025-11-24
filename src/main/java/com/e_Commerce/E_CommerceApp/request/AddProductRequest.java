package com.e_Commerce.E_CommerceApp.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @PositiveOrZero(message = "Sale must be zero or positive")
    private Number sale = 0;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @NotNull(message = "Category is required")
    private Long category;

    @NotEmpty(message = "At least one image URL is required")
    private String[] images;
}
