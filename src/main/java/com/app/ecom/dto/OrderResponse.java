package com.app.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderResponse {

    private Integer orderId;
    private String status;
    private double totalAmount;
    private List<OrderItemDTO> orderItems;
}
