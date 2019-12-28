package com.truongsinh.sqlite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.truongsinh.sqlite.DoiTuong.NhanVien;
import com.truongsinh.sqlite.SQLiteHelper.DataBaseNhanVien;

import java.util.ArrayList;

public class NhanVienDAO {
    DataBaseNhanVien dataBaseNhanVien;
    SQLiteDatabase sqLiteDatabase;
    public static final String arrColumn[]={DataBaseNhanVien.ID_NHANVIEN,DataBaseNhanVien.TEN_NHANVIEN};
    public NhanVienDAO(Context context) {
        this.dataBaseNhanVien = new DataBaseNhanVien(context,null);
        sqLiteDatabase = dataBaseNhanVien.getWritableDatabase();
    }
    public void close()
    {
        dataBaseNhanVien.close();
    }
    public boolean themNhanVien(NhanVien nhanVien)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseNhanVien.TEN_NHANVIEN,nhanVien.getName());
        long result = sqLiteDatabase.insert(DataBaseNhanVien.table,null,contentValues);
        if(result!=-1)
            return true;
        return false;
    }
    public void xoaDuLieu(NhanVien nhanVien)
    {
        sqLiteDatabase.delete(DataBaseNhanVien.table,DataBaseNhanVien.ID_NHANVIEN + "="+nhanVien.get_id(),null);

    }
    public ArrayList<NhanVien> getAllData()
    {
        int _id;
        String name;
        ArrayList<NhanVien> arr = new ArrayList<NhanVien>();
        Cursor cursor = sqLiteDatabase.query(DataBaseNhanVien.table,arrColumn,null,
                null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            _id = cursor.getInt(0);
            name = cursor.getString(1);
            NhanVien nhanVien = new NhanVien(name,_id);
            arr.add(nhanVien);
            cursor.moveToNext();
        }
        return arr;
    }
    public void update(NhanVien nhanVien)
    {
       // sqLiteDatabase.update(DataBaseNhanVien.table,)
    }

}
