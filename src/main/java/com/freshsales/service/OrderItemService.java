package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.OrderItem;
import com.freshsales.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItem> {
    public List<OrderItem> getByOrderId(Long orderId) {
        return lambdaQuery().eq(OrderItem::getOrderId, orderId).list();
    }
}