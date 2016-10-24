package com.pastiche.pastiche;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "MainActivity";
    private static final float DISASBLE_ALPHA = (float) 0.4;
    private static final float ENABLE_ALPHA = 1;
    private Toolbar main_toolbar;


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

        startAuthentications();

    }

    /**
     * Disables the button and sets the disable transparency
     * @param button
     */
    protected static void disableButton(Button button){
        button.setEnabled(false);
        button.setAlpha(DISASBLE_ALPHA);
    }

    /**
     * Enables the button and sets the enable transparency
     * @param button
     */
    protected static void enableButton(Button button){
        button.setEnabled(true);
        button.setAlpha(ENABLE_ALPHA);
    }

    //    //TODO need to add SplashActivity and move this method to it
    private void startAuthentications() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

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
