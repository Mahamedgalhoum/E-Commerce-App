package com.e_Commerce.E_CommerceApp.service.cart;

import com.e_Commerce.E_CommerceApp.dto.CartDto;
import com.e_Commerce.E_CommerceApp.dto.CartItemDto;
import com.e_Commerce.E_CommerceApp.dto.ProductDto;
import com.e_Commerce.E_CommerceApp.errors.ResourceNotFound;
import com.e_Commerce.E_CommerceApp.model.Cart;
import com.e_Commerce.E_CommerceApp.model.CartItem;
import com.e_Commerce.E_CommerceApp.model.Product;
import com.e_Commerce.E_CommerceApp.reposiotory.CartItemRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.CartRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.ProductRepository;
import com.e_Commerce.E_CommerceApp.reposiotory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public CartDto addProductToCart(Long userId, Long productId, int quantity) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product Not Found"));

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        cartRepository.save(cart);
        return convertCartToDto(cart);
    }


    @Override
    public CartDto removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("Cart not found"));

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFound("Item not found"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        cartRepository.save(cart);
        return convertCartToDto(cart);
    }

    @Override
    public CartDto getUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        return convertCartToDto(cart);
    }

    private Cart createNewCart(Long userId) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found")));
        return cartRepository.save(cart);
    }
    private CartItemDto convertCartItemToDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setProduct(new ProductDto(item.getProduct())); // هنا
        return dto;
    }

    private CartDto convertCartToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setItems(cart.getItems().stream()
                .map(this::convertCartItemToDto)
                .collect(Collectors.toList()));

        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice().doubleValue())
                .sum();
        dto.setTotal(total);

        return dto;
    }


}
