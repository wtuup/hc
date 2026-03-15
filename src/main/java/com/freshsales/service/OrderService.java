package com.freshsales.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.*;
import com.freshsales.mapper.OrderMapper;
import com.freshsales.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderService(CartService cartService, ProductService productService, OrderItemService orderItemService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Transactional
    public Order submitOrder(Long userId, OrderSubmitVO vo) {
        List<Cart> cartItems = cartService.getUserCart(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }
        if (vo.getCartItemIds() != null && !vo.getCartItemIds().isEmpty()) {
            cartItems = cartItems.stream()
                    .filter(c -> vo.getCartItemIds().contains(c.getId()))
                    .toList();
        }

        BigDecimal total = BigDecimal.ZERO;
        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(vo.getAddressId());
        order.setDeliveryMethod(vo.getDeliveryMethod());
        order.setPaymentMethod(vo.getPaymentMethod());
        order.setStatus("PAID");
        order.setCreateTime(LocalDateTime.now());

        for (Cart item : cartItems) {
            Product product = productService.getById(item.getProductId());
            productService.decreaseStock(product.getId(), item.getQuantity());
            productService.incrementSales(product.getId(), item.getQuantity());
            total = total.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        order.setTotalAmount(total);
        save(order);

        for (Cart item : cartItems) {
            Product product = productService.getById(item.getProductId());
            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getId());
            oi.setProductId(item.getProductId());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(product.getPrice());
            orderItemService.save(oi);
        }
        cartService.clearCart(userId);
        return order;
    }

    public List<Order> getUserOrders(Long userId) {
        return lambdaQuery().eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreateTime)
                .list();
    }

    public List<Order> getAllOrders(String status, Long userId) {
        LambdaQueryWrapper<Order> w = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            w.eq(Order::getStatus, status);
        }
        if (userId != null) {
            w.eq(Order::getUserId, userId);
        }
        w.orderByDesc(Order::getCreateTime);
        return list(w);
    }

    public void cancelOrder(Long orderId, Long userId) {
        Order order = getById(orderId);
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作");
        }
        lambdaUpdate().eq(Order::getId, orderId)
                .set(Order::getStatus, "CANCELLED")
                .update();
    }

    public void updateStatus(Long orderId, String status) {
        lambdaUpdate().eq(Order::getId, orderId)
                .set(Order::getStatus, status)
                .update();
    }
}