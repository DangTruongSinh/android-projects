package com.truongsinh.fragmentbasic;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    static myAsyntash myAsyntash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();

        //OneFragment oneFragment = (OneFragment) fragmentManager.findFragmentByTag("onefragment");
        OneFragment oneFragment = (OneFragment) fragmentManager.findFragmentById(R.id.frame_layout);
        if(oneFragment ==null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.frame_layout, new OneFragment());
            fragmentTransaction.commitNow();
           // Log.d("tag","chay ne");
        }
        myAsyntash = new myAsyntash();
        try {
            int x = (int) myAsyntash.execute().get();
            Log.d("tag",x+"");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        class myAsyntash extends AsyncTask
        {
            int x =1;
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d("tag","chay ne");
                return x;
            }
        }
}
