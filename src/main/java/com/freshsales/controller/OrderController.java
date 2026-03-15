package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import com.freshsales.vo.OrderSubmitVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public Result<Order> submit(@AuthenticationPrincipal UserDetails ud, @RequestBody OrderSubmitVO vo) {
        User user = userService.findByUsername(ud.getUsername());
        return Result.ok(orderService.submitOrder(user.getId(), vo));
    }

    @GetMapping("/my")
    public Result<List<Order>> myOrders(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByUsername(ud.getUsername());
        return Result.ok(orderService.getUserOrders(user.getId()));
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@AuthenticationPrincipal UserDetails ud, @PathVariable Long id) {
        User user = userService.findByUsername(ud.getUsername());
        orderService.cancelOrder(id, user.getId());
        return Result.ok();
    }
}