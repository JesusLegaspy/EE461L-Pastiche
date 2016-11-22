package com.pastiche.pastiche.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.pastiche.pastiche.R;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class PhotoListViewHolder extends RecyclerView.ViewHolder {



    private ImageView img_photo_item;




    public PhotoListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.photo_tile_item, parent, false));

        img_photo_item = (ImageView) itemView.findViewById(R.id.img_photo_item);

    }

    public ImageView getImg_event_item() {
        return img_photo_item;
    }

}