package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;
    private final AfterSaleService afterSaleService;
    private final LogisticsService logisticsService;
    private final AnalyticsService analyticsService;
    private final CategoryService categoryService;

    public AdminController(UserService userService, OrderService orderService,
                           AfterSaleService afterSaleService, LogisticsService logisticsService,
                           AnalyticsService analyticsService, CategoryService categoryService) {
        this.userService = userService;
        this.orderService = orderService;
        this.afterSaleService = afterSaleService;
        this.logisticsService = logisticsService;
        this.analyticsService = analyticsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/users")
    public Result<List<User>> users() {
        return Result.ok(userService.getAllUsers());
    }

    @GetMapping("/orders")
    public Result<List<Order>> orders(@RequestParam(required = false) String status,
                                       @RequestParam(required = false) Long userId) {
        return Result.ok(orderService.getAllOrders(status, userId));
    }

    @PutMapping("/orders/{id}/status")
    public Result<Void> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return Result.ok();
    }

    @PostMapping("/logistics")
    public Result<Void> ship(@RequestBody Map<String, String> body) {
        Long orderId = Long.valueOf(body.get("orderId"));
        logisticsService.shipOrder(orderId, body.get("company"), body.get("trackingNumber"));
        orderService.updateStatus(orderId, "SHIPPED");
        return Result.ok();
    }

    @GetMapping("/after-sales")
    public Result<List<AfterSale>> afterSales() {
        return Result.ok(afterSaleService.getAll());
    }

    @PutMapping("/after-sales/{id}/approve")
    public Result<Void> approve(@PathVariable Long id) {
        afterSaleService.approve(id);
        return Result.ok();
    }

    @PutMapping("/after-sales/{id}/reject")
    public Result<Void> reject(@PathVariable Long id) {
        afterSaleService.reject(id);
        return Result.ok();
    }

    @GetMapping("/categories")
    public Result<List<Category>> categories() {
        return Result.ok(categoryService.list());
    }

    @PostMapping("/categories")
    public Result<Void> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok();
    }

    @DeleteMapping("/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok();
    }

    @GetMapping("/analytics/total-users")
    public Result<Long> totalUsers() {
        return Result.ok(analyticsService.getTotalUsers());
    }

    @GetMapping("/analytics/total-sales")
    public Result<?> totalSales() {
        return Result.ok(analyticsService.getTotalSales());
    }

    @GetMapping("/analytics/sales-ranking")
    public Result<List<Product>> salesRanking(@RequestParam(defaultValue = "10") int top) {
        return Result.ok(analyticsService.getSalesRanking(top));
    }

    @GetMapping("/analytics/sales-trend")
    public Result<?> salesTrend(@RequestParam(defaultValue = "30") int days) {
        return Result.ok(analyticsService.getSalesTrend(days));
    }
}