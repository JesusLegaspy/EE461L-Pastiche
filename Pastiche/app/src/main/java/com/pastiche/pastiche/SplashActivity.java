package com.pastiche.pastiche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aria Pahlavan on 10/22/16.
 */

public class SplashActivity extends AppCompatActivity{
    private static final String ACTIVITY_TAG = "SplashActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Used to change color of status bar */
//        if ( Build.VERSION.SDK_INT >= 21) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.oil));
//        }



        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
