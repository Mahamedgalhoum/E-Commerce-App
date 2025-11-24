package com.e_Commerce.E_CommerceApp.service.order;

import com.e_Commerce.E_CommerceApp.dto.OrderDto;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {


    // 1) Create a new order from the user's cart
    OrderDto placeOrder(Long userId);

    // 2) Get all orders for a specific user
    List<OrderDto> getOrdersByUser(Long userId);

    // 3) Get details of a single order
    OrderDto getOrderById(Long orderId);

    // 4) Update the order status (Pending → Confirmed → Paid → Shipped → Delivered)
    void updateOrderStatus(Long orderId, String status);

    // 5) Cancel an order
    void cancelOrder(Long orderId);

    // 6) Delete an order (Admin only)
    void deleteOrder(Long orderId);

    // 7) Calculate total order price
    BigDecimal calculateTotal(Long orderId);

    // 8) Get all orders in the system (Admin)
    List<OrderDto> getAllOrders();
}
