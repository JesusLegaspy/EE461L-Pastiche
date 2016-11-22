package com.pastiche.pastiche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.pastiche.pastiche.register.LoginActivity;

/**
 * Screen only displayed if current part of the app is still configuring
 *
 * Created by Aria Pahlavan on 10/22/16.
 */

public class SplashActivity extends AppCompatActivity{
    private static final String ACTIVITY_TAG = "SplashActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call main activity
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
