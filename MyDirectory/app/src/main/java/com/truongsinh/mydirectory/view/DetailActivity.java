package com.truongsinh.mydirectory.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.truongsinh.mydirectory.R;
import com.truongsinh.mydirectory.Util;
import com.truongsinh.mydirectory.model.ModelItem;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA ="123";
    ImageView imageView;
    TextView txtTitle;
    TextView txtContent;
    ModelItem item ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // find view
        imageView = findViewById(R.id.imageDetail);
        txtTitle = findViewById(R.id.txtTitleDetail);
        txtContent = findViewById(R.id.txtContentDetail);
        // get Data
        getData();
    }

    private void getData() {
        int position = getIntent().getIntExtra(EXTRA,0);
        item = MainActivity.arr.get(position);
     //   Bitmap bitmap = Util.readBitmap(item.imgHinh,this);
        InputStream inputStream = null;
        Bitmap bitmap;
        try {
           // getAnh();

            inputStream = getContentResolver().openInputStream(NewNoteActivity.DATA);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




        txtTitle.setText(item.title);
        txtContent.setText(item.content);
    }

    private void getAnh() {
        Intent intent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "chon"), 767);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete)
        {
            xoaNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void xoaNote() {
        MainActivity.soureAdapter.deleteNote(item);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
            ContentResolver resolver = getContentResolver();
                resolver.takePersistableUriPermission(NewNoteActivity.DATA, takeFlags);

        }
    }
}
