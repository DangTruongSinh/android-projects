package com.truongsinh.testthread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread  thread  = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {Log.i("tag","run!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }}
        });
        thread.start();
    }
}
