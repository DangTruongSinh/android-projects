package com.truongsinh.freekingmath;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtCore;
    TextView txtOperator;
    TextView txtResult;
    ImageView imgCorrect;
    ImageView imgWrong;
    Timer timer;
    TimerTask timerTask;
    ConstraintLayout Layout;
    private int TIMER_GAME = 1200;
    private  int core =0;
    private int rand;
    ModelLevel generateLevel;
    private String[] arrColor = {	"#FA8072", "#DC143C", "#B22222","#FF4500", "#FF8C00", "#32CD32", "#328B22", "#008000",
            "#9ACD32", "#8FBC8F", "#556B2F", "#20B2AA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //remove notifycation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //find View
        txtCore = findViewById(R.id.txtCore);
        txtOperator = findViewById(R.id.textView3);
        txtResult = findViewById(R.id.textView4);
        imgCorrect = findViewById(R.id.imgCorrect);
        imgWrong = findViewById(R.id.imgWrong);
        Layout = findViewById(R.id.background1);
        //
        imgCorrect.setOnClickListener(this);
        imgWrong.setOnClickListener(this);
        //

        generateLevel = GenerateLevel.generateLevel(core);
        disPlayOnScreen(generateLevel);
        // create timer
        createTimer();
    }

    private void createTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showGameOver();
                    }
                });
            }
        };
        timer.schedule(timerTask,TIMER_GAME);
    }

    private void showGameOver() {
        imgCorrect.setVisibility(View.INVISIBLE);
        imgWrong.setVisibility(View.INVISIBLE);
        // cancel timer
        cancelTimer();

        new AlertDialog.Builder(this).setTitle("Game Over")
                        .setMessage("Your Core:"+core)
                        .setPositiveButton(R.string.replay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                imgCorrect.setVisibility(View.VISIBLE);
                                imgWrong.setVisibility(View.VISIBLE);
                                core = 0;
                                txtResult.setText(core+"");
                                nextLevel(core);
                            }
                        })
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).show();

    }

    private void nextLevel(int core) {
        // cancel timer
        cancelTimer();
        //
        generateLevel = GenerateLevel.generateLevel(core);
        //
        createTimer();
        disPlayOnScreen(generateLevel);

    }

    private void cancelTimer() {
        timer.cancel();
        timerTask.cancel();
    }

    private void disPlayOnScreen(ModelLevel generateLevel) {
        rand = new Random().nextInt(arrColor.length);
        Layout.setBackgroundColor(Color.parseColor(arrColor[rand]));
        txtCore.setText(core+"");
        txtOperator.setText(generateLevel.operator_txt);
        txtResult.setText(generateLevel.resul_txt);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.imgCorrect)
        {
            if(generateLevel.correctWrong)
            {
                core += 1;
                nextLevel(core);
            }
            else
            {
                showGameOver();
            }
        }
        else
        {
            if(!generateLevel.correctWrong)
            {
                core += 1;
                nextLevel(core);
            }
            else
            {
                showGameOver();
            }
        }
    }
}
