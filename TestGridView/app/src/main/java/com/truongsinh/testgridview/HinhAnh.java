package com.truongsinh.testgridview;

public class HinhAnh {
    int imgHinh;
    boolean block;

    public HinhAnh(int imgHinh, boolean block) {
        this.imgHinh = imgHinh;
        this.block = block;
    }

    public int getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(int imgHinh) {
        this.imgHinh = imgHinh;
    }

    public boolean isBlock() {
        return block;

    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
