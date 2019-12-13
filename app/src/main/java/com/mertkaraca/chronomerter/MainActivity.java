package com.mertkaraca.chronomerter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    /*Creating our variables globally to access
    them everywhere we want, We'll to determine them later*/
    Button btnStart,btnFinish;
    Chronometer chronometer;
    Animation rotate;
    ImageView imageView;
    long pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Determining variables values with their own id's which we dedicated in activity_main.xml
        btnStart = findViewById(R.id.btnStart);
        btnFinish = findViewById(R.id.btnFinish);
        chronometer = findViewById(R.id.chronometer);
        imageView = findViewById(R.id.imageView);

        //This object getting it's functionality from rotation.xml
        rotate = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotation);

        //Writing what will happen on we press Start button
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
                btnFinish.setVisibility(View.VISIBLE);
            }
        });

        //Writing what will happen on we press Stop button
        btnFinish.setOnClickListener(new View.OnClickListener() {
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
                //This time Start button come forward and Finish button goes backward
                btnStart.setVisibility(View.VISIBLE);
                btnFinish.setVisibility(View.INVISIBLE);
            }
        });
    }
}
