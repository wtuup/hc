package com.freshsales.controller;

import com.freshsales.entity.User;
import com.freshsales.service.UserService;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public Result<User> info(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByUsername(ud.getUsername());
        user.setPassword(null);
        return Result.ok(user);
    }

    @PutMapping("/info")
    public Result<Void> updateInfo(@AuthenticationPrincipal UserDetails ud, @RequestBody User form) {
        User user = userService.findByUsername(ud.getUsername());
        if (form.getNickname() != null) user.setNickname(form.getNickname());
        if (form.getPhone() != null) user.setPhone(form.getPhone());
        userService.updateById(user);
        return Result.ok();
    }

    @PutMapping("/password")
    public Result<Void> changePassword(@AuthenticationPrincipal UserDetails ud, @RequestBody Map<String, String> body) {
        User user = userService.findByUsername(ud.getUsername());
        userService.changePassword(user.getId(), body.get("oldPassword"), body.get("newPassword"));
        return Result.ok();
    }
}