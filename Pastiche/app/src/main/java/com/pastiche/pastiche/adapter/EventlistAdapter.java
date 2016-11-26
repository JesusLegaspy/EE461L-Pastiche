package com.pastiche.pastiche.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pastiche.pastiche.PObject.PEvent;
import com.pastiche.pastiche.PObject.PPhoto;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestHandler;
import com.pastiche.pastiche.viewHolder.EventListViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class EventlistAdapter extends RecyclerView.Adapter<EventListViewHolder> {
    private static final String TAG = "EventListAdapter";

    private final Context appContext;

    private List<PEvent> events;
    private Map<Integer, PPhoto> eventFirstPictures;


    /**
     * get resources (should be an array of event IDs)
     *
     * @param context
     * @param results
     */
    public EventlistAdapter(Context context, PEvent[] results) {
        appContext = context;
        if ( results == null )
            refresh();
        else
            refreshFromSearch(results);
    }



    public void refresh() {
        Log.d(TAG, "Refreshing events");
        events = new ArrayList<>(100);

        ServerHandler handler = ServerHandler.getInstance(appContext);
        handler.listEvents(
                data -> loadListEvents(data),
                error -> {
                    Log.e(TAG, error);
                    Toast.makeText(appContext, error, Toast.LENGTH_LONG).show();
                }
        );
    }


    /**
     * This is refresh as a result of search query by user
     * @param results
     */
    public void refreshFromSearch(PEvent[] results) {
        Log.d(TAG, "Refreshing events with search results");
        events = new ArrayList<>(100);

        loadListEvents(results);
    }



    /**
     * Load the list of all events
     *
     * @param data
     */
    private void loadListEvents(PEvent[] data) {
        ServerHandler handler = ServerHandler.getInstance(appContext);

        events.clear();
        Collections.addAll(events, data);

        eventFirstPictures = new ConcurrentHashMap<>();

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            events.parallelStream().forEach(
                    event -> handler.listPhotosForAnEvent(event.getEventId(),
                            listPhotos -> loadEventFirstPhotos(event, listPhotos),
                            error -> {
                                Log.e(TAG, error);
                                Toast.makeText(appContext, error, Toast.LENGTH_LONG).show();
                            }
                    )

            );
        } else {
            for ( PEvent event : events ) {

                handler.listPhotosForAnEvent(event.getEventId(),
                        listPhotos -> loadEventFirstPhotos(event, listPhotos),
                        error -> {
                            Log.e(TAG, error);
                            Toast.makeText(appContext, error, Toast.LENGTH_LONG).show();
                        }
                );
            }
        }
    }



    /**
     * load the first photo of event into corresponding slot in listPhotos
     * and notify the adapter that data has changed
     *
     * @param event
     * @param listPhotos
     */
    private void loadEventFirstPhotos(PEvent event, PPhoto[] listPhotos) {
        if ( listPhotos.length > 0 ) {
            eventFirstPictures.put(event.getEventId(), listPhotos[0]);
        }

        this.notifyDataSetChanged();
    }



    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventListViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }



    /**
     * assemble the view HOLDER for given POSITION
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(EventListViewHolder holder, int position) {

        String internetUrl = ServerRequestHandler.baseURL + "/photos/";

        PEvent event = events.get(position);


        if ( eventFirstPictures != null && eventFirstPictures.containsKey(event.getEventId()) ) {

            String url = internetUrl + eventFirstPictures.get(event.getEventId()).getId();
            Glide.with(appContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.getEventPhoto());


            holder.setEventId(event.getEventId());
        }

        holder.setEventName(event.getName());
        holder.setEventTitle(event.getName());
    }



    @Override
    public int getItemCount() {
        return events.size();
    }
}
