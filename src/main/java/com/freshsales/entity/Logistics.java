package com.freshsales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("logistics")
public class Logistics {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private String company;
    private String trackingNumber;
    private String status;     // SHIPPED, DELIVERED
}