package com.pastiche.pastiche.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.pastiche.pastiche.PObject.PPhoto;
import com.pastiche.pastiche.Server.ServerHandler;
import com.pastiche.pastiche.Server.ServerRequestHandler;
import com.pastiche.pastiche.viewHolder.PhotoListViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListViewHolder> {

    private Context context;
    private static final String TAG = "PhotoListAdapter";
    private int eventID;
    private List<PPhoto> photos;



    /**
     * get resources (should be an array of event IDs)
     *
     * @param context
     * @param eventId
     */
    public PhotoListAdapter(Context context, int eventId) {
        this.context = context;
        this.eventID = eventId;
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
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoListViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }




    /**
     * assemble the view HOLDER for given POSITION
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PhotoListViewHolder holder, int position) {

        String internetUrl = ServerRequestHandler.baseURL + "/photos/";

//        PPhoto curPhoto = photos.get(position);
        Log.d("PhotoListAdapter", "photos size: " +photos.size());

        if(photos != null && photos.size() >= position && photos.get(position).getId() > 11) {
            String url = internetUrl + photos.get(position).getId();
            Log.d("PhotoListAdapter", "photo picked: " +photos.get(position).getId());
            Glide.with(context).load(url).into(holder.getImg_event_item());
        }

    }



    @Override
    public int getItemCount() {
        return photos.size();
    }



    public void refresh() {
        photos = new ArrayList<>(100);
        photos.clear();

        ServerHandler.getInstance(context).listPhotosForAnEvent(
                eventID,
                photosList -> loadPhotos(photosList),
                error -> Log.e(TAG, error)
        );
    }
}
