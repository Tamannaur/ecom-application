package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public String addToCart(String id, CartItemRequest cartItemRequest){
        if(cartItemRepository.existsById(Integer.parseInt(id))){
            CartItem existingCartItem = cartItemRepository.findById(Integer.parseInt(id)).get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemRepository.save(existingCartItem);
            return "Updated quantity of existing cart item";
        } else {
            CartItem cartItem = mapCartItemFromReq(cartItemRequest);
            cartItemRepository.save(cartItem);
            return "Added new item to cart";
        }
    }
    public static CartItem mapCartItemFromReq(CartItemRequest req){
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(req.getQuantity());
        cartItem.setPrice(req.getPrice());
        cartItem.setUser(req.getUser());
        cartItem.setProduct(req.getProduct());
        return cartItem;
    }

}
