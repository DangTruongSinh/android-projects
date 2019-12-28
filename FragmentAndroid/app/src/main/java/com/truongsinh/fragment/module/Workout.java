package com.truongsinh.fragment.module;

public class Workout {
    private String name;
    private String describe;
    public static Workout arr[] = {new Workout("Lâu nhà","Lâu nhà cẩn thận"),
    new Workout("Rửa chén","Rửa chén cẩn thận"),new Workout("Đi chơi","Đi chơi khỏi về")
    };

    public Workout(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
