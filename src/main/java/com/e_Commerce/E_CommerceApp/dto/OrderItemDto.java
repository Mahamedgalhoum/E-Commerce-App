package com.e_Commerce.E_CommerceApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
