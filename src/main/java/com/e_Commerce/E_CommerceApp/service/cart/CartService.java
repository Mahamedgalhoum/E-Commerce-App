package com.e_Commerce.E_CommerceApp.service.cart;

import com.e_Commerce.E_CommerceApp.dto.CartDto;

public interface CartService {
    CartDto addProductToCart(Long userId, Long productId, int quantity);
    CartDto removeProductFromCart(Long userId, Long productId);
    CartDto getUserCart(Long userId);
}
