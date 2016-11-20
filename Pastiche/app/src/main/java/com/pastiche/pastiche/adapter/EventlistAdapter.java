package com.pastiche.pastiche.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pastiche.pastiche.EventsListFragment;
import com.pastiche.pastiche.R;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.viewHolder.EventListViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class EventlistAdapter extends RecyclerView.Adapter<EventListViewHolder> {
    private static final String TAG = "EventListAdapter";


    // TODO number of recycler view items needs change to be dynamic
    private static int LENGTH;
    private List<Integer> events; //TODO: needs to be a pEvent

//    private final String[] mEvents;
//    private final Drawable[] mEventPictures;


    /**
     * get resources (should be an array of event IDs)
     * @param context
     */
    public EventlistAdapter(Context context) {

        events = new ArrayList<>(Arrays.asList(24,25,26,27,28,29,30));
        LENGTH = events.size();
//        Resources resources = context.getResources();
//        mEvents = resources.getStringArray(R.array.events);
//        TypedArray a = resources.obtainTypedArray(R.array.events_picture);
//        mEventPictures = new Drawable[a.length()];
//        for (int i = 0; i < mEventPictures.length; i++) {
//            mEventPictures[i] = a.getDrawable(i);
//        }
//        a.recycle();//TODO to be removed?
    }



    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventListViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }



    /**
     * assemble the view HOLDER for given POSITION
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(EventListViewHolder holder, int position) {
//        holder.setImg_event_item(mEventPictures[position % mEventPictures.length]);
        Context appContext = EventsListFragment.getAppContext();
        String internetUrl = "http://api.pastiche.staging.jacobingalls.rocks:8080/photos/";

        Glide.with(appContext).load(internetUrl + position).into(holder.getImg_event_item());
    }




    @Override
    public int getItemCount() {
        return LENGTH;
    }
}
