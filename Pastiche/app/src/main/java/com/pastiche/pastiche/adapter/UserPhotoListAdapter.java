package com.pastiche.pastiche.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pastiche.pastiche.PObject.PPhoto;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestHandler;
import com.pastiche.pastiche.viewHolder.PhotoListViewHolder;
import com.pastiche.pastiche.viewHolder.UserPhotoListViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class UserPhotoListAdapter extends RecyclerView.Adapter<UserPhotoListViewHolder> {

    private Context context;
    private static final String TAG = "UserPhotoListAdapter";
    private int userID;
    private List<PPhoto> photos;



    /**
     * get resources (should be an array of event IDs)
     *
     * @param context
     * @param userID
     */
    public UserPhotoListAdapter(Context context, int userID) {
        this.context = context;
        this.userID = userID;
        refresh();
    }



    /**
     * loads the list of photos corresponding to cur event
     * @param photosList
     */
    private void loadPhotos(PPhoto[] photosList) {
        Collections.addAll(photos, photosList);
        this.notifyDataSetChanged();
    }



    @Override
    public UserPhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserPhotoListViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }




    /**
     * assemble the view HOLDER for given POSITION
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(UserPhotoListViewHolder holder, int position) {

        String internetUrl = ServerRequestHandler.baseURL + "/photos/";

//        PPhoto curPhoto = photos.get(position);
//        Log.d(TAG, "photos size: " +photos.size());

        if(photos != null && photos.size() >= position && photos.get(position).getId() > 11) {
            String url = internetUrl + photos.get(position).getId();
            Log.d(TAG, "photo picked: " +url);
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.getImg_event_item());
        }
        holder.setPhotoId(photos.get(position).getId());
        holder.setUserId(photos.get(position).getUserId());
        holder.setUploadDate(photos.get(position).getUploaded());

    }



    @Override
    public int getItemCount() {
        return photos.size();
    }



    public void refresh() {
        photos = new ArrayList<>(100);
        photos.clear();

        ServerHandler.getInstance(context).listPhotosForAnUser(
                userID,
                photosList -> loadPhotos(photosList),
                error -> Log.e(TAG, error)
        );
    }

    @Override
    public long getItemId(int position) {
        return photos.get(position).getId();
    }
}
