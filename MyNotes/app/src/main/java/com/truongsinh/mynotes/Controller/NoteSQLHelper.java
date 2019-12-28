package com.truongsinh.mynotes.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteSQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mynote.db";
    public static final String TABLE_NAME ="notes";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_NOTE ="note";
    public static final String COLUMN_DATE="date";
    private static final int DATABASE_VERSION=2;
    private final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + "( "+
                                                            COLUMN_ID+" INTEGER PRIMARY KEY autoincrement,"+
                                                            COLUMN_NOTE+" text not null,"+
                                                            COLUMN_DATE+" text not null);";
    public NoteSQLHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMydata(db,0,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMydata(db,oldVersion,newVersion);
    }
    private void updateMydata(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < 1)
        {
            db.execSQL(CREATE_DATABASE);
        }
        if(oldVersion < newVersion)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NOTE,"Fighting");
            db.update(TABLE_NAME,contentValues,COLUMN_NOTE+"=?",new String[]{"Watching film at"});
        }
    }
}
