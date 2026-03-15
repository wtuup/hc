package com.freshsales.vo;

import lombok.Data;
import java.util.List;

@Data
public class OrderSubmitVO {
    private Long addressId;
    private String deliveryMethod;
    private String paymentMethod;
    private List<Long> cartItemIds;
}