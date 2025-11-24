package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.OrderDto;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.order.OrderService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    // 1) Place a new order from cart
    @PostMapping("/place/{userId}")
    public ResponseEntity<ApiResponse> placeOrder(
            @PathVariable @NotNull(message = "User ID is required") @Positive(message = "User ID must be positive") Long userId) {
        OrderDto order = orderService.placeOrder(userId);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponse("Order placed successfully", order));
    }

    // 2) Get all orders of a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUser(
            @PathVariable @NotNull(message = "User ID is required") @Positive(message = "User ID must be positive") Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(new ApiResponse("User orders fetched successfully", orders));
    }

    // 3) Get details of a specific order
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(
            @PathVariable @NotNull(message = "Order ID is required") @Positive(message = "Order ID must be positive") Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new ApiResponse("Order details fetched successfully", order));
    }

    // 4) Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse> updateOrderStatus(
            @PathVariable @NotNull(message = "Order ID is required") @Positive(message = "Order ID must be positive") Long orderId,
            @RequestParam @NotNull(message = "Status is required") String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ApiResponse("Order status updated successfully", null));
    }

    // 5) Cancel an order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(
            @PathVariable @NotNull(message = "Order ID is required") @Positive(message = "Order ID must be positive") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Order canceled successfully", null));
    }

    // 6) Delete an order (Admin)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(
            @PathVariable @NotNull(message = "Order ID is required") @Positive(message = "Order ID must be positive") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Order deleted successfully", null));
    }

    // 7) Calculate total for a specific order
    @GetMapping("/{orderId}/total")
    public ResponseEntity<ApiResponse> calculateTotal(
            @PathVariable @NotNull(message = "Order ID is required") @Positive(message = "Order ID must be positive") Long orderId) {
        BigDecimal total = orderService.calculateTotal(orderId);
        return ResponseEntity.ok(new ApiResponse("Total calculated successfully", total));
    }

    // 8) Get all orders (Admin)
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse("All orders fetched successfully", orders));
    }
}
