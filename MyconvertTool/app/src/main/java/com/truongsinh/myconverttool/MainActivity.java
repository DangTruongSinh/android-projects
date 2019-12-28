package com.truongsinh.myconverttool;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout distance,weight,speed,templeture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find view
        distance = findViewById(R.id.distance);
        weight = findViewById(R.id.weight);
        templeture = findViewById(R.id.templeture);
        speed = findViewById(R.id.speed);
        // action
        distance.setOnClickListener(this);
        weight.setOnClickListener(this);
        speed.setOnClickListener(this);
        templeture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int ma=0;
        switch (id)
        {
            case R.id.distance:
                ma =0;
                break;
            case R.id.weight:
                ma =1;
                break;
            case R.id.templeture:
                ma =2;
                break;
            case R.id.speed:
                ma = 3;
                break;
        }
        Intent intent = new Intent(MainActivity.this,ConvertActivity.class);
        intent.putExtra(ConvertActivity.EXTRA,ma);
        startActivity(intent);
    }
}
