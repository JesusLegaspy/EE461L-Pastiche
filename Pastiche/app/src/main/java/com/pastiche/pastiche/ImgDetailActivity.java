package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ImgDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
