package com.truongsinh.sqlite.SQLiteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseNhanVien extends SQLiteOpenHelper {
    public static final String databaseName = "BangNhanVien";
    public static final int version = 2;
    public static final String table = "NhanVien";
    public static final String ID_NHANVIEN = "_id";
    public static final String TEN_NHANVIEN = "TenNhanVien";
    public DataBaseNhanVien( Context context,   SQLiteDatabase.CursorFactory factory) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + table + "("+ID_NHANVIEN
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TEN_NHANVIEN+" TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 2)
            db.execSQL("delete from "+table);
        String createTable = "CREATE TABLE " + table + "("+ID_NHANVIEN
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TEN_NHANVIEN+" TEXT)";
        db.execSQL(createTable);
    }
}
