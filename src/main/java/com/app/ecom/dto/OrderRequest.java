package com.app.ecom.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderRequest {
    private Integer orderId;
    private String status;
    private double totalAmount;
}
