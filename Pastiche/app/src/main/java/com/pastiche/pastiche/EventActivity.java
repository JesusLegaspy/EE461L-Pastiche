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
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_event_activity);
        recyclerView.setHasFixedSize(true);  //TODO might want to remove

        PhotoListAdapter adapter = new PhotoListAdapter(recyclerView.getContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        //TODO might need to change to allow download as scrolling
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
//        return recyclerView;

    }
}
