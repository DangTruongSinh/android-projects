package com.truongsinh.studyenglish;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
    ViewPager viewPager;
    public static final String EXTRA ="possition";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewPager = findViewById(R.id.viewPager);
        AdapterDetail adapterDetail = new AdapterDetail(this,MainActivity.arrayList);
        viewPager.setAdapter(adapterDetail);
        // get intent
        int id = getIntent().getIntExtra(EXTRA,0);
        viewPager.setCurrentItem(id);
    }
}
