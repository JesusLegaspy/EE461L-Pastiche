package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends ActionBarActivity {

    private Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        main_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(main_toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the option menu
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
