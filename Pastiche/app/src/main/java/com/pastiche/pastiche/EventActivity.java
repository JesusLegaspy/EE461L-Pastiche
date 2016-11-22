package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.pastiche.pastiche.adapter.PhotoListAdapter;

/**
 * Created by Aria Pahlavan on 11/12/16.
 */

public class EventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";
    protected final int INVALID_ID = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_event_activity);
        recyclerView.setHasFixedSize(true);  //TODO might want to remove



        //TODO might need to change to allow download as scrolling
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        PhotoListAdapter adapter =
                new PhotoListAdapter(this, retrieveEventId(savedInstanceState));


        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }




    /**
     * retrieve event id from callee (which is an eventListViewHolder)
     * @param savedInstanceState
     * @return
     */
    private int retrieveEventId(Bundle savedInstanceState) {
        int eventId;

        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();

            if(extras == null)
                eventId = INVALID_ID;


            else
                eventId = extras.getInt(EXTRA_EVENT_ID);


        }
        else
            eventId = (int) savedInstanceState.getSerializable(EXTRA_EVENT_ID);


        return eventId;
    }

}
