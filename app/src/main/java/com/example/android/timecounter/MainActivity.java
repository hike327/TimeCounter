package com.example.android.timecounter;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    int timerSec;
    int timerMemory;
    boolean inProgress = false;
    boolean firstTouch = true;
    boolean lastTouch = false;

    Button mainButton;
    Button resetButton;
    Button minPlus;
    Button minMinus;
    Button secPlus;
    Button secMinus;
    TextView timerOut;
    SeekBar timerSeekBar;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerOut = (TextView)findViewById(R.id.timerTextView);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        mainButton = (Button)findViewById(R.id.mainButton);
        resetButton = (Button)findViewById(R.id.resetButton);
        minPlus = (Button)findViewById(R.id.minutePlusButton);
        minMinus = (Button)findViewById(R.id.minuteMinusButton);
        secPlus = (Button)findViewById(R.id.secondPlusButton);
        secMinus = (Button)findViewById(R.id.secondMinusButton);

        resetCounter();

    }

    public void resetCounter(){

        inProgress = false;
        firstTouch = true;
        lastTouch = false;
        timerSeekBar.setMax(30);
        setTimer(30);

        resetButton.setVisibility(View.INVISIBLE);
    }

    public void setTimer(int newTime){
        timerSec = newTime;
        int minutes = (int) timerSec/60;
        int seconds = timerSec - minutes*60;
        String minutesOut;
        String secondsOut;
        if (minutes <=9) {
            minutesOut = "0"+Integer.toString(minutes);
        } else {
            minutesOut = Integer.toString(minutes);
        }

        if (seconds <=9) {
            secondsOut = "0"+Integer.toString(seconds);
        } else {
            secondsOut = Integer.toString(seconds);
        }

        timerOut.setText( minutesOut + ":" + secondsOut);
        timerSeekBar.setProgress(timerSec);
    }

    public void munitesPlus(View view){
        if (timerSec <= 1740) {
            timerSeekBar.setMax(timerSec + 60);
            setTimer(timerSec + 60);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set time more than 30 minutes",Toast.LENGTH_SHORT).show();
        }
    }
    public void munitesMinus(View view){
        if (timerSec >= 60) {
            timerSeekBar.setMax(timerSec - 60);
            setTimer(timerSec - 60);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set negative time",Toast.LENGTH_SHORT).show();
        }
    }
    public void secondsPlus(View view){
        if (timerSec <= 1799) {
            timerSeekBar.setMax(timerSec + 1);
            setTimer(timerSec+1);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set time more than 30 minutes",Toast.LENGTH_SHORT).show();
        }
    }
    public void secondsMinus(View view){
        if (timerSec >= 1) {
            timerSeekBar.setMax(timerSec - 1);
            setTimer(timerSec - 1);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set negative time",Toast.LENGTH_SHORT).show();
        }
    }

    public void mainButton(View view) {

        if (firstTouch) {
            timerMemory = timerSec;
            firstTouch = false;
            inProgress = true;
            mainButton.setText("Pause");
            minMinus.setEnabled(false);
            minPlus.setEnabled(false);
            secMinus.setEnabled(false);
            secPlus.setEnabled(false);
            setCountDownTimer();
            countDownTimer.start();
        } else if (lastTouch) {
            lastTouch = false;
            resetButton.setVisibility(View.INVISIBLE);
            setTimer(timerMemory);
            mainButton.setText("Pause");
            setCountDownTimer();
            countDownTimer.start();
        } else {
            if (inProgress) {
                resetButton.setVisibility(View.VISIBLE);
                inProgress=false;
                countDownTimer.cancel();
                setCountDownTimer();
                mainButton.setText("Resume");

            } else {
                resetButton.setVisibility(View.INVISIBLE);
                inProgress=true;
                countDownTimer.start();
                mainButton.setText("Pause");
            }
        }
    }

    public void resetAll(View view){

        countDownTimer.cancel();
        resetCounter();
        mainButton.setText("Go!");
        resetButton.setVisibility(View.INVISIBLE);
        minMinus.setEnabled(true);
        minPlus.setEnabled(true);
        secMinus.setEnabled(true);
        secPlus.setEnabled(true);
        mainButton.setEnabled(true);
    }

    public void setCountDownTimer(){
        countDownTimer = new CountDownTimer(timerSec * 1000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTimer((int) millisUntilFinished / 1000);
                timerSeekBar.setProgress((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                setTimer(0);
                timerSeekBar.setProgress(0);
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.foghorn);
                mediaPlayer.start();
                lastTouch=true;
                mainButton.setText("REPEAT");
                resetButton.setVisibility(View.VISIBLE);
            }
        };
    }
}
