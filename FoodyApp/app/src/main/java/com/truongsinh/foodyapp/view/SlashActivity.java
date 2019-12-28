package com.truongsinh.foodyapp.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.truongsinh.foodyapp.R;

public class SlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.truongsinh.foodyapp",0);
            TextView txtVersion = findViewById(R.id.txtVersion);
            txtVersion.setText("Version "+ packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SlashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
