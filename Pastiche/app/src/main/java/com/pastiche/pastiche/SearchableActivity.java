package com.pastiche.pastiche;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.adapter.EventlistAdapter;

import java.util.List;

public class SearchableActivity extends AppCompatActivity {
    private EventlistAdapter adapter;
    private RecyclerView recyclerView;
    private List<PEvent> results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable);
        getQuery();


        setupRecyclerView();
    }



    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rec_view_searchable_activity);


        adapter = new EventlistAdapter(recyclerView.getContext(), results);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }



    public void getQuery() {
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }




    private void doMySearch(String query) {
//        ServerHandler.getInstance(getApplicationContext())
                
    }


}
