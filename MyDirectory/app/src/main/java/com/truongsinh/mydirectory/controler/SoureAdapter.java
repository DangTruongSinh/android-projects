package com.truongsinh.mydirectory.controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.truongsinh.mydirectory.model.ModelItem;

import java.util.ArrayList;

public class SoureAdapter {
    private SQLiteDatabase db;
    private Context context;
    private SQLiteOpenHelper sqLiteOpenHelper;
    public SoureAdapter(Context context)
    {
        this.context = context;
        this.sqLiteOpenHelper = new NoteDataOpenHelper(context);
    }
    public void open() throws SQLException
    {
        db = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close() throws SQLException
    {
        sqLiteOpenHelper.close();
    }
    public void addNote(ModelItem note)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDataOpenHelper.COLUMN_DATE, note.tgian);
        contentValues.put(NoteDataOpenHelper.COLUMN_NOTE,note.title);
        contentValues.put(NoteDataOpenHelper.COLUMN_CONTENT,note.content);
        contentValues.put(NoteDataOpenHelper.COLUMN_IMAGE,note.imgHinh);
        contentValues.put(NoteDataOpenHelper.COLUMN_MAMAU,note.maMau);
        db.insert(NoteDataOpenHelper.TABLE_NAME,null,contentValues);

    }
    public void deleteNote(ModelItem note)
    {
        db.delete(NoteDataOpenHelper.TABLE_NAME,NoteDataOpenHelper.COLUMN_ID+"="+note._id,null);
        Toast.makeText(context, "delete node success", Toast.LENGTH_SHORT).show();
    }
    public ArrayList<ModelItem> getAllNote()
    {
        ArrayList<ModelItem> arrayList = new ArrayList<ModelItem>();
        Cursor cursor = db.query(NoteDataOpenHelper.TABLE_NAME,null,null,null
                ,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ModelItem x = getNote(cursor);
                arrayList.add(x);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
            return null;
        return arrayList;
    }

    private ModelItem getNote(Cursor cursor) {
        ModelItem x = new ModelItem();
        x._id = cursor.getInt(0);
        x.tgian = cursor.getString(1);
        x.title = cursor.getString(2);
        x.content = cursor.getString(3);
        x.maMau = cursor.getString(4);
        x.imgHinh = cursor.getString(5);
        return x;
    }
}
