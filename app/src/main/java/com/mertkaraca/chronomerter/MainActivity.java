package com.mertkaraca.chronomerter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /*Creating our variables globally to access
    them everywhere we want, We'll to determine them later*/
    Button btnStart,btnPause,btnResume,btnReset;
    Chronometer chronometer;
    Animation rotate;
    ImageView imageView;
    long pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Keeps screen on while view is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Determining variables values with their own id's which we dedicated in activity_main.xml
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);
        btnReset = findViewById(R.id.btnReset);
        chronometer = findViewById(R.id.chronometer);
        imageView = findViewById(R.id.imageView);

        //This object getting it's functionality from rotation.xml
        rotate = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotation);

        //Start Button
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //our imageView start to animate with rotate which we wrote part of it in rotation.xml
                imageView.startAnimation(rotate);
                chronometer.setFormat("%s");
                //chronometer object subtract pause's value from elapsed time
                chronometer.setBase(SystemClock.elapsedRealtime()-pause);
                chronometer.start();
                //After we start chronometer object start button going back and finish button come forward
                btnStart.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        //Stop Button
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stops chronometer visually
                chronometer.stop();
                //We are using this expression to use it Start and Resume buttons
                pause = SystemClock.elapsedRealtime() - chronometer.getBase();
                //Stops and resets the animation
                imageView.clearAnimation();

                //When we press the Stop button, the Stop button is invisible,
                //and the Resume and Finish buttons are visible.
                btnPause.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });

        //Resume Button
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We should use this expression to resume the chronometer
                //It basically prevent chronometer from elapsed time
                chronometer.setBase(SystemClock.elapsedRealtime()-pause);
                //Activates chronometer visually
                chronometer.start();
                //Starts turning animation
                imageView.startAnimation(rotate);

                //When we press the Resume button, the Resume and Reset buttons become invisible,
                //and the Pause button becomes visible.
                btnResume.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        //Reset Button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                //It set's chronometer's base value to default to restart the process
                chronometer.setBase(SystemClock.elapsedRealtime());
                //We equaling pause to default value to restart the process again
                pause = 0;
                //imageView stop's rotating
                rotate.cancel();
                imageView.setAnimation(rotate);

                //When we press the Reset button, the Resume and Finish buttons become invisible
                //and the Start button becomes visible.
                btnReset.setVisibility(View.INVISIBLE);
                btnResume.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });
    }
}
