package com.app.ecom.dto;


import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Integer id;
    private double price;
    private Integer quantity;

    private User user;

    private Product product;
}
