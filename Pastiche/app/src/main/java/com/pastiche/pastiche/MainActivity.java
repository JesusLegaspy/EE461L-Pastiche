package com.pastiche.pastiche;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.PObject.PUser;
import com.pastiche.pastiche.Server.PersistentCookieStore;
import com.pastiche.pastiche.Server.ServerHandler;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class MainActivity extends AppCompatActivity{

    private static final String ACTIVITY_TAG = "MainActivity";
    private static final String SHARED_PREF_NAME = "PUSER_INFO";
    private static final float DISASBLE_ALPHA = (float) 0.4;
    private static final float ENABLE_ALPHA = 1;
    private Toolbar main_toolbar;
    private List<PEvent> eventSearchResult;
    private PEvent curUserEvent;
    private PUser pUser;



    public static String getSharedPreferenceName() {
        return SHARED_PREF_NAME;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationSetup();
        appbarSetup();
        updateCurUserEvents();
    }



    /**
     * updates the list of current user events
     */
    public void updateCurUserEvents() {
        //TODO API call to get cur user list of events
    }



    /**
     * set appbar font
     */
    private void appbarSetup() {
        //set up the app bar
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        TextView app_name = (TextView) findViewById(R.id.toolbar_app_name);


        //set up app name font
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");
        app_name.setTypeface(title_font);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



    /**
     * make Navigation bar transparent with bg color
     * set status bar color
     */
    private void applicationSetup() {
        CookieHandler.setDefault( new CookieManager( new PersistentCookieStore(this), CookiePolicy.ACCEPT_ALL ) );
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackgroundDarker));
        }
    }






    /**
     * testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
     * <p>
     * Disables the button and sets the disable transparency
     *
     * @param button
     */
    public static void disableButton(Button button) {
        button.setEnabled(false);
        button.setAlpha(DISASBLE_ALPHA);
    }




    /**
     * Enables the button and sets the enable transparency
     *
     * @param button
     */
    public static void enableButton(Button button) {
        button.setEnabled(true);
        button.setAlpha(ENABLE_ALPHA);
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
        if ( id == R.id.action_settings ) {
            Toast.makeText(this, "No settings!", Toast.LENGTH_SHORT).show();
        }

        if ( id == R.id.action_search ) {
            Integer[] event_results = performSearch("keyword");


            return true;
        }

        if ( id == R.id.action_logout ) {
            logout();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    /**
     * Initiate camera activity
     * @param view
     */
    public void capturePicture(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }



    /**
     * Performs a server call to acquire a list of events as a result of search
     * for KEYWORD
     *
     * @param keyword
     * @return
     */
    private Integer[] performSearch(String keyword) {
        //TODO API call to perform search for events
        Integer[] results = { 1, 2, 3 };
        return results;
    }



    @Override
    public void onBackPressed() {
        finishAffinity();
    }



    /**
     * logout user.. clean up shared preferences
     */
    private void logout() {
        SharedPreferences pref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ServerHandler.getInstance(this.getApplicationContext()).logout(
                data -> onLogoutSuccess(editor),
                error -> onLogoutFail(error));
    }



    private void onLogoutFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT);
    }



    void onLogoutSuccess(SharedPreferences.Editor editor){
        editor.clear();
        boolean is_loggedout = editor.commit();
        Log.d(ACTIVITY_TAG, "Logout: " + is_loggedout);
        finish();
    }
}

