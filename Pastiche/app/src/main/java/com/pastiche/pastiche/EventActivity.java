package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aria Pahlavan on 11/12/16.
 */

public class EventActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }
}
