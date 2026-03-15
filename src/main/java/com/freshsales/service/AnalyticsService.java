package com.freshsales.service;

import com.freshsales.entity.Order;
import com.freshsales.entity.Product;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public AnalyticsService(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    public long getTotalUsers() {
        return userService.count();
    }

    public BigDecimal getTotalSales() {
        return orderService.list().stream()
                .filter(o -> !"CANCELLED".equals(o.getStatus()))
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Product> getSalesRanking(int top) {
        return productService.getTopBySales(top);
    }

    public List<Map<String, Object>> getSalesTrend(int days) {
        LocalDateTime start = LocalDate.now().minusDays(days - 1).atStartOfDay();
        List<Order> orders = orderService.lambdaQuery()
                .ge(Order::getCreateTime, start)
                .ne(Order::getStatus, "CANCELLED")
                .list();

        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (int i = days - 1; i >= 0; i--) {
            map.put(LocalDate.now().minusDays(i).toString(), BigDecimal.ZERO);
        }
        for (Order o : orders) {
            map.merge(o.getCreateTime().toLocalDate().toString(), o.getTotalAmount(), BigDecimal::add);
        }

        return map.entrySet().stream()
                .map(e -> {
                    Map<String, Object> r = new LinkedHashMap<>();
                    r.put("date", e.getKey());
                    r.put("sales", e.getValue());
                    return r;
                })
                .collect(Collectors.toList());
    }
}