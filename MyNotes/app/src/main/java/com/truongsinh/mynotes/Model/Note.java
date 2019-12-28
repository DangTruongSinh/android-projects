package com.truongsinh.mynotes.Model;

public class Note {
    public int _id;
    public String content;
    public String dateTime;

    public Note()
    {

    }
    public Note( String content, String dateTime) {

        this.content = content;
        this.dateTime = dateTime;
    }
}
