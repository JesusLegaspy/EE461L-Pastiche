package com.pastiche.pastiche.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pastiche.pastiche.ImgDetailActivity;
import com.pastiche.pastiche.R;

import java.util.Date;

/**
 * Created by Aria Pahlavan on 11/13/16.
 */

public class UserPhotoListViewHolder extends RecyclerView.ViewHolder{
    public static final String TAG = "UserPhotoListViewHolder";
    private ImageView img_photo_item;
    private int photoId;
    private int userId;
    private String uploadDate;



    public UserPhotoListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.photo_tile_item, parent, false));

        img_photo_item = (ImageView) itemView.findViewById(R.id.img_photo_item);

        itemView.setOnClickListener(v->{
            Context context = v.getContext();
            Intent intent = new Intent(context, ImgDetailActivity.class);
            Log.d(TAG, "itemView Listener: " + photoId );
            intent.putExtra(ImgDetailActivity.EXTRA_PHOTO_ID, this.photoId);
            intent.putExtra(ImgDetailActivity.EXTRA_IMG_USER_ID, this.userId);
            intent.putExtra(ImgDetailActivity.EXTRA_IMG_UPLOAD, this.uploadDate);
            context.startActivity(intent);
        });

    }



    public ImageView getImg_event_item() {
        img_photo_item.layout(0,0,0,0);
        return img_photo_item;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate.toString();
    }
}