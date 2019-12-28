package com.truongsinh.toolbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.truongsinh.model.DataPizzas;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_ID ="123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ImageView imageView = findViewById(R.id.image_detail);
        TextView textView = findViewById(R.id.txt_detail);
        int id = getIntent().getIntExtra(EXTRA_ID,0);
        imageView.setImageResource(DataPizzas.dataPizzas[id].getId());
        textView.setText(DataPizzas.dataPizzas[id].getCaption());
    }
}
