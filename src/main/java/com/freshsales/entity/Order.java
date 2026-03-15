package com.freshsales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private Long addressId;
    private String deliveryMethod;
    private String paymentMethod;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}