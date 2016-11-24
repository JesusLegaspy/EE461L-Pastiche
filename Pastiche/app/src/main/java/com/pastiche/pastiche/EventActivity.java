package com.pastiche.pastiche;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.pastiche.pastiche.adapter.PhotoListAdapter;

/**
 * Created by Aria Pahlavan on 11/12/16.
 */

public class EventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";
    public static final String EXTRA_EVENT_NAME = "eventName";
    protected final int INVALID_ID = -1;
    protected int eventID = -1;
    protected String eventName = null;
    protected PhotoListAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        setupActivity(savedInstanceState);
        buildRecyclerView();
    }



    private void buildRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_event_activity);
        recyclerView.setHasFixedSize(true);


        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PhotoListAdapter(this, eventID);


        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }



    private void setupActivity(Bundle savedInstanceState) {
        eventID = retrieveEventId(savedInstanceState);
        eventName = retrieveEventName(savedInstanceState);

        TextView event_name = (TextView) findViewById(R.id.txt_event_toolbar);
        event_name.setText(eventName);
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
    private int retrieveEventId(Bundle savedInstanceState) {
        int eventId;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                eventId = INVALID_ID;
            } else {
                eventId = extras.getInt(EXTRA_EVENT_ID);
            }
        } else {
            eventId = (int) savedInstanceState.getSerializable(EXTRA_EVENT_ID);
        }

        return eventId;
    }



    /**
     * retrieve event name from callee (which is an eventListViewHolder)
     *
     * @param savedInstanceState
     * @return
     */
    private String retrieveEventName(Bundle savedInstanceState) {
        String eventName;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                eventName = null;
            } else {
                eventName = extras.getString(EXTRA_EVENT_NAME);
            }
        } else {
            eventName = (String) savedInstanceState.getSerializable(EXTRA_EVENT_NAME);
        }

        eventName = reformatNAme(eventName);

        return eventName;
    }


    /**
     * if event name is too large, shortens the name
     * @param eventName
     * @return
     */
    @NonNull
    private String reformatNAme(String eventName) {
        if ( eventName != null ) {


            if ( eventName.length() > 20 ) {
                char[] result = new char[18];
                eventName.getChars(0, 17, result, 0);
                eventName = String.valueOf(result).concat("...");
            }


        }else {
            eventName = " ¯\\_(ツ)_/¯";
        }
        return eventName;
    }



    /**
     * Initiate camera activity
     *
     * @param view
     */
    public void capturePicture(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT_ID, eventID);
        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refresh();
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
