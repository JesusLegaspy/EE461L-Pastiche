package com.pastiche.pastiche;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.PObject.PUser;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestQueue;

import java.util.List;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class MainActivity extends AppCompatActivity {


    private static final String ACTIVITY_TAG = "MainActivity";
    private static final String SHARED_PREF_NAME = "PUSER_INFO";
    private static final float DISASBLE_ALPHA = (float) 0.4;
    private static final float ENABLE_ALPHA = 1;
    private Toolbar main_toolbar;
    private ImageView searchFilter;
    private SearchView searchView;
    private List<PEvent> eventSearchResult;
    private PEvent curUserEvent;
    private PUser pUser;


    public static String getSharedPreferenceName() {
        return SHARED_PREF_NAME;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActivity();
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

        searchFilter = (ImageView) findViewById(R.id.img_main_search_filter);

        //set up app name font
        Typeface title_font = Typeface.createFromAsset(getAssets(), "fonts/GreatVibes-Regular.ttf");
        app_name.setTypeface(title_font);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * make Navigation bar transparent with bg color
     * set status bar color
     */
    private void setupActivity() {
        Glide.get(getApplicationContext()).setMemoryCategory(MemoryCategory.HIGH);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackgroundDarker));
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


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Pastiche Event");
        ComponentName componentName = new ComponentName(this, SearchableActivity.class);
        searchView.setSearchableInfo( searchManager.getSearchableInfo(componentName) );


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(ACTIVITY_TAG, query);
                Intent intent  = new Intent(MainActivity.this, SearchableActivity.class);
                intent.putExtra(SearchableActivity.QUERY, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "No settings!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                break;
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_refresh:
                refresh();
                break;
            case R.id.action_addEvent:
                addEvent();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }



    /**
     * Initiate camera activity
     *
     * @param view
     */
    public void capturePicture(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        searchFilter.setAlpha((float) 0.0);
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


    private void refresh() {
        EventsListFragment elf = (EventsListFragment) getSupportFragmentManager().findFragmentById(R.id.frg_events_main);
        ServerRequestQueue.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
        elf.refresh();
    }


    private void onLogoutFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT);
    }


    void onLogoutSuccess(SharedPreferences.Editor editor) {
        editor.clear();
        boolean is_loggedout = editor.commit();
        Log.d(ACTIVITY_TAG, "Logout: " + is_loggedout);
        finish();
    }


    void addEvent() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.new_event_user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", (dialogBox, id) -> ServerHandler.getInstance(getApplicationContext())
                        .createEvent(userInputDialogEditText.getText().toString(),
                                x -> {
                                    Toast.makeText(getApplicationContext(), "Event" + x.getName() + "Created", Toast.LENGTH_SHORT).show();
                                    refresh();
                                },
                                x -> Toast.makeText(getApplicationContext(), x, Toast.LENGTH_LONG).show()))

                .setNegativeButton("Cancel", (dialogBox, id) -> dialogBox.cancel());

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }


    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return super.onSearchRequested(searchEvent);
    }
}

