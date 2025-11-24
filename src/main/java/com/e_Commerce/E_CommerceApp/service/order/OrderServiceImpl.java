package com.e_Commerce.E_CommerceApp.service.order;

import com.e_Commerce.E_CommerceApp.dto.OrderDto;
import com.e_Commerce.E_CommerceApp.dto.OrderItemDto;
import com.e_Commerce.E_CommerceApp.errors.ResourceNotFound;
import com.e_Commerce.E_CommerceApp.model.*;
import com.e_Commerce.E_CommerceApp.reposiotory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto placeOrder(Long userId) {
        User user  = userRepository.findById(userId)
                .orElseThrow(  ()-> new ResourceNotFound("User Not Found "));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFound("Cart has no items");
        }

        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            if (product.getQuantity() < cartItem.getQuantity()){
                throw new ResourceNotFound("Not Enough stock For Product " + product.getName());

            }
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            return orderItem;
                }
        ).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setTotalPrice(order.calculateTotalPrice());

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);

        return convertToDto(savedOrder);

    }



    @Override
    public List<OrderDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public OrderDto getOrderById(Long orderId) {
         Order order =orderRepository.findById(orderId)
                 .orElseThrow(()-> new ResourceNotFound("Order Not Found"));

         return convertToDto(order);

    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFound("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);

    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFound("Order not found"));

        order.getOrderItems().forEach(item ->{
            Product product =item.getProduct();
            product.setQuantity(product.getQuantity()+ item.getQuantity());
            productRepository.save(product);
        });
        order.setStatus("Cancelled");
        orderRepository.save(order);

    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId))
            throw new ResourceNotFound("Order Not Found ") ;
        orderRepository.deleteById(orderId);
    }

    @Override
    public BigDecimal calculateTotal(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFound("Order Not Found"));
        return order.calculateTotalPrice();
    }

    @Override
    public List<OrderDto> getAllOrders() {
         return orderRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private OrderDto convertToDto(Order order) {

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());

        dto.setItems(
                order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getProduct().getName(),
                                orderItem.getUnitPrice(),
                                orderItem.getQuantity(),
                                orderItem.getTotalPrice()
                        )).collect(Collectors.toList())
        );

        return dto;
    }
}
