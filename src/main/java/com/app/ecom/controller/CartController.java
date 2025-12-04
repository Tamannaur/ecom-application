package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.function.ServerResponse;

@Controller
@RequestMapping("api/cartItems")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") String userId,
                        @RequestBody CartItemRequest cartItemRequest)
    {
        String res = cartService.addToCart(userId, cartItemRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(res);
    }

}
