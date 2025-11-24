package com.e_Commerce.E_CommerceApp.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private ProductDto product;
}
