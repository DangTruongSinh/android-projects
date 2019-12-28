package com.example.doan2;

public class TaiKhoan {
    private String username;
    private String password;

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

    public TaiKhoan() {
    }

    public TaiKhoan(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
