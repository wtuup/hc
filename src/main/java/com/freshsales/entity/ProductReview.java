package com.freshsales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("product_review")
public class ProductReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long userId;
    private String comment;
    private Integer rating;  // 1-5
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}