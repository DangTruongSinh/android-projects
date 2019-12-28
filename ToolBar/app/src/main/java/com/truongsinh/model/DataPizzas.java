package com.truongsinh.model;

import com.truongsinh.toolbar.R;

public class DataPizzas {
    private int id;
    private String caption;
    public static DataPizzas dataPizzas[] = {new DataPizzas(R.drawable.diavolo,"Diavolo"),
    new DataPizzas(R.drawable.funghi,"Funghi")};
    public DataPizzas(int id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
