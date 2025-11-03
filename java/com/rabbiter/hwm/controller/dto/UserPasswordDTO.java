package com.rabbiter.hwm.controller.dto;

public class UserPasswordDTO {
    private String username;
    private String phone;
    private String password;
    private String newPassword;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO(String username, String phone, String password, String newPassword) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
