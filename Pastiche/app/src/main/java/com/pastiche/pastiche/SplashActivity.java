package com.pastiche.pastiche;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Aria Pahlavan on 10/22/16.
 */

public class SplashActivity extends AppCompatActivity{
    private static final String ACTIVITY_TAG = "SplashActivity";
    private final int STR_SPLASH_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        TextView app_name = (TextView) findViewById(R.id.toolbar_app_name);
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");

        app_name.setTypeface(title_font);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        startSplashTimer();

    }

    //TODO need to add SplashActivity and move this method to it
    private void startSplashTimer() {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, STR_SPLASH_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
