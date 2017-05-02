package com.pastiche.pastiche;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pastiche.pastiche.adapter.EventlistAdapter;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class EventsListFragment extends Fragment {
    private EventlistAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        adapter = new EventlistAdapter(recyclerView.getContext(), null);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }


    public void refresh() {
        adapter.refresh();
    }
}
