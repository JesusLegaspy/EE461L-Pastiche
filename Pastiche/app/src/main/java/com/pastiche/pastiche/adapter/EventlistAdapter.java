package com.pastiche.pastiche.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.pastiche.pastiche.R;
import com.pastiche.pastiche.viewHolder.EventListViewHolder;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class EventlistAdapter extends RecyclerView.Adapter<EventListViewHolder> {

    // TODO number of recycler view items needs change to be dynamic
    private static final int LENGTH = 20;


    private final String[] mEvents;
    private final Drawable[] mEventPictures;


    /**
     * get resources (should be an array of event IDs)
     * @param context
     */
    public EventlistAdapter(Context context) {

        //TODO data must be received from server dynamically
        Resources resources = context.getResources();
        mEvents = resources.getStringArray(R.array.events);
        TypedArray a = resources.obtainTypedArray(R.array.events_picture);
        mEventPictures = new Drawable[a.length()];
        for (int i = 0; i < mEventPictures.length; i++) {
            mEventPictures[i] = a.getDrawable(i);
        }
        a.recycle();//TODO to be removed?
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
        holder.setImg_event_item(mEventPictures[position % mEventPictures.length]);
        holder.setTxt_event_item_title(mEvents[position % mEvents.length]);
    }




    @Override
    public int getItemCount() {
        return LENGTH;
    }
}
