package com.truongsinh.testthread;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);

    }

    public void handle(View view) {
        int id = view.getId();
        Intent intent = new Intent(MainActivity.this,TestService.class);
        switch (id)
        {
            case R.id.startService:
                String s = editText.getText().toString();
                intent.putExtra("result",s);
                startService(intent);

                break;
            case R.id.stopService:
                stopService(intent);
        }


    }


}
