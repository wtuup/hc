package com.freshsales.controller;

import com.freshsales.entity.User;
import com.freshsales.service.UserService;
import com.freshsales.util.JwtUtil;
import com.freshsales.util.Result;
import com.freshsales.vo.LoginVO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, UserService userService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginVO vo) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(vo.getUsername(), vo.getPassword())
            );
            String token = jwtUtil.generateToken(vo.getUsername());
            User user = userService.findByUsername(vo.getUsername());
            return Result.ok(Map.of(
                "token", token,
                "role", user.getRole(),
                "userId", user.getId()
            ));
        } catch (BadCredentialsException e) {
            return Result.error(401, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        userService.register(user);
        return Result.ok("注册成功");
    }
}