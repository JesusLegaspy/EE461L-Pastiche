package com.pastiche.pastiche.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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



    private ImageView img_event_item;
    private TextView txt_event_item_title;



    public EventListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.event_tile_item, parent, false));



        img_event_item = (ImageView) itemView.findViewById(R.id.img_event_item);
        txt_event_item_title = (TextView) itemView.findViewById(R.id.txt_event_item_title);



        itemView.setOnClickListener(v->{
            Context context = v.getContext();
            Intent intent = new Intent(context, EventActivity.class);
            intent.putExtra(EventActivity.EXTRA_POSITION, getAdapterPosition());
            context.startActivity(intent);
        });
    }

    public ImageView getImg_event_item() {
        return img_event_item;
    }

    public void setImg_event_item(Drawable img_event_item) {
        this.img_event_item.setImageDrawable(img_event_item);
    }

    public void setTxt_event_item_title(String txt_event_item_title) {
        this.txt_event_item_title.setText(txt_event_item_title);
    }
}