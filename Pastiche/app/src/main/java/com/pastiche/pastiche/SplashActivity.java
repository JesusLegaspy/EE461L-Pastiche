package com.pastiche.pastiche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aria Pahlavan on 10/22/16.
 */

public class SplashActivity extends AppCompatActivity{
    private static final String ACTIVITY_TAG = "SplashActivity";
    private final int STR_SPLASH_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
