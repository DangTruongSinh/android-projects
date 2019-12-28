package com.example.doan2;

public class ThietBi {
    private Long id;
    private String ten;
    private Integer giatri;
    private Integer thoigiandoc;
    private Boolean trangthai;
    private Boolean chedo;
    private String thoigianmo;
    private String thoigiantat;

    public ThietBi(Long id, String ten, Integer giatri, Boolean trangthai, Boolean chedo, String thoigianmo, String thoigiantat) {
        this.id = id;
        this.ten = ten;
        this.giatri = giatri;
        this.trangthai = trangthai;
        this.chedo = chedo;
        this.thoigianmo = thoigianmo;
        this.thoigiantat = thoigiantat;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }
    public Integer getGiatri() {
        return giatri;
    }
    public void setGiatri(Integer giatri) {
        this.giatri = giatri;
    }
    public Integer getThoigiandoc() {
        return thoigiandoc;
    }
    public void setThoigiandoc(Integer thoigiandoc) {
        this.thoigiandoc = thoigiandoc;
    }
    public Boolean getTrangthai() {
        return trangthai;
    }
    public void setTrangthai(Boolean trangthai) {
        this.trangthai = trangthai;
    }
    public Boolean getChedo() {
        return chedo;
    }
    public void setChedo(Boolean chedo) {
        this.chedo = chedo;
    }

    public String getThoigianmo() {
        return thoigianmo;
    }
    public void setThoigianmo(String thoigianmo) {
        this.thoigianmo = thoigianmo;
    }
    public String getThoigiantat() {
        return thoigiantat;
    }
    public void setThoigiantat(String thoigiantat) {
        this.thoigiantat = thoigiantat;
    }
    public ThietBi() {
    }


}
