package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/after-sales")
public class AfterSaleController {
    private final AfterSaleService afterSaleService;
    private final UserService userService;

    public AfterSaleController(AfterSaleService afterSaleService, UserService userService) {
        this.afterSaleService = afterSaleService;
        this.userService = userService;
    }

    @PostMapping
    public Result<Void> apply(@AuthenticationPrincipal UserDetails ud, @RequestBody AfterSale afterSale) {
        afterSaleService.apply(afterSale, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }

    @GetMapping("/my")
    public Result<List<AfterSale>> my(@AuthenticationPrincipal UserDetails ud) {
        return Result.ok(afterSaleService.getByUserId(userService.findByUsername(ud.getUsername()).getId()));
    }
}