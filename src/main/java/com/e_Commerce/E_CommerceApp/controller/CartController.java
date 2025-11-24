package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.CartDto;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.cart.CartService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<ApiResponse> addProductToCart(
            @PathVariable Long userId,
            @RequestParam @Positive(message = "Product ID must be positive") Long productId,
            @RequestParam @Positive(message = "Quantity must be greater than 0") int quantity) {

        CartDto cartDto = cartService.addProductToCart(userId, productId, quantity);

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse("Product added to cart successfully", cartDto));
    }


    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<ApiResponse> removeProductFromCart(
            @PathVariable Long userId,
            @RequestParam Long productId) {

        CartDto cartDto = cartService.removeProductFromCart(userId, productId);

        return ResponseEntity.ok(
                new ApiResponse("Product removed from cart successfully", cartDto)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId) {

        CartDto cartDto = cartService.getUserCart(userId);

        return ResponseEntity.ok(
                new ApiResponse("Cart fetched successfully", cartDto)
        );
    }
}
