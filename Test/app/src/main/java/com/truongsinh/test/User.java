package com.truongsinh.test;

public class User {
    String hoten;
    String tuoi;
    boolean gioitinh;

    public User() {
    }

    public User(String hoten, String tuoi, boolean gioitinh) {
        this.hoten = hoten;
        this.tuoi = tuoi;
        this.gioitinh = gioitinh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }
}
