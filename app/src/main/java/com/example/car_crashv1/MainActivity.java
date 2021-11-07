package com.example.car_crashv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 1000;
    private ImageButton leftBTN,rightBTN;
private Timer timer;
private Timer timer2;
private ImageView stones[][];
private ImageView carView[];
private ImageView hearts[];
private int carPos = 1;
private int life = 2;
private TextView msg;
private Vibrator v;
private int stonePos[][] = {
        {0,0,0},
        {0,0,0},
        {0,0,0},
        {0,0,0}};

    private int changed[][] = {
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        findViews();
        findStoneImgs();
        findCarImgs();
        setBTNs();
        startTicker();



    }

    private void startTicker() {
        timer = new Timer();
        timer2 = new Timer();

        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()-> {
                    int x = (int)(Math.random()*3);
                    Log.d("random number ", "random number " + x);
                    stonePos[0][x] = 1;
                    stones[0][x].setVisibility(View.VISIBLE);
                    changed[0][x] = 1;
                });
            }
        },0,2000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()-> {
                    Log.d("timeTick","Tick"+ Thread.currentThread().getName());
                    resetLastlane();
                    //showpos();
                    for (int i = 0; i < stonePos.length; i++) {
                        for (int j = 0; j < stonePos[i].length; j++) {
                            if (stonePos[i][j] == 1 && changed[i][j] == 0){
                                stonePos[i][j] = 0;
                                Log.d("J pos","J pos" + j);

                                if (i < 3){
                                    stones[i][j].setVisibility(View.INVISIBLE);
                                    stonePos[i+1][j] = 1;
                                    changed[i+1][j] = 1;
                                    Log.d("J pos","J pos" + j);
                                }
                                if (i < 2){
                                    stones[i+1][j].setVisibility(View.VISIBLE);
                                    Log.d("J pos","J pos" + j);
                                }
                            }
                        }
                    }
                    for (int i = 0; i < stonePos.length - 1; i++) {
                        if (stonePos[3][carPos] == 1){
                            hearts[life].setVisibility(View.INVISIBLE);
                            life = life - 1;
                            vibrator();

                            if (life == -1){
                                life = 2;
                                for (int j = 0; j < 3; j++) {
                                    hearts[j].setVisibility(View.VISIBLE);
                                }

                            }
                            break;
                        }

                    }
                    resetChanged();
                });
            }},1000,DELAY);



    }

    private void vibrator() {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

//    private void showpos() {
//        Log.d("stonepos","\n" + stonePos[0][0] + " " + stonePos[0][1] + " " + stonePos[0][2] );
//        Log.d("stonepos","\n" + stonePos[1][0] + " " + stonePos[1][1] + " " + stonePos[1][2] );
//        Log.d("stonepos","\n" + stonePos[2][0] + " " + stonePos[2][1] + " " + stonePos[2][2] );
//
//        Log.d("changed","\n" + changed[0][0] + " " + changed[0][1] + " " + changed[0][2] );
//        Log.d("changed","\n" + changed[1][0] + " " + changed[1][1] + " " + changed[1][2] );
//        Log.d("changed","\n" + changed[2][0] + " " + changed[2][1] + " " + changed[2][2] );
//
//    }

    private void resetLastlane() {
        for (int i = 0; i < stonePos[3].length; i++) {
            stonePos[3][i] = 0;
        }
    }


    private void resetChanged() {
        for (int i = 0; i < changed.length; i++) {
            for (int j = 0; j < changed[i].length; j++) {
                changed[i][j] = 0;
            }
        }
    }


    private void findCarImgs() {
        carView = new ImageView[]{
                findViewById(R.id.car40),findViewById(R.id.car41),findViewById(R.id.car42)
        };
    }

    private void setBTNs() {
        leftBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (carPos){
                    case 0:
                        break;
                    case 1:
                        carPos = 0;
                        carView[1].setVisibility(View.INVISIBLE);
                        carView[0].setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        carPos = 1;
                        carView[2].setVisibility(View.INVISIBLE);
                        carView[1].setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        rightBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (carPos){
                    case 0:
                        carPos = 1;
                        carView[0].setVisibility(View.INVISIBLE);
                        carView[1].setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        carPos = 2;
                        carView[1].setVisibility(View.INVISIBLE);
                        carView[2].setVisibility(View.VISIBLE);
                        break;
                    case 2:

                        break;
                }

            }
        });
    }

    private void findStoneImgs() {
        stones = new ImageView[][] {
                {findViewById(R.id.stone00),findViewById(R.id.stone01),findViewById(R.id.stone02)},
                {findViewById(R.id.stone10),findViewById(R.id.stone11),findViewById(R.id.stone12)},
                {findViewById(R.id.stone30),findViewById(R.id.stone31),findViewById(R.id.stone32)}
        };
    }


    private void findViews() {

        leftBTN = findViewById(R.id.btn_left);
        rightBTN = findViewById(R.id.btn_right);
        hearts = new ImageView[]{
                findViewById(R.id.heart0),findViewById(R.id.heart1),findViewById(R.id.heart2)
        };
    }
}