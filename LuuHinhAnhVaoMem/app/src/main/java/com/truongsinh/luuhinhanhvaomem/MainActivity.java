package com.truongsinh.luuhinhanhvaomem;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    int i =0;
    final Handler handler1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updatetime();

        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                handler1.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        //checkAndRequestPermissions();


    }

    private void updatetime() {
            if(i < 3) {
                i++;
                Log.i("Tag", i + "");
                Thread thread = new Thread(runnable);
                thread.start();
            }
    }

    public void handle(View view) {
        int id = view.getId();
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.starbuzz_logo);
        if(id == R.id.button)
        {
            boolean x = WRImageToMem.writeImageToSD("imageToSdCard1.png",image,this);
            if(x) Toast.makeText(this, "Completly", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.button2)
        {
            boolean x = WRImageToMem.wireImageToInternalMem("imageToSdCard1.png",image,this);
            if(x) Toast.makeText(this, "Completly", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
        else if( id == R.id.button3)
        {
            Bitmap bitmap = WRImageToMem.readBitmap("imageToSdCard1.png",this);
            imageView.setImageBitmap(bitmap);
        }
        else if(id == R.id.button4)
        {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        else
        {
            WRImageToMem.deleteImage("imageToSdCard1.png",this);
        }


    }
    // Yeu cau permission, neu ko yeu cau thi phai vao setting permission cho ung dung
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
            };
        List<String> listPermissionsNeeded = new ArrayList<>();
            for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
}
}
