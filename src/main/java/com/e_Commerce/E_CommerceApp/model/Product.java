package com.e_Commerce.E_CommerceApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(nullable = false)
    private String name ;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand ;

    @Column(nullable = false)
    private int quantity ;

    private double rating = 0;

    @ManyToOne
    @JoinColumn(name = "cat_id")
   // @JsonIgnore
    @JsonBackReference
    private Category category;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();


    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }

}
