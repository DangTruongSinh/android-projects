package com.truongsinh.mydirectory.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.truongsinh.mydirectory.R;
import com.truongsinh.mydirectory.Util;
import com.truongsinh.mydirectory.model.ModelItem;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NewNoteActivity extends AppCompatActivity {
    ImageView imageView;
    EditText edtInputTitle;
    EditText edtInputContent;
    Bitmap bitmap=null;
    public final int REQUEST_CODE = 123;
    public static Uri DATA;
    private String[] arrColor = {"#CDDC39", "#FF3D00", "#FFFF00", "#AEEA00", "#00B8D4", "#311B92", "#FF1744", "#4A148C", "#F44336"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbarNewNote);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.imageView);
        edtInputTitle = findViewById(R.id.edtInputTitle);
        edtInputContent = findViewById(R.id.edtInputContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newnote,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save)
        {
            addNote();
        }
        else
        {
            addImage();
        }
        return super.onOptionsItemSelected(item);

    }

    private void addImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent intent1 = Intent.createChooser(intent,"Please choose");
        startActivityForResult(intent1,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {

            try {
                DATA = data.getData();
                Toast.makeText(this, DATA+"", Toast.LENGTH_LONG).show();
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Toast.makeText(this,data.getData()+"", Toast.LENGTH_LONG).show();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNote() {
        final String title = edtInputTitle.getText().toString().trim();

        if(title.length()>0)
        {


            final ModelItem item = new ModelItem();
            item.title = title;
            saveMaMau(item);
            if(bitmap!=null)
                saveImage(item);
            String content = edtInputContent.getText().toString();
            item.content = content;
            MainActivity.soureAdapter.addNote(item);

        }
        else
        {
            Toast.makeText(this, "Please enter the title!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveMaMau(ModelItem item) {
        Random random = new Random();
        int x = random.nextInt(9);
        item.maMau = arrColor[x];
    }

    private void saveImage(ModelItem item) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMM yyyy HH mm");
        String stringDate = simpleDateFormat.format(date);
        final String filename = Util.formatStringDateToNameImage(stringDate);
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(NewNoteActivity.this, "add new note success", Toast.LENGTH_SHORT).show();
            }
        };
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Util.writeImage(filename, bitmap, NewNoteActivity.this);
                handler.sendMessage(new Message());
            }
        };

        thread.start();
        item.imgHinh = filename;
        item.tgian = stringDate;

    }
}
