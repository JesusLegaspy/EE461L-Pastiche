package com.pastiche.pastiche;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pastiche.pastiche.adapter.PhotoListAdapter;
import com.pastiche.pastiche.adapter.UserPhotoListAdapter;

/**
 * Created by Aria Pahlavan on 11/12/16.
 */

public class MyPhotosActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "userID";
    public static final String EXTRA_USER_NAME = "username";
    protected final int INVALID_ID = -1;
    protected int userId = -1;
    protected String username = null;
    protected UserPhotoListAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photos);


        setupActivity(savedInstanceState);
        buildRecyclerView();
    }



    private void buildRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_my_photos_activity);
        recyclerView.setHasFixedSize(true);


        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new UserPhotoListAdapter(this, userId);


        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }



    private void setupActivity(Bundle savedInstanceState) {
        userId = retrieveUserId(savedInstanceState);
        username = retrieveUsername(savedInstanceState);

        Toolbar eventToolbar = (Toolbar) findViewById(R.id.my_photos_toolbar);
        eventToolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentYellow));
        TextView event_name = (TextView) findViewById(R.id.txt_my_photos_toolbar);
        event_name.setText(username);

        //make Navigation bar transparent with bg color
        //set status bar color
        if ( Build.VERSION.SDK_INT >= 21 ) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentYellow));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.windowBackgroundDarker));
        }
    }



    @Override
    public void onBackPressed() {
        finish();
    }





    /**
     * retrieve event id from callee (which is an eventListViewHolder)
     *
     * @param savedInstanceState
     * @return
     */
    private int retrieveUserId(Bundle savedInstanceState) {
        int eventId;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                eventId = INVALID_ID;
            } else {
                eventId = extras.getInt(EXTRA_USER_ID);
            }
        } else {
            eventId = (int) savedInstanceState.getSerializable(EXTRA_USER_ID);
        }

        return eventId;
    }



    /**
     * retrieve event name from callee (which is an eventListViewHolder)
     *
     * @param savedInstanceState
     * @return
     */
    private String retrieveUsername(Bundle savedInstanceState) {
        String eventName;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                eventName = null;
            } else {
                eventName = extras.getString(EXTRA_USER_NAME);
            }
        } else {
            eventName = (String) savedInstanceState.getSerializable(EXTRA_USER_NAME);
        }

        eventName = reformatName(eventName);

        return eventName;
    }


    /**
     * if event name is too large, shortens the name
     * @param eventName
     * @return
     */
    @NonNull
    private String reformatName(String eventName) {
        if ( eventName != null ) {


            if ( eventName.length() > 20 ) {
                char[] result = new char[18];
                eventName.getChars(0, 17, result, 0);
                eventName = String.valueOf(result).concat("...");
            }


        }else {
            eventName = "Unknown";
        }
        return eventName;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refresh();
        adapter.notifyDataSetChanged();
//        super.finish();
    }



    public void refresh() {
        adapter.refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
