package com.e_Commerce.E_CommerceApp.dto;

import com.e_Commerce.E_CommerceApp.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String brand;
    private int quantity;
    private double rating;
    private String categoryName;
    private List<String> images;


    // Constructor for mapping from Product entity
    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.brand = product.getBrand();
        this.quantity = product.getQuantity();
        this.rating = product.getRating();
        this.categoryName = product.getCategoryName();
        this.images = product.getImages() != null
                ? product.getImages().stream()
                .map(img -> img.getImage())
                .collect(Collectors.toList())
                : null;
    }

    // Default constructor
    public ProductDto() {}


}
