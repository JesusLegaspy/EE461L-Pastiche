package com.pastiche.pastiche.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pastiche.pastiche.EventActivity;
import com.pastiche.pastiche.R;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class EventListViewHolder extends RecyclerView.ViewHolder {


    public static final String TAG = "EventListViewHolder";
    private String eventName;
    private ImageView eventPhoto;
    private TextView eventTitle;
    private int eventId;


    public EventListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.event_tile_item, parent, false));



        eventPhoto = (ImageView) itemView.findViewById(R.id.img_event_item);
        eventTitle = (TextView) itemView.findViewById(R.id.txt_event_item_title);



        itemView.setOnClickListener(v->{
            Context context = v.getContext();
            Intent intent = new Intent(context, EventActivity.class);
            Log.d(TAG, "itemView Listener: "+eventId );
            intent.putExtra(EventActivity.EXTRA_EVENT_NAME, this.eventName);
            intent.putExtra(EventActivity.EXTRA_EVENT_ID, this.eventId);
            context.startActivity(intent);
        });
    }



    public ImageView getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(Drawable eventPhoto) {
        this.eventPhoto.setImageDrawable(eventPhoto);
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle.setText(eventTitle);
    }

    public void setEventId(int eventId) {
        Log.d(TAG, "setEventId: "+eventId );
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}