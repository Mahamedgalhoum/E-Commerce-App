package com.e_Commerce.E_CommerceApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity ;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product  product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order ;

    private BigDecimal unitPrice;

    // Calculate the total price for this item (unitPrice * quantity)
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }

}
