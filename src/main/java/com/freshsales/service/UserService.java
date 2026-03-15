package com.freshsales.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freshsales.entity.User;
import com.freshsales.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    public void register(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        save(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        lambdaUpdate().eq(User::getId, userId)
                .set(User::getPassword, passwordEncoder.encode(newPassword))
                .update();
    }

    public List<User> getAllUsers() {
        return list();
    }
}