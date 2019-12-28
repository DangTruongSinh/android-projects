package com.truongsinh.kiemtrapermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView ;
    int requestCode =123;
    static final String PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.requestCode && resultCode == RESULT_OK && data!= null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }


    protected void onStart() {
        super.onStart();

        boolean hasMicrophone = isPermissionGranted(PERMISSION);
        if (hasMicrophone) {
            // Tiến hành ghi âm
        } else {
            ActivityCompat.requestPermissions(this, new String[] {PERMISSION}, 123);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123 && permissions[0].equals(PERMISSION)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Tiến hành ghi âm
                Toast.makeText(this, "Da cap quyen!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Người dùng không thèm cấp quyền", Toast.LENGTH_LONG).show();
            }
        }
    }
    boolean isPermissionGranted(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        if        (result == PackageManager.PERMISSION_GRANTED) return true;
        else if (result == PackageManager.PERMISSION_DENIED)    return false;
        else throw new IllegalStateException("Cannot check permission " + permission);
    }
    public void handle(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,requestCode);
    }
}
