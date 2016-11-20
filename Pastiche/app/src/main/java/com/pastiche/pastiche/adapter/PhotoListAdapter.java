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
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.PObject.PPhoto;
import com.pastiche.pastiche.R;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestHandler;
import com.pastiche.pastiche.viewHolder.EventListViewHolder;
import com.pastiche.pastiche.viewHolder.PhotoListViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListViewHolder> {
    private static final String TAG = "PhotoListAdapter";


    private List<PEvent> events;
    private Map<Integer, PPhoto> eventFirstPictures;

    /**
     * get resources (should be an array of event IDs)
     * @param context
     */
    public PhotoListAdapter(Context context) {
        // TODO replace logic with that to load a list of photos for a supplied event id

        events = new ArrayList<>(100);
        ServerHandler.getInstance(context).listEvents(data -> {
            events.clear();
            Collections.addAll(events, data);
            this.notifyDataSetChanged();

            eventFirstPictures = new ConcurrentHashMap<>();

            events.parallelStream().forEach(event -> {
                ServerHandler.getInstance(context).listPhotosForAnEvent(event.getEventId(), photoDate -> {
                    if(photoDate.length > 0) {
                        eventFirstPictures.put(event.getEventId(), photoDate[photoDate.length-1]);
                    } else {
                        // TODO use default picture or gradient
                    }

                    this.notifyDataSetChanged();
                }, error -> {
                    Log.e(TAG, error);
                });
            });
        }, error -> {
            Log.e(TAG, error);
        });
    }





    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoListViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }



    /**
     * assemble the view HOLDER for given POSITION
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PhotoListViewHolder holder, int position) {
//        holder.setImg_event_item(mEventPictures[position % mEventPictures.length]);
        Context appContext = EventsListFragment.getAppContext();
        String internetUrl = ServerRequestHandler.baseURL + "/photos/";

        PEvent event = events.get(position);

        if(eventFirstPictures != null && eventFirstPictures.containsKey(event.getEventId())) {
            String url = internetUrl + eventFirstPictures.get(event.getEventId()).getId();
            Glide.with(appContext).load(url).into(holder.getImg_event_item());
        }

        holder.setTxt_event_item_title(event.getName());
    }




    @Override
    public int getItemCount() {
        return events.size();
    }
}
