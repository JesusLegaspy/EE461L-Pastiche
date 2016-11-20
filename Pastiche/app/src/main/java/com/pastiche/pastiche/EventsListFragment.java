package com.pastiche.pastiche;

import android.content.Context;
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
    private static Context appContext;


    public static Context getAppContext() {
        return appContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        appContext = getActivity().getApplicationContext();

        EventlistAdapter adapter = new EventlistAdapter(recyclerView.getContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        //TODO might need to change to allow download as scrolling
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }









































}
