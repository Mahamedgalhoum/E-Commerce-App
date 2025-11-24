package com.e_Commerce.E_CommerceApp.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Brand is required")
    @Size(max = 100, message = "Brand cannot exceed 100 characters")
    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Sale must be non-negative")
    private Number sale = 0;

    @Min(value = 0, message = "Quantity must be non-negative")
    private int quantity;

    @NotNull(message = "Category ID is required")
    private Long category;

    @NotNull(message = "Images cannot be null")
    private String[] images;
}
