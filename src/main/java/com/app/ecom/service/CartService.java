package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    public String addToCart(String id, CartItemRequest cartItemRequest){
        Product product = productRepository.findById(Integer.parseInt(id)).orElse(null);
        if (product == null)return "Product not found";

        User user = userRepository.findById(Integer.parseInt(id)).orElse(null);
        if(null == user)return "User not found";

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice() * existingCartItem.getQuantity());
            cartItemRepository.save(existingCartItem);
            return "Updated quantity of existing cart item";
        } else {
            CartItem cartItem = mapCartItemFromReq(cartItemRequest, product, user);
            cartItemRepository.save(cartItem);
            return "Added new item to cart";
        }
    }

    public List<CartItem> getCartItems(String id){
        List<CartItem> cartItems = cartItemRepository.findById(Integer.parseInt(id)).map(List::of).orElse(null);
        return Optional.ofNullable(cartItems).orElse(null);
    }

    public String deleteCartItems(String productId, String userId){
        Product product = productRepository.findById(Integer.parseInt(productId)).orElse(null);
        if (product == null)return "Product not found";
        User user = userRepository.findById(Integer.parseInt(userId)).orElse(null);
        if (user == null)return "User not found";
        cartItemRepository.deleteByUserAndProduct(user, product);
        return "Cart items deleted successfully";

    }

    public void clerCart(String userId){
        userRepository.findById(Integer.parseInt(userId)).ifPresent(
                cartItemRepository::deleteByUser
        );
    }
    public static CartItem mapCartItemFromReq(CartItemRequest req, Product product, User user){
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(req.getQuantity());
        cartItem.setPrice(req.getQuantity() * product.getPrice());
        cartItem.setUser(user);
        cartItem.setProduct(product);
        return cartItem;
    }

}
