package com.truongsinh.starbuzzcoffee;

import java.util.ArrayList;

public class Drinks {
    private String name;
    private int image;
    private String mota;
    public static Drinks drinks[] = {new Drinks("Latte",R.drawable.latte,"ngon"),
    new Drinks("Cappuccino",R.drawable.cappuccino,"ngon vcl ra"),
            new Drinks("Filter",R.drawable.filter,"mắc ngon")
    };
    public Drinks(String name, int image, String mota) {
        this.name = name;
        this.image = image;
        this.mota = mota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
