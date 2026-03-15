package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    public CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public Result<List<Map<String, Object>>> list(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByUsername(ud.getUsername());
        List<Cart> carts = cartService.getUserCart(user.getId());
        List<Map<String, Object>> result = new ArrayList<>();
        for (Cart cart : carts) {
            Product p = productService.getById(cart.getProductId());
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", cart.getId());
            item.put("productId", cart.getProductId());
            item.put("quantity", cart.getQuantity());
            item.put("product", p);
            item.put("subtotal", p.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            result.add(item);
        }
        return Result.ok(result);
    }

    @PostMapping("/add")
    public Result<Void> add(@AuthenticationPrincipal UserDetails ud, @RequestBody Map<String, Object> body) {
        User user = userService.findByUsername(ud.getUsername());
        cartService.addToCart(
            user.getId(),
            Long.valueOf(body.get("productId").toString()),
            Integer.valueOf(body.get("quantity").toString())
        );
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<Void> updateQuantity(@AuthenticationPrincipal UserDetails ud,
                                        @PathVariable Long id, @RequestBody Map<String, Integer> body) {
        User user = userService.findByUsername(ud.getUsername());
        cartService.updateQuantity(id, user.getId(), body.get("quantity"));
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> remove(@AuthenticationPrincipal UserDetails ud, @PathVariable Long id) {
        User user = userService.findByUsername(ud.getUsername());
        cartService.removeItem(id, user.getId());
        return Result.ok();
    }
}