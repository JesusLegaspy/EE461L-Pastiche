package com.pastiche.pastiche.model;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aria Pahlavan on 11/5/16.
 */

public class EventlistData {
    private ArrayList<ImageView> pImagesId;
    private ArrayList<String> pEventNameId;


    public List<EventlistItem> getData(){
        List<EventlistItem> pData = new ArrayList<>();

        for (int i=0; i<pImagesId.size() && i<pEventNameId.size(); i++ )
            pData.add(new EventlistItem(pEventNameId.get(i)));


        return pData;
    }
}
