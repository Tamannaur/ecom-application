package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

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
        if (res.endsWith("not found"))return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") String userId){
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if(cartItems == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String productId, @RequestHeader("X-User-Id")String id){
        String s = cartService.deleteCartItems(productId,id);
        if (s.endsWith("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
        }
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }


}
