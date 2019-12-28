package com.truongsinh.mynotes.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.truongsinh.mynotes.Model.Note;

import java.util.ArrayList;

public class NoteDatasoure {
    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;
    Context context;

    public NoteDatasoure(Context context)
    {
        this.context = context;
        this.sqLiteOpenHelper = new NoteSQLHelper(context);
    }
    public void open() throws SQLException
    {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close() throws SQLException
    {
        sqLiteOpenHelper.close();
    }

    public void addNote(Note note)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteSQLHelper.COLUMN_NOTE, note.content);
        contentValues.put(NoteSQLHelper.COLUMN_DATE,note.dateTime);
        sqLiteDatabase.insert(NoteSQLHelper.TABLE_NAME,null,contentValues);
        Toast.makeText(context, "add new node success", Toast.LENGTH_SHORT).show();
    }
    public void deleteNote(Note note)
    {
        sqLiteDatabase.delete(NoteSQLHelper.TABLE_NAME,NoteSQLHelper.COLUMN_ID+"="+note._id,null);
        Toast.makeText(context, "delete node success", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Note> getAllNote()
    {
        ArrayList<Note> arrayList = new ArrayList<Note>();
        Cursor cursor = sqLiteDatabase.query(NoteSQLHelper.TABLE_NAME,null,null,null
            ,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Note x = getNote(cursor);
                arrayList.add(x);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
            return null;
        return arrayList;
    }

    private Note getNote(Cursor cursor) {
        Note x = new Note();
        x._id = cursor.getInt(0);
        x.content = cursor.getString(1);
        x.dateTime = cursor.getString(2);
        return x;
    }

}