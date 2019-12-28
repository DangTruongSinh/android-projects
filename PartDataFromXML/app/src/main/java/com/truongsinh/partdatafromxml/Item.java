package com.truongsinh.partdatafromxml;

public class Item {
    String img_hinh;
    String noidung;
    String title;

    public Item(String img_hinh, String noidung, String title) {
        this.img_hinh = img_hinh;
        this.noidung = noidung;
        this.title = title;
    }
    public Item() {
        this.img_hinh = "ko co noi dung";
        this.noidung = "ko co noi dung";
        this.title = "ko co noi dung";

    }
    public String getImg_hinh() {
        return img_hinh;
    }

    public void setImg_hinh(String img_hinh) {
        this.img_hinh = img_hinh;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
