package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping
    public Result<List<Product>> list(@AuthenticationPrincipal UserDetails ud) {
        return Result.ok(favoriteService.getFavoriteProducts(userService.findByUsername(ud.getUsername()).getId()));
    }

    @PostMapping
    public Result<Void> add(@AuthenticationPrincipal UserDetails ud, @RequestBody Map<String, Long> body) {
        favoriteService.addFavorite(userService.findByUsername(ud.getUsername()).getId(), body.get("productId"));
        return Result.ok();
    }

    @DeleteMapping("/{productId}")
    public Result<Void> remove(@AuthenticationPrincipal UserDetails ud, @PathVariable Long productId) {
        favoriteService.removeFavorite(userService.findByUsername(ud.getUsername()).getId(), productId);
        return Result.ok();
    }

    @GetMapping("/check/{productId}")
    public Result<Boolean> check(@AuthenticationPrincipal UserDetails ud, @PathVariable Long productId) {
        return Result.ok(favoriteService.isFavorited(userService.findByUsername(ud.getUsername()).getId(), productId));
    }
}