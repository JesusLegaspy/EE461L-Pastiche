package com.pastiche.pastiche;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pastiche.pastiche.adapter.PhotoListAdapter;

import java.util.Random;

/**
 * Created by Aria Pahlavan on 11/12/16.
 */

public class EventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";
    protected final int INVALID_ID = -1;
    protected int eventID = -1;
    protected PhotoListAdapter adapter = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        eventID = retrieveEventId(savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_event_activity);
        recyclerView.setHasFixedSize(true);  //TODO might want to remove


        //TODO might need to change to allow download as scrolling
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PhotoListAdapter(this, eventID);


        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Event Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
