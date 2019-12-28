package com.truongsinh.fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OrderActivity extends AppCompatActivity {
    public static String EXTRA="123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        OrderFragment orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentById(R.id.order_fragment);
        int id = getIntent().getIntExtra(EXTRA,0);
        orderFragment.setWorkId(id);
    }
}
