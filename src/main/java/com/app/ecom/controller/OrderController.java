package com.app.ecom.controller;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-Id") String userId) {
        OrderResponse order = orderService.createOrder(userId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetailsByOrderId(@PathVariable String orderId, @RequestHeader("X-User-Id") String userId) {
        // Implementation for fetching order details can be added here
        OrderResponse order = orderService.getOrderById(orderId, userId);
        return order == null ? ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null):
                ResponseEntity.status(HttpStatus.OK).body(order);
    }



}
