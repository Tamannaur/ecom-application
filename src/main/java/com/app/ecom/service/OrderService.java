package com.app.ecom.service;


import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.*;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    public OrderResponse createOrder(String userId) {
        // Implementation goes here
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (null == cartItems || cartItems.isEmpty()) {
            return null; // Or throw an exception indicating no items in cart
        }
        User user = userRepository.findById(Integer.parseInt(userId)).orElse(null);
        if (null == user) {
            return null; // Or throw an exception indicating user not found
        }
        Double totalPrice = cartItems.stream().map(CartItem::getPrice)
                .reduce(Double::sum).orElse(0.0);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItems> orderItems = cartItems.stream()
                .map(item->new OrderItems(null, item.getProduct(), item.getQuantity(), item.getPrice(),order))
                .toList();

        order.setOrderItems(orderItems);

        Order saveOrder = orderRepository.save(order);
        cartService.clerCart(userId);

        List<OrderItemDTO> orderItemDTOS = Optional.ofNullable(saveOrder.getOrderItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(oi -> OrderItemDTO.builder()
                        .productId(oi.getProduct().getId())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build())
                .toList();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .orderItems(orderItemDTOS)
                .build();

        return orderResponse;
    }

    public OrderResponse getOrderById(String orderId, String userId){
        User user = userRepository.findById(Integer.parseInt(userId)).orElse(null);
        if (null == user)return null;
        Order order = null;
        if(userRepository.existsById(Integer.parseInt(userId))) {
            order = orderRepository.findById(Integer.parseInt(orderId)).orElse(null);
        }

        if (null == order)return null;

        List<OrderItemDTO> orderItemDTOS = Optional.ofNullable(order.getOrderItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(oi -> OrderItemDTO.builder()
                        .productId(oi.getProduct().getId())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build())
                .toList();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .orderItems(orderItemDTOS)
                .build();
        return orderResponse;
    }
}
