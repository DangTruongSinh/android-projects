package com.truongsinh.mydirectory.controler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class NoteDataOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydir.db";
    public static final String TABLE_NAME ="dir";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_NOTE ="title";
    public static final String COLUMN_CONTENT="content";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_IMAGE="image";
    public static final String COLUMN_MAMAU="mamau";
    private static final int DATABASE_VERSION=2;
    private final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + "( "+
            COLUMN_ID+" INTEGER PRIMARY KEY autoincrement,"+
            COLUMN_DATE + " text not null,"+
            COLUMN_NOTE+" text not null,"+
            COLUMN_CONTENT+" text not null," +
            COLUMN_MAMAU + " text," +
            COLUMN_IMAGE + " text);";


    public NoteDataOpenHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
