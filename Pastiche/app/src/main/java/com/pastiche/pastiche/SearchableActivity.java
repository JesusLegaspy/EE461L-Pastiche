package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.adapter.EventlistAdapter;

public class SearchableActivity extends AppCompatActivity {
    public static final String QUERY = "SEARCH_QUERY";
    private EventlistAdapter adapter = null;
    private PEvent[] results = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        doMySearch(retrieveQuery(savedInstanceState));
    }



    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view_searchable_activity);


        adapter = new EventlistAdapter(getApplicationContext(), results);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }



    /**
     * retrieve query from callee (Main Activity)
     *
     * @param savedInstanceState
     * @return
     */
    private String retrieveQuery(Bundle savedInstanceState) {
        String txt;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                txt = null;
            } else {
                txt = extras.getString(QUERY);
            }
        } else {
            txt = (String) savedInstanceState.getSerializable(QUERY);
        }


        return txt;
    }



    private void doMySearch(String query) {
        ServerHandler.getInstance(SearchableActivity.this)
                .searchEvents(
                        query,
                        data -> {
                            this.results = data;
                            setupRecyclerView();
                        },
                        error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        );
    }



    @Override
    public void onBackPressed() {
        finish();
    }
}
