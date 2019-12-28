package com.truongsinh.mynotes.View;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.truongsinh.mynotes.Controller.AdapterNote;
import com.truongsinh.mynotes.Controller.NoteDatasoure;
import com.truongsinh.mynotes.Model.Note;
import com.truongsinh.mynotes.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtInput;
    Button btnADD;
    ListView listComment;
    TextView txtComment;
    public  NoteDatasoure noteDatasoure;
    private ArrayList<Note> arrayList = new ArrayList<>();
    private AdapterNote adapterNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find id
        findId();
        noteDatasoure = new NoteDatasoure(this);
        noteDatasoure.open();
        btnADD.setOnClickListener(this);
        updateView();
    }

    private void updateView() {
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(arrayList.size()>0)
                {
                    txtComment.setVisibility(View.INVISIBLE);
                    listComment.setVisibility(View.VISIBLE);
                    adapterNote = new AdapterNote(MainActivity.this,arrayList);
                    listComment.setAdapter(adapterNote);

                }
                else
                {
                    txtComment.setVisibility(View.VISIBLE);
                    listComment.setVisibility(View.INVISIBLE);
                }
            }
        };
      Thread thread = new Thread()
      {
          @Override
          public void run() {
              super.run();
              arrayList =  noteDatasoure.getAllNote();
              handler.sendMessage(new Message());
          }
      }   ;
      thread.start();


    }

    private void findId() {


        edtInput = findViewById(R.id.editText);
        btnADD = findViewById(R.id.button);
        listComment = findViewById(R.id.list);
        txtComment = findViewById(R.id.textView);
        adapterNote = new AdapterNote(this,arrayList);
        listComment.setAdapter(adapterNote);
    }

    @Override
    public void onClick(View v) {
        String content = edtInput.getText().toString().trim();
        if(content.length()>0)
        // get time from system
        {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMM yyyy HH mm");
            String dateTime = simpleDateFormat.format(date);
            Note note = new Note(content,dateTime);
            noteDatasoure.addNote(note);
            updateView();
            edtInput.setText("");
        }
    }

    public void deleteNote(Note note1)
    {
        noteDatasoure.deleteNote(note1);
        updateView();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteDatasoure.close();
    }
}
