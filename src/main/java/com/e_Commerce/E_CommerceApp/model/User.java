package com.e_Commerce.E_CommerceApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
//@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String firstName ;

    @Column(nullable = false)
    private String LastName ;

    @Email
    @Column(unique = true)
    @NotEmpty(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password is Required")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    /*
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

     */
}
