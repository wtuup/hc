package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/product/{productId}")
    public Result<List<ProductReview>> list(@PathVariable Long productId) {
        return Result.ok(reviewService.getByProductId(productId));
    }

    @PostMapping
    public Result<Void> add(@AuthenticationPrincipal UserDetails ud, @RequestBody ProductReview review) {
        reviewService.addReview(review, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }
}