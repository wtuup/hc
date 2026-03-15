package com.freshsales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("after_sale")
public class AfterSale {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private String type;       // RETURN, REFUND
    private String reason;
    private String status;     // PENDING, APPROVED, REJECTED
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime applyTime;
}