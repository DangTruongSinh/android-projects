package com.truongsinh.testgetimagecontentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE =123;
    Bitmap bitmap;
    ImageView imageView, imageView2;
    Uri filename;
    TextView txtView;
    Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.txtText);
        imageView2 = findViewById(R.id.imageView2);
    }

    private void addImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent intent1 = Intent.createChooser(intent,"Please choose");
        startActivityForResult(intent1,REQUEST_CODE);
    }


    public void handle(View view) {
        addImage();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {

            try {
                 filename= data.getData();
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Log.i("my_uri",filename.toString());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //public static final String MY_FOLDER = "/DemoReadWriteImage/";
    public static Bitmap readBitmap(String filename,Context context)
    {
        Bitmap bitmap=null;
        String fullpath =filename;
        try
        {
            bitmap = BitmapFactory.decodeFile(fullpath);
        }
        catch (Exception ex)
        {
            Log.d("mytag","No image in SDcard");
        }
        // read image from internal Memory
        File file = new File(filename);
        file.getAbsoluteFile();
        try {

            FileInputStream fIn =new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fIn);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            fIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void getImage(View view) {


      /*  try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                    Uri.parse("content://com.android.providers.media.documents/document/image%3A22"));
            imageView2.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "loi", Toast.LENGTH_SHORT).show();
        }*/

    }
}
