package com.truongsinh.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements MainFragment.Listener1 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    public void setWorkout(int id) {
        View frameLayout = findViewById(R.id.frame_layout);
        if(frameLayout == null)
        {
            Intent intent = new Intent(MainActivity.this,OrderActivity.class);
            intent.putExtra(OrderActivity.EXTRA,id);
            startActivity(intent);
        }
        else
        {
            OrderFragment orderFragment = new OrderFragment();
            orderFragment.setWorkId(id);
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,orderFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}
