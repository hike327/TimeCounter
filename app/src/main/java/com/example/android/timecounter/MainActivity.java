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
    TextView timerOut;
    SeekBar timerSeekBar;
    boolean inProgress = false;
    boolean firstTouch = true;
    CountDownTimer countDownTimer;
    Button mainButton;
    Button resetButton;
    Button minPlus;
    Button minMinus;
    Button secPlus;
    Button secMinus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSec = 30;
        timerOut = (TextView) findViewById(R.id.timerTextView);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
        mainButton = (Button) findViewById(R.id.mainButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setVisibility(View.INVISIBLE);
        minPlus = (Button) findViewById(R.id.minutePlusButton);
        minMinus = (Button)findViewById(R.id.minuteMinusButton);
        secPlus = (Button)findViewById(R.id.secondPlusButton);
        secMinus = (Button)findViewById(R.id.secondMinusButton);

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

    }

    public void munitesPlus(View view){
        if (timerSec <= 1740) {
            setTimer(timerSec + 60);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set time more than 30 minutes",Toast.LENGTH_SHORT).show();
        }
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
    }
    public void munitesMinus(View view){
        if (timerSec >= 60) {
            setTimer(timerSec - 60);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set negative time",Toast.LENGTH_SHORT).show();
        }
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
    }
    public void secondsPlus(View view){
        if (timerSec <= 1799) {
            setTimer(timerSec+1);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set time more than 30 minutes",Toast.LENGTH_SHORT).show();
        }
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
    }
    public void secondsMinus(View view){
        if (timerSec >= 1) {
            setTimer(timerSec - 1);
        } else {
            Toast.makeText(getApplicationContext(),"You can't set negative time",Toast.LENGTH_SHORT).show();
        }
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
    }

    public void mainButton(View view) {

        resetButton.setVisibility(View.VISIBLE);

        if (firstTouch) {

            firstTouch = false;
            inProgress = true;
            mainButton.setText("Stop");
            minMinus.setEnabled(false);
            minPlus.setEnabled(false);
            secMinus.setEnabled(false);
            secPlus.setEnabled(false);
            setCountDownTimer();
            countDownTimer.start();
        } else {
            if (inProgress) {

                inProgress=false;
                countDownTimer.cancel();
                setCountDownTimer();
                mainButton.setText("Resume");

            } else {
                inProgress=true;
                countDownTimer.start();
                mainButton.setText("Stop");
            }
        }
    }

    public void resetAll(View view){

        countDownTimer.cancel();
        timerSec = 30;
        timerSeekBar.setMax(timerSec);
        timerSeekBar.setProgress(timerSec);
        setTimer(timerSec);
        inProgress = false;
        firstTouch = true;
        mainButton.setText("Go!");
        resetButton.setVisibility(View.INVISIBLE);
        minMinus.setEnabled(true);
        minPlus.setEnabled(true);
        secMinus.setEnabled(true);
        secPlus.setEnabled(true);
        mainButton.setEnabled(true);
    }

    public void setCountDownTimer(){
        countDownTimer = new CountDownTimer(timerSec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTimer((int) millisUntilFinished / 1000);
                timerSeekBar.setProgress((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                setTimer(0);
                timerSeekBar.setProgress(0);
                mainButton.setEnabled(false);
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.foghorn);
                mediaPlayer.start();
            }
        };
    }
}
