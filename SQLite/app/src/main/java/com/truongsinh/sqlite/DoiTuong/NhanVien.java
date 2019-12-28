package com.truongsinh.sqlite.DoiTuong;

public class NhanVien {
    private String name;
    private int _id;

    public NhanVien(String name, int _id) {
        this.name = name;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
