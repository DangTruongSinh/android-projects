package com.truongsinh.fragment;


import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.os.Handler;
import android.service.vr.VrListenerService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private  int sec =0;
    private boolean ruuning;
    private boolean wasRunning;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState!=null)
        {
            sec = savedInstanceState.getInt("sec",0);
            ruuning = savedInstanceState.getBoolean("running");
        }
        View layout =     inflater.inflate(R.layout.fragment_test, container, false);
        runTimer(layout);
        handleButton(layout);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
           wasRunning = ruuning;
    ruuning = false;
        Toast.makeText(getContext(), "onPause", Toast.LENGTH_SHORT).show();
}

    @Override
    public void onResume() {
        super.onResume();
        if(wasRunning==true)
            ruuning = wasRunning;
    }

    private void handleButton(View layout) {
        Button start = layout.findViewById(R.id.button);
        Button stop = layout.findViewById(R.id.button2);
        Button reStart =layout.findViewById(R.id.button3);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reStart.setOnClickListener(this);
    }

    private void setStart()
    {
        ruuning =true;
    }
    private void setStop()
    {
        ruuning = false;

    }
    private void setRestart()
    {
        ruuning = false;
        sec =0;
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(), "onStop", Toast.LENGTH_SHORT).show();
    }

    private void runTimer(View layout) {
        final TextView txt = layout.findViewById(R.id.txtKetQua);
        final Handler handler =  new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int giay=sec%60;
                int phut = (sec/60)%60;
                int gio = (sec/3600)%24;
                String s = String.format(Locale.getDefault(),"%d:%02d:%02d",gio,phut,giay);
                txt.setText(s);
                if(ruuning == true)
                    sec ++;
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running",wasRunning);
        outState.putInt("sec",sec);
        Toast.makeText(getContext(), "onSave", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                setStart();
                break;
            case R.id.button2:
                setStop();
                break;
            case R.id.button3:
                setRestart();
                break;
        }
    }
}
