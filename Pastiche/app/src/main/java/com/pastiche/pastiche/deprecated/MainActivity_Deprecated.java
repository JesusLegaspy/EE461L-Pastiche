package com.pastiche.pastiche.deprecated;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.pastiche.pastiche.CameraActivity;
import com.pastiche.pastiche.R;
import com.pastiche.pastiche.register.LoginActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_Deprecated extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "MainActivity_Deprecated";
    private static final String SHARED_PREF_NAME = "PUSER_INFO";
    private static final float DISASBLE_ALPHA = (float) 0.4;
    private static final float ENABLE_ALPHA = 1;
    private Toolbar main_toolbar;
    private List<Integer> curUserEvents;


    public static String getSharedPreferenceName() {
        return SHARED_PREF_NAME;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_deprecated);
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL)); //TODO: remove??

        //make Navigation bar transparent with bg color
        //set status bar color
        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackground));
        }

        //set up the app bar
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        TextView app_name = (TextView) findViewById(R.id.toolbar_app_name);

        //set up app name font
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");

        app_name.setTypeface(title_font);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //authentication
        String id = new String();
        if ( !registered(id) ) {
            startAuthentications();
        } else {
            Log.d(ACTIVITY_TAG, "logged_in");
        }

        //update user's events
        updateCurUserEvents();

    }

    /**
     * updates the list of current user events
     */
    public void updateCurUserEvents() {
        //TODO API call to get cur user list of events
    }

    /**
     * Add event list fragment to Main Activity
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new EventsListFragment_Deprecated(), "Tile");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /**
     * Check private storage for user info
     * if nothing found, user is not registered
     *
     * @return true if user already logged in, false otherwise
     */
    private boolean registered(String _id) {
        SharedPreferences pref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String id = pref.getString("id", "");
        boolean logged_in = pref.getBoolean("logged_in", false);

        Log.d(ACTIVITY_TAG, "id: " + id + " is logged in? " + logged_in);
        Toast.makeText(this, "id: " + id + " is logged in? " + logged_in, Toast.LENGTH_LONG).show();

        _id = id;
        return logged_in;
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

    private void startAuthentications() {

        Intent intent = new Intent(MainActivity_Deprecated.this, LoginActivity.class);
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
        if ( id == R.id.action_settings ) {
            String _id = new String();
            registered(_id);
            return true;
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

    /**
     * logout user.. clean up shared preferences
     */
    private void logout() {
        SharedPreferences pref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        boolean is_loggedout = editor.commit();
        Log.d(ACTIVITY_TAG, "Logout: " + is_loggedout);
    }


    //-------------------[TESTING]------------------------
    {    /*
    public void testLogin(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ( networkInfo != null && networkInfo.isConnected() ) {
            //do work
            ServerHandler handle = ServerHandler.getInstance(getApplicationContext());
            handle.login("kqtest", "mypassword", x -> Log.d("Main_KhalidTesting", "ID: " + x.getId() + " UserID: " + x.getUserId()), x -> Log.d("Main_KhalidTesting", x));
            testImage();
        } else {
            //log
        }
    }

    public void testImage() {
        ServerHandler handler = ServerHandler.getInstance(getApplicationContext());

        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.regularImageView);
        //mImageView.setImageBitmap();


        handler.getImg(27, ImageView.ScaleType.CENTER, x -> mImageView.setImageBitmap(x), x -> Log.d("main", "Didn't work. " + x));
    }
    */}
}