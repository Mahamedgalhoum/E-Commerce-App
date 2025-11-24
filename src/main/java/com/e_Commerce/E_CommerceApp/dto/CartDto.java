package com.e_Commerce.E_CommerceApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Long id;
    private List<CartItemDto> items;
    private double total;

}
