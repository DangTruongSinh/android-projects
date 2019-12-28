package com.truongsinh.foodyapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.truongsinh.foodyapp.R;
import com.truongsinh.foodyapp.adapter.AdapterViewPager;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    FirebaseAuth firebaseAuth;
    ViewPager viewPager;
    RadioButton rdPlace;
    RadioButton rdFood;
    RadioGroup rdGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu_activity);
        viewPager = findViewById(R.id.view_papger);
        AdapterViewPager adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        rdPlace = findViewById(R.id.rdb_place);
        rdFood = findViewById(R.id.rdb_food);
        rdGroup = findViewById(R.id.rd_group);
        rdGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if(i == 0)
            rdPlace.setChecked(true);
        else
            rdFood.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId ==  R.id.rdb_place)
            viewPager.setCurrentItem(0);
        else
            viewPager.setCurrentItem(1);
    }
}
