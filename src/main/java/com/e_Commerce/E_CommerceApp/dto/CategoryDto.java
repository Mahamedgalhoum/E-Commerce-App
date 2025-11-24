package com.e_Commerce.E_CommerceApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Category image URL is required")
    private String image;
}
