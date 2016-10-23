package com.pastiche.pastiche;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "MainActivity";
    private Toolbar main_toolbar;
//    private final int STR_SPLASH_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        main_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(main_toolbar);
        TextView app_name = (TextView) findViewById(R.id.toolbar_app_name);
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");

        app_name.setTypeface(title_font);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        startSplashTimer();

    }

//    //TODO need to add SplashActivity and move this method to it
//    private void startSplashTimer() {
//        try {
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, STR_SPLASH_TIME);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the option main_menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noInspection SimplifiableIfStatement
        if ( id == R.id.action_settings ){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
