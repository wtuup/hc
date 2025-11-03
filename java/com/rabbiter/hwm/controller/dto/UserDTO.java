package com.rabbiter.hwm.controller.dto;

import com.rabbiter.hwm.entity.Menu;

import java.util.List;

/**
 * 接受前端登录请求的参数
 */
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String nickname, String avatarUrl, String token, String role, List<Menu> menus) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.token = token;
        this.role = role;
        this.menus = menus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", menus=" + menus +
                '}';
    }
}
