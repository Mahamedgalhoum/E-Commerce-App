package com.e_Commerce.E_CommerceApp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private Long userId;

    private LocalDate orderDate;

    private BigDecimal totalPrice;

    private String status;

    private List<OrderItemDto> items;
}
